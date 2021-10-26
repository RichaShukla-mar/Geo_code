package net.apmm.mdm.ops.geo.service;

import com.maersk.Geography.smds.operations.MSK.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.apmm.mdm.ops.geo.dao.GeographyMessageDao;
import net.apmm.mdm.ops.geo.dao.model.GeographyMessageData;
import net.apmm.mdm.ops.geo.exception.PublishException;
import net.apmm.mdm.ops.geo.model.StatusResponse;
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
public class PublishGeographyService {

    @Autowired

    private final GeographyMessageDao geographyMessageDao;
    @Value("${kafka.topic}")
    private String geoTopic;

    @Value("#{${geo-event-details}}")
    private Map<String, String> geoEventDetails;

    private final KafkaTemplate<String, GeographyMessage> kafkaTemplate;

    private String messageId = UUID.randomUUID().toString();


    public StatusResponse publishGeographyDetails(String geoRowID, String eventDetails) {
        try {
            log.debug(" Processing request, Processing DB queries.... ");
            GeographyMessageData geoMsg = geographyMessageDao.getGeographyMessage(geoRowID, eventDetails);
            log.debug(" Message before sending.... " + transformData(geoMsg).toString());
            final ProducerRecord<String, GeographyMessage> record = new ProducerRecord<>(geoTopic, geoRowID, transformData(geoMsg));
            log.debug(" Record before publish " + record.toString());
            //log.debug("Kafka Template" + kafkaTemplate.toString());
            String eventType = geoEventDetails.get(eventDetails).split("\\|")[0];
            String eventDescr = geoEventDetails.get(eventDetails).split("\\|")[1];

            record.headers().add("EventType", eventType.getBytes());
            record.headers().add("EventDescription", eventDescr.getBytes());
            record.headers().add("eventDateTime", java.util.Calendar.getInstance().getTime().toString().getBytes());
            record.headers().add("messageId", messageId.getBytes());
            log.debug(" Record after header append " + record.toString());
            kafkaTemplate.send(record);
            kafkaTemplate.flush();
            log.info(" ### Geo message Published successfully to geo topic for GeoRowid " + geoRowID + " ### ");
            return StatusResponse.builder().geoRowID(geoRowID).geoRequestId(UUID.randomUUID().toString()).build();

        } catch (Exception e) {
            log.debug("Error cause" + e.getCause());
            log.debug("Error cause1" + e.getMessage());
            log.debug("Error cause2" + e.getLocalizedMessage());
            e.getLocalizedMessage();
            e.printStackTrace();
            e.getMessage();

            throw new PublishException("Error while generating & publishing AVRO message :: " + e);
        }

    }

