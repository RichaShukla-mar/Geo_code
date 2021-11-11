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

    private final KafkaTemplate<String, geographyMessage> kafkaTemplate;

    private String messageId = UUID.randomUUID().toString();


    public StatusResponse publishGeographyDetails(String geoRowID, String eventDetails) {
        try {
            log.debug(" Processing request, Processing DB queries.... ");
            GeographyMessageData geoMsg = geographyMessageDao.getGeographyMessage(geoRowID, eventDetails);
            log.debug(" Message before sending.... " + transformData(geoMsg).toString());
            final ProducerRecord<String, geographyMessage> record = new ProducerRecord<>(geoTopic, geoRowID, transformData(geoMsg));
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
            e.printStackTrace();
            throw new PublishException("Error while generating & publishing AVRO message :: " + e);
        }

    }

    private geographyMessage transformData(GeographyMessageData geoMsg) {
        //geographyEntity geographyEntity = new geographyEntity();
        geographyMessage geographyMessage = new geographyMessage();
        geography geographyInfo = new geography();

        //geographyInfo.setGeoRowID(geoMsg.getGeographyEntity().getGeographyData().getGeoRowID());
        geographyInfo.setGeoType(geoMsg.getGeographyData().getGeoType());
        geographyInfo.setName(geoMsg.getGeographyData().getName());
        geographyInfo.setStatus(geoMsg.getGeographyData().getStatus());
        geographyInfo.setValidFrom(geoMsg.getGeographyData().getValidFrom());
        geographyInfo.setValidTo(geoMsg.getGeographyData().getValidTo());
        geographyInfo.setLongitude(geoMsg.getGeographyData().getLongitude());
        geographyInfo.setLatitude(geoMsg.getGeographyData().getLatitude());
        geographyInfo.setTimeZone(geoMsg.getGeographyData().getTimeZone());
        geographyInfo.setDaylightSavingTime(geoMsg.getGeographyData().getDaylightSavingTime());
        geographyInfo.setUtcOffsetMinutes(geoMsg.getGeographyData().getUtcOffsetMinutes());
        geographyInfo.setDaylightSavingStart(geoMsg.getGeographyData().getDaylightSavingStart());
        geographyInfo.setDaylightSavingEnd(geoMsg.getGeographyData().getDaylightSavingEnd());
        geographyInfo.setDaylightSavingShiftMinutes(geoMsg.getGeographyData().getDaylightSavingShiftMinutes());
        geographyInfo.setDescription(geoMsg.getGeographyData().getDescription());
        geographyInfo.setWorkaroundReason(geoMsg.getGeographyData().getWorkaroundReason());
        geographyInfo.setRestricted(geoMsg.getGeographyData().getRestricted());

        /* Comment Start as its maintain by SITE and SITE is published by Facility schema
        geographyInfo.setSiteType(geoMsg.getGeographyEntity().getGeographyData().getSiteType());
        geographyInfo.setGpsFlag(geoMsg.getGeographyEntity().getGeographyData().getGpsFlag());
        geographyInfo.setGsmFlag(geoMsg.getGeographyEntity().getGeographyData().getGsmFlag());
        geographyInfo.setStreetNumber(geoMsg.getGeographyEntity().getGeographyData().getStreetNumber());
        geographyInfo.setAddressLine1(geoMsg.getGeographyEntity().getGeographyData().getAddressLine1());
        geographyInfo.setAddressLine2(geoMsg.getGeographyEntity().getGeographyData().getAddressLine2());
        geographyInfo.setAddressLine3(geoMsg.getGeographyEntity().getGeographyData().getAddressLine3());
        geographyInfo.setPostalCode(geoMsg.getGeographyEntity().getGeographyData().getPostalCode());
        Comment End as its maintain by SITE and SITE is published by Facility schema*/

        geographyInfo.setPostalCodeMandatoryFlag(geoMsg.getGeographyData().getPostalCodeMandatoryFlag());
        geographyInfo.setStateProvienceMandatory(geoMsg.getGeographyData().getStateProvinceMandatory());
        geographyInfo.setDialingCode(geoMsg.getGeographyData().getDialingCode());
        geographyInfo.setDialingCodedescription(geoMsg.getGeographyData().getDialingCodeDescription());
        geographyInfo.setPortFlag(geoMsg.getGeographyData().getPortFlag());
        geographyInfo.setOlsonTimezone(geoMsg.getGeographyData().getOlsonTimezone());
        geographyInfo.setBdaType(geoMsg.getGeographyData().getBdaType());
        geographyInfo.setHsudName(geoMsg.getGeographyData().getHsudName());


        log.info("geographyInfo message Out", geographyInfo);


        List<alternateNames> geographyAlternateNamesList = geoMsg.getGeographyData().getAlternateNames().stream()
                .map(list -> new alternateNames(list.getName(), list.getDescription(), list.getStatus())).collect(Collectors.toList());

        if (geographyAlternateNamesList.size() != 0)
            geographyInfo.setAlternateNames(geographyAlternateNamesList);


        List<alternateCodes> geographyAlternateCodesList = geoMsg.getGeographyData().getAlternateCodes().stream()
                .map(list -> new alternateCodes(list.getCodeType(), list.getCode())).collect(Collectors.toList());

        if (geographyAlternateCodesList.size() != 0)
            geographyInfo.setAlternateCodes(geographyAlternateCodesList);

/*
        List<GeographyFence> geographyFenceList = geoMsg.getGeographyEntity().getGeographyData().getFence().stream()
                .map(list -> new GeographyFence(list.getName(), list.getGeoFenceType())).collect(Collectors.toList());

        if (geographyFenceList.size() != 0)
            geographyInfo.setGeographyFence(geographyFenceList);*/

        List<country> geographyCountryDetailsList = geoMsg.getGeographyData().getCountry().stream()
                .map(list -> new country(list.getName(), list.getType(),
                        list.getCountryAltCdData().stream()
                                .map(l -> new countryAlternateCodes(l.getCodeType(), l.getCode())).collect(Collectors.toList())))
                .collect(Collectors.toList());

        for (country countryDetails : geographyCountryDetailsList) {
            for (countryAlternateCodes cntryAltCdDetails : countryDetails.getAlternateCodes()) {
                if (cntryAltCdDetails.getCodeType() == null &&
                        cntryAltCdDetails.getCode() == null)
                    countryDetails.setAlternateCodes(null);

            }

        }
        if (geographyCountryDetailsList.size() != 0)
            geographyInfo.setCountry(geographyCountryDetailsList);


        List<parent> geographyPrntDetailsList = geoMsg.getGeographyData().getParentDetails().stream()
                .map(list -> new parent(list.getName(), list.getType(), list.getBdatype(),
                        list.getParentAlternateCode().stream()
                                .map(l -> new parentAlternateCode(l.getCodeType(), l.getCode())).collect(Collectors.toList())))
                .collect(Collectors.toList());

        for (parent prntDetails : geographyPrntDetailsList) {
            for (parentAlternateCode parentAltCd : prntDetails.getAlternateCodes()) {
                if (parentAltCd.getCodeType() == null &&
                        parentAltCd.getCode() == null)
                    prntDetails.setAlternateCodes(null);

            }
        }
        if (geographyPrntDetailsList.size() != 0)
            geographyInfo.setParent(geographyPrntDetailsList);


        List<subCityParent> geographySubCityPrntDetailsList = geoMsg.getGeographyData().getSubCityParentDetails().stream()
                .map(list -> new subCityParent(list.getName(), list.getType(), list.getBdatype(),
                        list.getSubCityParentAlternateCode().stream()
                                .map(l -> new subCityParentAlternateCode(l.getCodeType(), l.getCode())).collect(Collectors.toList())))
                .collect(Collectors.toList());

        for (subCityParent prntDetails : geographySubCityPrntDetailsList) {
            for (subCityParentAlternateCode sunCityparentAltCd : prntDetails.getAlternateCodes()) {
                if (sunCityparentAltCd.getCodeType() == null &&
                        sunCityparentAltCd.getCode() == null)
                    prntDetails.setAlternateCodes(null);

            }
        }
        if (geographySubCityPrntDetailsList.size() != 0)
            geographyInfo.setSubCityParent(geographySubCityPrntDetailsList);


        List<bda> geographyBDADetailsList = geoMsg.getGeographyData().getBdaDetails().stream()
                .map(list -> new bda( list.getName(), list.getType(), list.getBdaType(),
                        list.getBdaAlternateCodeData().stream()
                                .map(l -> new bdaAlternateCode(l.getCodeType(), l.getCode())).collect(Collectors.toList())))
                .collect(Collectors.toList());


        for (bda bdaDetails : geographyBDADetailsList) {
            for (bdaAlternateCode bdaAltCdDetails : bdaDetails.getAlternateCodes()) {
                if (bdaAltCdDetails.getCodeType() == null &&
                        bdaAltCdDetails.getCode() == null)
                    bdaDetails.setAlternateCodes(null);

            }

        }
        if (geographyBDADetailsList.size() != 0)
            geographyInfo.setBda(geographyBDADetailsList);


        List<bdaLocation> geoBDALocDetailsList = geoMsg.getGeographyData().getBdaLocationsDetails().stream()
                .map(list -> new bdaLocation( list.getName(), list.getType(), list.getStatus(),
                        list.getBDALocationAlternateCodeData().stream()
                                .map(l -> new bdaLocationAlternateCode(l.getCodeType(), l.getCode())).collect(Collectors.toList())))
                .collect(Collectors.toList());


        for (bdaLocation bdaLocDetails : geoBDALocDetailsList) {
            for (bdaLocationAlternateCode bdaLocAltCdDetails : bdaLocDetails.getAlternateCodes()) {
                if (bdaLocAltCdDetails.getCodeType() == null &&
                        bdaLocAltCdDetails.getCode() == null)
                    bdaLocDetails.setAlternateCodes(null);

            }

        }
        if (geoBDALocDetailsList.size() != 0)
            geographyInfo.setBdaLocations(geoBDALocDetailsList);

        //geographyEntity.setGeography(geographyInfo);
        //geographyMessage.setGeographyEntity(geographyEntity);
        geographyMessage.setGeography(geographyInfo);

        return geographyMessage;
    }

}
