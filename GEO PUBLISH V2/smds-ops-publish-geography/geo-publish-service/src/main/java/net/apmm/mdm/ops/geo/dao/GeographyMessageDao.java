package net.apmm.mdm.ops.geo.dao;


import lombok.extern.slf4j.Slf4j;
import net.apmm.mdm.ops.geo.dao.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Repository
@Slf4j
public class GeographyMessageDao {
    @Autowired
    private GeographyDao geographyDao;
    @Autowired
    private GeographyAltNmDao GeographyAltNmDao;
    @Autowired
    private GeographyAltCdDao GeographyAltCdDao;
    @Autowired
    private GeographyFenceDao GeographyFenceDao;
    @Autowired
    private GeographyCountryDao GeographyCountryDao;
    @Autowired
    private GeographyCountryAltCdDao GeographyCountryAltCdDao;
    @Autowired
    private GeographyParentDao GeographyParentDao;
    @Autowired
    private GeographyParentAltCdDao GeographyParentAltCdDao;
    @Autowired
    private GeographySubCityParentDao GeographySubCityParentDao;
    @Autowired
    private GeographySubCityParentAltCdDao GeographySubCityParentAltCdDao;
    @Autowired
    private GeographyBdaDao GeographyBdaDao;
    @Autowired
    private GeographyBdaAltCdDao GeographyBdaAltCdDao;
    @Autowired
    private GeographyBdaLocationDao GeographyBdaLocationDao;
    @Autowired
    private GeographyBdaLocationAltCdDao GeographyBdaLocationAltCdDao;

    @Value("#{${geo-event-details}}")
    private Map<String, String> geoEventDetails;

public GeographyMessageData getGeographyMessage(String geoRowID, String eventDetails) throws Exception {

    List<GeographyCountryAltCdData> countryAltCdData = new ArrayList<>();
    List<GeographyParentAlternateCodeData> parentAltCdData = new ArrayList<>();
    List<GeographySubCityParentAlternateCodeData> subCityParentAltCdData = new ArrayList<>();
    List<GeographyBDAAlternateCodeData> bdaAlternateCodeData = new ArrayList<>();
    List<GeographyBDALocationAlternateCodeData> bdaLocationAltCdData = new ArrayList<>();

    GeographyData geography = geographyDao.retrieveGeographyDetailsByRowid(geoRowID);
    List<GeographyAlternateNameData> geoAlternateNamesList = GeographyAltNmDao.retrieveAltNameDetailsByGeoRowId(geoRowID);
    List<GeographyAlternateCodesData> geoAlternateCodeList = GeographyAltCdDao.retrieveAltCodeDetailsByGeoRowId(geoRowID);
    List<GeographyFenceData> geographyFenceList = GeographyFenceDao.retrieveFenceDetailsbyGeoRowId(geoRowID);
    List<GeographyCountryData> geographyCountryList = GeographyCountryDao.retrieveCountryDetailsByGeoRowId(geoRowID,geoRowID,geoRowID,geoRowID);
    for (GeographyCountryData countryList : geographyCountryList) {
        countryAltCdData = GeographyCountryAltCdDao.retrieveCountryAltCodeDetailsByCountryId(countryList.getCountryRowID());
        countryList.setCountryAltCdData(countryAltCdData);
    }

    List<GeographyParentDetailsData> parentDetailsList = GeographyParentDao.retrieveParentDetailsByGeoRowId(geoRowID,geoRowID);
    for (GeographyParentDetailsData parentRowidList : parentDetailsList) {
        parentAltCdData = GeographyParentAltCdDao.retrieveParentAltCodeDtlsByParentId(parentRowidList.getParentRowId());
        parentRowidList.setParentAlternateCode(parentAltCdData);
    }

    List<GeographySubCityParentDetailsData> subcityparentDetailsList = GeographySubCityParentDao.retrieveSubCityParentDetailsByGeoRowId(geoRowID);
    for (GeographySubCityParentDetailsData subcityparentRowidList : subcityparentDetailsList) {
        subCityParentAltCdData = GeographySubCityParentAltCdDao.retrieveSubCityParentAltCodeDtlsByParentId(subcityparentRowidList.getParentRowId());
        subcityparentRowidList.setSubCityParentAlternateCode(subCityParentAltCdData);
    }
    List<GeographyBDADetailsData> bdaDetailsList = GeographyBdaDao.retrieveBdaDetailsByGeoRowId(geoRowID);
    for (GeographyBDADetailsData bdaRowidList : bdaDetailsList) {
        bdaAlternateCodeData = GeographyBdaAltCdDao.retrieveBdaAltDetailsCodeByBdaId(bdaRowidList.getBdaRowID());
        bdaRowidList.setBdaAlternateCodeData(bdaAlternateCodeData);
    }
    List<GeographyBDALocationsDetailsData> bdaLocDetailsList = GeographyBdaLocationDao.retrieveBdaLocationDtlsByGeoRowId(geoRowID);
    for (GeographyBDALocationsDetailsData bdaLocRowidList : bdaLocDetailsList) {
        bdaLocationAltCdData = GeographyBdaLocationAltCdDao.retrieveBdaLocationdtlsAltCodeByBdaId(bdaLocRowidList.getBdaLocRowID());
        bdaLocRowidList.setBDALocationAlternateCodeData(bdaLocationAltCdData);
    }

    Random rand = new Random();

    return GeographyMessageData.builder().geographyData(geography.builder()
                    .geoRowID(geography.getGeoRowID())
                    .geoType(geography.getGeoType())
                    .name(geography.getName())
                    .status(geography.getStatus())
                    .validFrom(geography.getValidFrom())
                    .validTo(geography.getValidTo())
                    .longitude(geography.getLongitude())
                    .latitude(geography.getLatitude())
                    .timeZone(geography.getTimeZone())
                    .daylightSavingTime(geography.getDaylightSavingTime())
                    .utcOffsetMinutes(geography.getUtcOffsetMinutes())
                    .daylightSavingStart(geography.getDaylightSavingStart())
                    .daylightSavingEnd(geography.getDaylightSavingEnd())
                    .daylightSavingShiftMinutes(geography.getDaylightSavingShiftMinutes())
                    .description(geography.getDescription())
                    .workaroundReason(geography.getWorkaroundReason())
                    .restricted(geography.getRestricted())
                    .siteType(geography.getSiteType())
                    .gpsFlag(geography.getGpsFlag())
                    .gsmFlag(geography.getGsmFlag())
                    .streetNumber(geography.getStreetNumber())
                    .addressLine1(geography.getAddressLine1())
                    .addressLine2(geography.getAddressLine2())
                    .addressLine3(geography.getAddressLine3())
                    .postalCode(geography.getPostalCode())
                    .postalCodeMandatoryFlag(geography.getPostalCodeMandatoryFlag())
                    .stateProvinceMandatory(geography.getStateProvinceMandatory())
                    .dialingCode(geography.getDialingCode())
                    .dialingCodeDescription(geography.getDialingCodeDescription())
                    .portFlag(geography.getPortFlag())
                    .olsonTimezone(geography.getOlsonTimezone())
                    .bdaType(geography.getBdaType())
                    .hsudName(geography.getHsudName())
                    .alternateNames(geoAlternateNamesList)
                    .alternateCodes(geoAlternateCodeList)
                    .fence(geographyFenceList)
                    .country(geographyCountryList)
                    .parentDetails(parentDetailsList)
                    .subCityParentDetails(subcityparentDetailsList)
                    .bdaDetails(bdaDetailsList)
                    .bdaLocationsDetails(bdaLocDetailsList).build()).build();


}


}
