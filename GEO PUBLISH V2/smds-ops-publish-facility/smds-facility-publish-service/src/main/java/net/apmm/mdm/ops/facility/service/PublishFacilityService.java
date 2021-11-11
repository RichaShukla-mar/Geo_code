package net.apmm.mdm.ops.facility.service;


import com.maersk.facility.smds.operations.MSK.*;
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

    private final KafkaTemplate<String, facilityMessage> kafkaTemplate;

    private String messageId = UUID.randomUUID().toString();

    public StatusResponse publishOpsFacilityDetails(String fctRowID, String eventDetails) {
        try {
            log.debug(" Processing request, Processing DB queries.... ");
            OpsFacilityMessageData fctMsg = opsFacilityMessageDao.getFacilityMessage(fctRowID, eventDetails);
            log.debug(" Message before sending.... " + transformData(fctMsg).toString());
            final ProducerRecord<String, facilityMessage> record = new ProducerRecord<>(facilityTopic, fctRowID, transformData(fctMsg));
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

    private facilityMessage transformData(OpsFacilityMessageData fctMsg) {
        //OpsFacilityEntity opsFacilityEntity = new OpsFacilityEntity();
        facilityMessage facilityMessage = new facilityMessage();
        facility opsFacilityInfo = new facility();


        //opsFacilityInfo.setFctRowID(fctMsg.getOpsFacilityEntityData().getOpsFacilityData().getFctRowID());
        opsFacilityInfo.setName(fctMsg.getOpsFacilityData().getFacilityName());
        opsFacilityInfo.setType(fctMsg.getOpsFacilityData().getFacilityType());
        opsFacilityInfo.setExtOwned(fctMsg.getOpsFacilityData().getFacilityExtOwned());
        opsFacilityInfo.setStatus(fctMsg.getOpsFacilityData().getFacilityStatus());
        opsFacilityInfo.setExtExposed(fctMsg.getOpsFacilityData().getFacilityExtExposed());
        opsFacilityInfo.setUrl(fctMsg.getOpsFacilityData().getFacilityURL());
        opsFacilityInfo.setDoDAAC(fctMsg.getOpsFacilityData().getFacilityDODAAC());

        log.info("OpsFacility message Out", opsFacilityInfo);

        List<address> opsFacilityAddressDataList = fctMsg.getOpsFacilityData().getFacilityAddress().stream()
                .map(list -> new address(list.getHouseNumber(), list.getStreet(), list.getCity(), list.getPostalCode(), list.getPoBox(),
                        list.getDistrict(), list.getTerritory(), list.getCountryName(), list.getCountryCode(), list.getAddressLine2(), list.getAddressLine3(), list.getLatitude(),
                        list.getLongitude(), list.getAddressQualityCheckIndicator())).collect(Collectors.toList());

        if (opsFacilityAddressDataList.size() != 0)
            opsFacilityInfo.setAddress(opsFacilityAddressDataList);


        List<parent> opsFacilityParentDetailsList = fctMsg.getOpsFacilityData().getFacilityParentDetails().stream()
                .map(list -> new parent(list.getName(), list.getType(),
                        list.getParentAltCodeData().stream()
                                .map(l -> new parentAlternateCodes(l.getCodeType(), l.getCode())).collect(Collectors.toList())))
                .collect(Collectors.toList());

        for (parent prntDetails : opsFacilityParentDetailsList) {
            for (parentAlternateCodes parentAltCd : prntDetails.getAlternateCodes()) {
                if (parentAltCd.getCodeType() == null &&
                        parentAltCd.getCode() == null)
                    prntDetails.setAlternateCodes(null);

            }
        }
        if (opsFacilityParentDetailsList.size() != 0)
            opsFacilityInfo.setParent(opsFacilityParentDetailsList);


        List<facilityDetail> opsFacilityDetailList = fctMsg.getOpsFacilityData().getFacilityDetail().stream()
                .map(list -> new facilityDetail(list.getWeightLimitCraneKg(), list.getWeightLimitYardKg(), list.getVesselAgent(),
                        list.getGpsFlag(), list.getGsmFlag(), list.getOceanFreightPricing(), list.getCommFacilityBrand(), list.getCommFacilityType(), list.getCommExportEnquiriesEmail(),
                        list.getCommImportEnquiriesEmail(), list.getCommFacilityFunction(), list.getCommFacilityFunctionDesc(), list.getCommInternationalDialCode(),
                        list.getTelephoneNumber(),
                        list.getFacilityType().stream()
                                .map(l -> new OpsFacilityType(l.getCode(), l.getName(), l.getMasterType(), l.getValidThroughDate())).collect(Collectors.toList())))
                .collect(Collectors.toList());


        for (facilityDetail opsDetails : opsFacilityDetailList) {
            for (OpsFacilityType fctType : opsDetails.getFacilityType()) {
                if (fctType.getCode() == null &&
                        fctType.getName() == null &&
                        fctType.getMasterType() == null &&
                        fctType.getValidThroughDate() == null)
                    opsDetails.setFacilityType(null);

            }
        }

        if (opsFacilityDetailList.size() != 0)
            opsFacilityInfo.setFacilityDetails(opsFacilityDetailList);


        List<alternateCodes> opsFacilityAlternateCodesDataList = fctMsg.getOpsFacilityData().getFacilityAlternateCodes().stream()
                .map(list -> new alternateCodes(list.getCodeType(), list.getCode())).collect(Collectors.toList());

        if (opsFacilityAlternateCodesDataList.size() != 0)
            opsFacilityInfo.setAlternateCodes(opsFacilityAlternateCodesDataList);

        List<openingHours> opsFacilityOpeningHoursList = fctMsg.getOpsFacilityData().getFacilityOpeningHours().stream()
                .map(list -> new openingHours(list.getDay(), list.getOpenTimeHours(), list.getOpenTimeMinutes(), list.getCloseTimeHours(), list.getCloseTimeMinutes())).collect(Collectors.toList());

        if (opsFacilityOpeningHoursList.size() != 0)
            opsFacilityInfo.setOpeningHours(opsFacilityOpeningHoursList);


        List<transportModes> opsFacilityTransportModesList = fctMsg.getOpsFacilityData().getFacilityTransportModes().stream()
                .map(list -> new transportModes(list.getTransportMode(), list.getTransportCode(), list.getTransportDescription(), list.getValidThroughDate())).collect(Collectors.toList());

        if (opsFacilityTransportModesList.size() != 0)
            opsFacilityInfo.setTransportModes(opsFacilityTransportModesList);


        List<facilityServices> opsFacilityServicesList = fctMsg.getOpsFacilityData().getFacilityServices().stream()
                .map(list -> new facilityServices(list.getServiceName(), list.getServiceCode(), list.getServiceDescription(), list.getValidThroughDate())).collect(Collectors.toList());

        if (opsFacilityServicesList.size() != 0)
            opsFacilityInfo.setFacilityServices(opsFacilityServicesList);

        List<fence> opsFacilityFenceList = fctMsg.getOpsFacilityData().getFacilityFence().stream()
                .map(list -> new fence(list.getName(), list.getFenceType())).collect(Collectors.toList());

        if (opsFacilityFenceList.size() != 0)
            opsFacilityInfo.setFence(opsFacilityFenceList);

        List<bda> opsFacilityBDADetailsList = fctMsg.getOpsFacilityData().getFacilityBda().stream()
                .map(list -> new bda(list.getName(),list.getType(),list.getBdaType(),list.getBdaAlternateCodeData().stream()
                        .map(l -> new bdaAlternateCode(l.getCodeType(),l.getCode())).collect(Collectors.toList())))
                .collect(Collectors.toList());

        for( bda bdaDetails: opsFacilityBDADetailsList){
            for(bdaAlternateCode bdaAlternateCode :bdaDetails.getAlternateCodes()){
                if(bdaAlternateCode.getCodeType()==null &&
                        bdaAlternateCode.getCode() ==null)
                    bdaDetails.setAlternateCodes(null);
            }
        }

        if(opsFacilityBDADetailsList.size() !=0){
            opsFacilityInfo.setBda(opsFacilityBDADetailsList);
        }

        List<contactDetails> opsFacilityContactDetailList = fctMsg.getOpsFacilityData().getFacilityContactDetail().stream()
                .map(list -> new contactDetails(list.getFirstName(), list.getLastName(), list.getJobTitle(), list.getDepartment(), list.getInternationalDialingCdPhone(),
                        list.getExtension(), list.getPhoneNumber(), list.getInternationalDialingCdMobile(), list.getMobileNumber(), list.getInternationalDialingCdFax(), list.getFaxNumber(), list.getEmailAddress(),
                        list.getValidThroughDate())).collect(Collectors.toList());

        if (opsFacilityContactDetailList.size() != 0)
            opsFacilityInfo.setContactDetails(opsFacilityContactDetailList);

        //opsFacilityEntity.setOpsFacilityInformation(opsFacilityInfo);
        //opsFacilityMessage.setOpsFacilityEntity(opsFacilityInfo);
        facilityMessage.setFacility(opsFacilityInfo);

        return facilityMessage;


    }


}