    private GeographyMessage transformData(GeographyMessageData geoMsg) {
        GeographyEntity geographyEntity = new GeographyEntity();
        GeographyMessage geographyMessage = new GeographyMessage();
        Geography geographyInfo = new Geography();

        geographyInfo.setGeoRowID(geoMsg.getGeographyEntity().getGeographyData().getGeoRowID());
        geographyInfo.setGeoType(geoMsg.getGeographyEntity().getGeographyData().getGeoType());
        geographyInfo.setName(geoMsg.getGeographyEntity().getGeographyData().getName());
        geographyInfo.setStatus(geoMsg.getGeographyEntity().getGeographyData().getStatus());
        geographyInfo.setValidFrom(geoMsg.getGeographyEntity().getGeographyData().getValidFrom());
        geographyInfo.setValidTo(geoMsg.getGeographyEntity().getGeographyData().getValidTo());
        geographyInfo.setLongitude(geoMsg.getGeographyEntity().getGeographyData().getLongitude());
        geographyInfo.setLatitude(geoMsg.getGeographyEntity().getGeographyData().getLatitude());
        geographyInfo.setTimeZone(geoMsg.getGeographyEntity().getGeographyData().getTimeZone());
        geographyInfo.setDaylightSavingTime(geoMsg.getGeographyEntity().getGeographyData().getDaylightSavingTime());
        geographyInfo.setUTCOffsetMinutes(geoMsg.getGeographyEntity().getGeographyData().getUtcOffsetMinutes());
        geographyInfo.setDaylightSavingStart(geoMsg.getGeographyEntity().getGeographyData().getDaylightSavingStart());
        geographyInfo.setDaylightSavingEnd(geoMsg.getGeographyEntity().getGeographyData().getDaylightSavingEnd());
        geographyInfo.setDaylightSavingShiftMinutes(geoMsg.getGeographyEntity().getGeographyData().getDaylightSavingShiftMinutes());
        geographyInfo.setDescription(geoMsg.getGeographyEntity().getGeographyData().getDescription());
        geographyInfo.setWorkaroundReason(geoMsg.getGeographyEntity().getGeographyData().getWorkaroundReason());
        geographyInfo.setRestricted(geoMsg.getGeographyEntity().getGeographyData().getRestricted());
        geographyInfo.setSiteType(geoMsg.getGeographyEntity().getGeographyData().getSiteType());
        geographyInfo.setGPSFlag(geoMsg.getGeographyEntity().getGeographyData().getGpsFlag());
        geographyInfo.setGSMFlag(geoMsg.getGeographyEntity().getGeographyData().getGsmFlag());
        geographyInfo.setStreetNumber(geoMsg.getGeographyEntity().getGeographyData().getStreetNumber());
        geographyInfo.setAddressLine1(geoMsg.getGeographyEntity().getGeographyData().getAddressLine1());
        geographyInfo.setAddressLine2(geoMsg.getGeographyEntity().getGeographyData().getAddressLine2());
        geographyInfo.setAddressLine3(geoMsg.getGeographyEntity().getGeographyData().getAddressLine3());
        geographyInfo.setPostalCode(geoMsg.getGeographyEntity().getGeographyData().getPostalCode());
        geographyInfo.setPostalCodeMandatoryFlag(geoMsg.getGeographyEntity().getGeographyData().getPostalCodeMandatoryFlag());
        geographyInfo.setStateProvienceMandatory(geoMsg.getGeographyEntity().getGeographyData().getStateProvinceMandatory());
        geographyInfo.setDialingCode(geoMsg.getGeographyEntity().getGeographyData().getDialingCode());
        geographyInfo.setDialingCodedescription(geoMsg.getGeographyEntity().getGeographyData().getDialingCodeDescription());
        geographyInfo.setPortFlag(geoMsg.getGeographyEntity().getGeographyData().getPortFlag());
        geographyInfo.setOlsonTimezone(geoMsg.getGeographyEntity().getGeographyData().getOlsonTimezone());
        geographyInfo.setBDAType(geoMsg.getGeographyEntity().getGeographyData().getBdaType());
        geographyInfo.setHSUDName(geoMsg.getGeographyEntity().getGeographyData().getHsudName());


        log.info("geographyInfo message Out", geographyInfo);


        List<GeographyAlternateNames> geographyAlternateNamesList = geoMsg.getGeographyEntity().getGeographyData().getAlternateNames().stream()
                .map(list -> new GeographyAlternateNames(list.getName(), list.getDescription(), list.getStatus())).collect(Collectors.toList());

        if (geographyAlternateNamesList.size() != 0)
            geographyInfo.setGeographyAlternateNames(geographyAlternateNamesList);


        List<GeographyAlternateCodes> geographyAlternateCodesList = geoMsg.getGeographyEntity().getGeographyData().getAlternateCodes().stream()
                .map(list -> new GeographyAlternateCodes(list.getCodeType(), list.getCode())).collect(Collectors.toList());

        if (geographyAlternateCodesList.size() != 0)
            geographyInfo.setGeographyAlternateCodes(geographyAlternateCodesList);


        List<GeographyFence> geographyFenceList = geoMsg.getGeographyEntity().getGeographyData().getFence().stream()
                .map(list -> new GeographyFence(list.getName(), list.getGeoFenceType())).collect(Collectors.toList());

        if (geographyFenceList.size() != 0)
            geographyInfo.setGeographyFence(geographyFenceList);

        List<GeographyCountryDetails> geographyCountryDetailsList = geoMsg.getGeographyEntity().getGeographyData().getCountry().stream()
                .map(list -> new GeographyCountryDetails(list.getCountryRowID(), list.getName(), list.getType(),
                        list.getCountryAltCdData().stream()
                                .map(l -> new GeographyCountryAlternateCode(l.getCodeType(), l.getCode())).collect(Collectors.toList())))
                .collect(Collectors.toList());

        for (GeographyCountryDetails countryDetails : geographyCountryDetailsList) {
            for (GeographyCountryAlternateCode cntryAltCdDetails : countryDetails.getGeographyCountryAlternateCodeDetails()) {
                if (cntryAltCdDetails.getCodeType() == null &&
                        cntryAltCdDetails.getCode() == null)
                    countryDetails.setGeographyCountryAlternateCodeDetails(null);

            }

        }
        if (geographyCountryDetailsList.size() != 0)
            geographyInfo.setGeographyCountryDetails(geographyCountryDetailsList);


        List<GeographyParentDetails> geographyPrntDetailsList = geoMsg.getGeographyEntity().getGeographyData().getParentDetails().stream()
                .map(list -> new GeographyParentDetails(list.getParentRowId(), list.getName(), list.getType(), list.getBdatype(),
                        list.getParentAlternateCode().stream()
                                .map(l -> new GeographyParentAlternateCode(l.getCodeType(), l.getCode())).collect(Collectors.toList())))
                .collect(Collectors.toList());

        for (GeographyParentDetails prntDetails : geographyPrntDetailsList) {
            for (GeographyParentAlternateCode parentAltCd : prntDetails.getGeographyParentAlternateCodeDetails()) {
                if (parentAltCd.getCodeType() == null &&
                        parentAltCd.getCode() == null)
                    prntDetails.setGeographyParentAlternateCodeDetails(null);

            }
        }
        if (geographyPrntDetailsList.size() != 0)
            geographyInfo.setGeographyParentDetails(geographyPrntDetailsList);


        List<GeographySubCityParentDetails> geographySubCityPrntDetailsList = geoMsg.getGeographyEntity().getGeographyData().getSubCityParentDetails().stream()
                .map(list -> new GeographySubCityParentDetails(list.getParentRowId(), list.getName(), list.getType(), list.getBdatype(),
                        list.getSubCityParentAlternateCode().stream()
                                .map(l -> new GeographySubCityParentAlternateCode(l.getCodeType(), l.getCode())).collect(Collectors.toList())))
                .collect(Collectors.toList());

        for (GeographySubCityParentDetails prntDetails : geographySubCityPrntDetailsList) {
            for (GeographySubCityParentAlternateCode sunCityparentAltCd : prntDetails.getGeographySubCityParentAlternateCodeDetails()) {
                if (sunCityparentAltCd.getCodeType() == null &&
                        sunCityparentAltCd.getCode() == null)
                    prntDetails.setGeographySubCityParentAlternateCodeDetails(null);

            }
        }
        if (geographySubCityPrntDetailsList.size() != 0)
            geographyInfo.setGeographySubCityParentDetails(geographySubCityPrntDetailsList);


        List<GeographyBDADetails> geographyBDADetailsList = geoMsg.getGeographyEntity().getGeographyData().getBdaDetails().stream()
                .map(list -> new GeographyBDADetails(list.getBdaRowID(), list.getName(), list.getType(), list.getBdaType(),
                        list.getBdaAlternateCodeData().stream()
                                .map(l -> new GeographyBDAAlternateCode(l.getCodeType(), l.getCode())).collect(Collectors.toList())))
                .collect(Collectors.toList());


        for (GeographyBDADetails bdaDetails : geographyBDADetailsList) {
            for (GeographyBDAAlternateCode bdaAltCdDetails : bdaDetails.getGeographyBDAAlternateCodeDetails()) {
                if (bdaAltCdDetails.getCodeType() == null &&
                        bdaAltCdDetails.getCode() == null)
                    bdaDetails.setGeographyBDAAlternateCodeDetails(null);

            }

        }
        if (geographyBDADetailsList.size() != 0)
            geographyInfo.setGeographyBDADetails(geographyBDADetailsList);


        List<GeographyBDALocationDetails> geoBDALocDetailsList = geoMsg.getGeographyEntity().getGeographyData().getBdaLocationsDetails().stream()
                .map(list -> new GeographyBDALocationDetails(list.getBdaLocRowID(), list.getName(), list.getType(), list.getStatus(),
                        list.getBDALocationAlternateCodeData().stream()
                                .map(l -> new GeographyBDALocationAlternateCode(l.getCodeType(), l.getCode())).collect(Collectors.toList())))
                .collect(Collectors.toList());


        for (GeographyBDALocationDetails bdaLocDetails : geoBDALocDetailsList) {
            for (GeographyBDALocationAlternateCode bdaLocAltCdDetails : bdaLocDetails.getGeographyBDALocationAlternateCodeDetails()) {
                if (bdaLocAltCdDetails.getCodeType() == null &&
                        bdaLocAltCdDetails.getCode() == null)
                    bdaLocDetails.setGeographyBDALocationAlternateCodeDetails(null);

            }

        }
        if (geoBDALocDetailsList.size() != 0)
            geographyInfo.setGeographyBDALocationsDetails(geoBDALocDetailsList);

        geographyEntity.setGeography(geographyInfo);
        geographyMessage.setGeographyEntity(geographyEntity);

        return geographyMessage;
    }

}
