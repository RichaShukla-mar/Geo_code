package net.apmm.mdm.ops.facility.service;


import com.maersk.OpsFacility.smds.operations.MSK.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.apmm.mdm.ops.facility.dao.OpsFacilityMessageDao;
import net.apmm.mdm.ops.facility.dao.model.OpsFacilityBdaAltCdData;
import net.apmm.mdm.ops.facility.dao.model.OpsFacilityMessageData;
import net.apmm.mdm.ops.facility.exception.PublishException;
import net.apmm.mdm.ops.facility.model.StatusResponse;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


import java.util.*;

import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PublishFacilityService {

    @Autowired
    private final OpsFacilityMessageDao opsFacilityMessageDao;
    @Value("${kafka.topic}")
    private String facilityTopic;


    @Value("#{${facility-event-details}}")
    private Map<String, String> facilityEventDetails;

    private final KafkaTemplate<String, OpsFacilityMessage> kafkaTemplate;

    private String messageId = UUID.randomUUID().toString();

    public StatusResponse publishOpsFacilityDetails(String fctRowID, String eventDetails) {
        try {
            log.debug(" Processing request, Processing DB queries.... ");
            OpsFacilityMessageData fctMsg = opsFacilityMessageDao.getFacilityMessage(fctRowID, eventDetails);
            log.debug(" Message before sending.... " + transformData(fctMsg).toString());
            final ProducerRecord<String, OpsFacilityMessage> record = new ProducerRecord<>(facilityTopic, fctRowID, transformData(fctMsg));
            log.debug(" Record before publish " + record.toString());
            String eventType = facilityEventDetails.get(eventDetails).split("\\|")[0];
            String eventDescr = facilityEventDetails.get(eventDetails).split("\\|")[1];

            record.headers().add("EventType", eventType.getBytes());
            record.headers().add("EventDescription", eventDescr.getBytes());
            record.headers().add("eventDateTime", java.util.Calendar.getInstance().getTime().toString().getBytes());
            record.headers().add("messageId", messageId.getBytes());
            log.debug(" Record after header append " + record.toString());
            kafkaTemplate.send(record);
            kafkaTemplate.flush();
            log.info(" ### Facility message Published successfully to facility topic for fctRowID " + fctRowID + " ### ");
            return StatusResponse.builder().fctRowID(fctRowID).fctRequestId(UUID.randomUUID().toString()).build();
        } catch (Exception e) {
            log.debug("Error cause" + e.getCause());
            e.printStackTrace();
            throw new PublishException("Error while generating & publishing AVRO message :: " + e);
        }
    }

    private OpsFacilityMessage transformData(OpsFacilityMessageData fctMsg) {
        OpsFacilityEntity opsFacilityEntity = new OpsFacilityEntity();
        OpsFacilityMessage opsFacilityMessage = new OpsFacilityMessage();
        OpsFacilityInfo opsFacilityInfo = new OpsFacilityInfo();
        opsFacilityInfo.setFctRowID(fctMsg.getOpsFacilityEntityData().getOpsFacilityData().getFctRowID());
        opsFacilityInfo.setFacilityName(fctMsg.getOpsFacilityEntityData().getOpsFacilityData().getFacilityName());
        opsFacilityInfo.setFacilityType(fctMsg.getOpsFacilityEntityData().getOpsFacilityData().getFacilityType());
        opsFacilityInfo.setFacilityExtOwned(fctMsg.getOpsFacilityEntityData().getOpsFacilityData().getFacilityExtOwned());
        opsFacilityInfo.setFacilityStatus(fctMsg.getOpsFacilityEntityData().getOpsFacilityData().getFacilityStatus());
        opsFacilityInfo.setFacilityExtExposed(fctMsg.getOpsFacilityEntityData().getOpsFacilityData().getFacilityExtExposed());
        opsFacilityInfo.setFacilityURL(fctMsg.getOpsFacilityEntityData().getOpsFacilityData().getFacilityURL());
        opsFacilityInfo.setFacilityDODAAC(fctMsg.getOpsFacilityEntityData().getOpsFacilityData().getFacilityDODAAC());

        log.info("OpsFacility message Out", opsFacilityInfo);

        List<OpsFacilityAddress> opsFacilityAddressDataList = fctMsg.getOpsFacilityEntityData().getOpsFacilityData().getFacilityAddress().stream()
                .map(list -> new OpsFacilityAddress(list.getHouseNumber(), list.getStreet(), list.getCity(), list.getPostalCode(), list.getPoBox(),
                        list.getDistrict(), list.getTerritory(), list.getCountryName(), list.getCountryCode(), list.getAddressLine2(), list.getAddressLine3(), list.getLatitude(),
                        list.getLongitude(), list.getAddressQualityCheckIndicator())).collect(Collectors.toList());

        if (opsFacilityAddressDataList.size() != 0)
            opsFacilityInfo.setOpsFacilityAddress(opsFacilityAddressDataList);


        List<OpsFacilityParentDetails> opsFacilityParentDetailsList = fctMsg.getOpsFacilityEntityData().getOpsFacilityData().getFacilityParentDetails().stream()
                .map(list -> new OpsFacilityParentDetails(list.getParentRowID(), list.getName(), list.getType(),
                        list.getParentAltCodeData().stream()
                                .map(l -> new OpsFacilityParentAlternateCode(l.getCodeType(), l.getCode())).collect(Collectors.toList())))
                .collect(Collectors.toList());

        for (OpsFacilityParentDetails prntDetails : opsFacilityParentDetailsList) {
            for (OpsFacilityParentAlternateCode parentAltCd : prntDetails.getOpsFacilityParentAlternateCodeDetails()) {
                if (parentAltCd.getCodeType() == null &&
                        parentAltCd.getCode() == null)
                    prntDetails.setOpsFacilityParentAlternateCodeDetails(null);

            }
        }
        if (opsFacilityParentDetailsList.size() != 0)
            opsFacilityInfo.setOpsFacilityParentDetails(opsFacilityParentDetailsList);


        List<OpsFacilityDetail> opsFacilityDetailList = fctMsg.getOpsFacilityEntityData().getOpsFacilityData().getFacilityDetail().stream()
                .map(list -> new OpsFacilityDetail(list.getFctOpsRowid(), list.getWeightLimitCraneKg(), list.getWeightLimitYardKg(), list.getVesselAgent(),
                        list.getGpsFlag(), list.getGsmFlag(), list.getOceanFreightPricing(), list.getCommFacilityBrand(), list.getCommFacilityType(), list.getCommExportEnquiriesEmail(),
                        list.getCommImportEnquiriesEmail(), list.getCommFacilityFunction(), list.getCommFacilityFunctionDesc(), list.getCommInternationalDialCode(),
                        list.getTelephoneNumber(),
                        list.getFacilityType().stream()
                                .map(l -> new OpsFacilityType(l.getCode(), l.getName(), l.getMasterType(), l.getValidThroughDate())).collect(Collectors.toList())))
                .collect(Collectors.toList());


        for (OpsFacilityDetail opsDetails : opsFacilityDetailList) {
            for (OpsFacilityType fctType : opsDetails.getOpsFacilityType()) {
                if (fctType.getCode() == null &&
                        fctType.getName() == null &&
                        fctType.getMasterType() == null &&
                        fctType.getValidThroughDate() == null)
                    opsDetails.setOpsFacilityType(null);

            }
        }

        if (opsFacilityDetailList.size() != 0)
            opsFacilityInfo.setOpsFacilityDetail(opsFacilityDetailList);


        List<OpsFacilityAlternateCodes> opsFacilityAlternateCodesDataList = fctMsg.getOpsFacilityEntityData().getOpsFacilityData().getFacilityAlternateCodes().stream()
                .map(list -> new OpsFacilityAlternateCodes(list.getCodeType(), list.getCode())).collect(Collectors.toList());

        if (opsFacilityAlternateCodesDataList.size() != 0)
            opsFacilityInfo.setOpsFacilityAlternateCodes(opsFacilityAlternateCodesDataList);

        List<OpsFacilityOpeningHours> opsFacilityOpeningHoursList = fctMsg.getOpsFacilityEntityData().getOpsFacilityData().getFacilityOpeningHours().stream()
                .map(list -> new OpsFacilityOpeningHours(list.getDay(), list.getOpenTimeHours(), list.getOpenTimeMinutes(), list.getCloseTimeHours(), list.getCloseTimeMinutes())).collect(Collectors.toList());

        if (opsFacilityOpeningHoursList.size() != 0)
            opsFacilityInfo.setOpsFacilityOpeningHours(opsFacilityOpeningHoursList);


        List<OpsFacilityTransportModes> opsFacilityTransportModesList = fctMsg.getOpsFacilityEntityData().getOpsFacilityData().getFacilityTransportModes().stream()
                .map(list -> new OpsFacilityTransportModes(list.getTransportMode(), list.getTransportCode(), list.getTransportDescription(), list.getValidThroughDate())).collect(Collectors.toList());

        if (opsFacilityTransportModesList.size() != 0)
            opsFacilityInfo.setOpsTransportModes(opsFacilityTransportModesList);


        List<OpsFacilityServices> opsFacilityServicesList = fctMsg.getOpsFacilityEntityData().getOpsFacilityData().getFacilityServices().stream()
                .map(list -> new OpsFacilityServices(list.getServiceName(), list.getServiceCode(), list.getServiceDescription(), list.getValidThroughDate())).collect(Collectors.toList());

        if (opsFacilityServicesList.size() != 0)
            opsFacilityInfo.setOpsFacilityServices(opsFacilityServicesList);

        List<OpsFacilityFence> opsFacilityFenceList = fctMsg.getOpsFacilityEntityData().getOpsFacilityData().getFacilityFence().stream()
                .map(list -> new OpsFacilityFence(list.getName(), list.getFenceType())).collect(Collectors.toList());

        if (opsFacilityFenceList.size() != 0)
            opsFacilityInfo.setOpsFacilityFence(opsFacilityFenceList);

        List<OpsFacilityBDADetails> opsFacilityBDADetailsList = fctMsg.getOpsFacilityEntityData().getOpsFacilityData().getFacilityBda().stream()
                .map(list -> new OpsFacilityBDADetails(list.getBdaRowID(),list.getName(),list.getType(),list.getBdaType(),list.getBdaAlternateCodeData().stream()
                        .map(l -> new OpsFacilityBDAAlternateCode(l.getCodeType(),l.getCode())).collect(Collectors.toList())))
                .collect(Collectors.toList());

        for( OpsFacilityBDADetails bdaDetails: opsFacilityBDADetailsList){
            for(OpsFacilityBDAAlternateCode bdaAlternateCode :bdaDetails.getOpsFacilityBDAAlternateCodeDetails() ){
                if(bdaAlternateCode.getCodeType()==null &&
                        bdaAlternateCode.getCode() ==null)
                    bdaDetails.setOpsFacilityBDAAlternateCodeDetails(null);
            }
        }

        if(opsFacilityBDADetailsList.size() !=0){
            opsFacilityInfo.setOpsFacilityBDADetails(opsFacilityBDADetailsList);
        }

        List<OpsFacilityContactDetail> opsFacilityContactDetailList = fctMsg.getOpsFacilityEntityData().getOpsFacilityData().getFacilityContactDetail().stream()
                .map(list -> new OpsFacilityContactDetail(list.getFirstName(), list.getLastName(), list.getJobTitle(), list.getDepartment(), list.getInternationalDialingCdPhone(),
                        list.getExtension(), list.getPhoneNumber(), list.getInternationalDialingCdMobile(), list.getMobileNumber(), list.getInternationalDialingCdFax(), list.getFaxNumber(), list.getEmailAddress(),
                        list.getValidThroughDate())).collect(Collectors.toList());

        if (opsFacilityContactDetailList.size() != 0)
            opsFacilityInfo.setOpsContactDetail(opsFacilityContactDetailList);

        opsFacilityEntity.setOpsFacilityInformation(opsFacilityInfo);
        opsFacilityMessage.setOpsFacilityEntity(opsFacilityEntity);

        return opsFacilityMessage;


    }


}
