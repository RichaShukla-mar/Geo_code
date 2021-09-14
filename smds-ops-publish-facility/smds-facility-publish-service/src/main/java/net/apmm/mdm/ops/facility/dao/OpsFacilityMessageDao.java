package net.apmm.mdm.ops.facility.dao;

import lombok.extern.slf4j.Slf4j;
import net.apmm.mdm.ops.facility.dao.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Repository
@Slf4j

public class OpsFacilityMessageDao {

    @Autowired
    private OpsFacilityDao opsFacilityDao;
    @Autowired
    private OpsFacilityAddressDao opsFacilityAddressDao;
    @Autowired
    private OpsFacilityParentDao opsFacilityParentDao;
    @Autowired
    private OpsFacilityParentAltDao opsFacilityParentAltDao;
    @Autowired
    private OpsFacilityDetailsDao opsFacilityDetailsDao;
    @Autowired
    private OpsFacilityTypeDao opsFacilityTypeDao;
    @Autowired
    private OpsFacilityAlternateCdDao opsFacilityAlternateCdDao;
    @Autowired
    private OpsFacilityOpeningHrsDao opsFacilityOpeningHrsDao;
    @Autowired
    private OpsFacilityTransportModeDao opsFacilityTransportModeDao;
    @Autowired
    private OpsFacilityServicesDao opsFacilityServicesDao;
    @Autowired
    private OpsFacilityFenceDao opsFacilityFenceDao;
    @Autowired
    private OpsFacilityBdaDao opsFacilityBdaDao;
    @Autowired
    private OpsFacilityBdaAltCdDao opsFacilityBdaAltCdDao;
    @Autowired
    private OpsFacilityContactDtlsDao opsFacilityContactDtlsDao;


    @Value("#{${facility-event-details}}")
    private Map<String, String> facilityEventDetails;

    public OpsFacilityMessageData getFacilityMessage(String fctRowID, String eventDetails) throws Exception {

        List<OpsFacilityTypeData> facilityTypeData = new ArrayList<>();
        List<OpsFacilityParentAltCodeData> facilityParentAltcdData = new ArrayList<>();
        List<OpsFacilityBdaAltCdData> facilityBdaAltCdData = new ArrayList<>();

        OpsFacilityData facility = opsFacilityDao.retrieveOpsFacilityDtlByRowid(fctRowID);
        List<OpsFacilityAddressData> facilityAddressDataList = opsFacilityAddressDao.retrieveFctAddressDtlByFctRowId(fctRowID);
        List<OpsFacilityParentDetailsData> facilityParentDataList = opsFacilityParentDao.retrieveParentDtlByFctRowId(fctRowID);
        for (OpsFacilityParentDetailsData fctParentList : facilityParentDataList) {
            facilityParentAltcdData = opsFacilityParentAltDao.retrieveParentAltCodeDtlByParentId(fctParentList.getParentRowID());
            fctParentList.setParentAltCodeData(facilityParentAltcdData);

        }

        List<OpsFacilityDetailData> facilityDetailsDataList = opsFacilityDetailsDao.retrieveFctDetailsDtlByFctRowId(fctRowID,fctRowID);
        for (OpsFacilityDetailData fctDetailsList : facilityDetailsDataList) {
            facilityTypeData = opsFacilityTypeDao.retrieveFctTypeDtlByFctOpsRowId(fctDetailsList.getFctOpsRowid());
            fctDetailsList.setFacilityType(facilityTypeData);

        }
        List<OpsFacilityAlternateCodesData> facilityAltCdDataList = opsFacilityAlternateCdDao.retrieveFctAltCodeDtlByFctRowId(fctRowID);

        List<OpsFacilityOpeningHoursData> facilityOpeningHrList = opsFacilityOpeningHrsDao.retrieveFctOpeningHrsDtlFctRowId(fctRowID);
        List<OpsFacilityTransportModesData> facilityTransportDataList = opsFacilityTransportModeDao.retrieveFctTransportModeDtlByFctRowId(fctRowID);
        List<OpsFacilityServicesData> facilityServiceDataList = opsFacilityServicesDao.retrieveFctServiceDtlByFctRowId(fctRowID);
        List<OpsFacilityFenceData> facilityFenceDataList = opsFacilityFenceDao.retrieveFctFenceDtlByFctRowId(fctRowID);

        List<OpsFacilityBdaData> facilityBdaDataList = opsFacilityBdaDao.retrieveFctBdaDtlByFctRowId(fctRowID);
        for (OpsFacilityBdaData fctBdaList : facilityBdaDataList) {
            facilityBdaAltCdData = opsFacilityBdaAltCdDao.retrieveFctBdaAltCdDtlByFctRowId(fctBdaList.getBdaRowID());
            fctBdaList.setBdaAlternateCodeData(facilityBdaAltCdData);

        }

        List<OpsFacilityContactDetailData> facilityContactDataList = opsFacilityContactDtlsDao.retrieveFctContactInfoDtlByFctRowId(fctRowID);

        Random rand = new Random();

        return OpsFacilityMessageData.builder().opsFacilityEntityData(OpsFacilityEntityData.builder()
                .opsFacilityData(facility.builder()
                        .fctRowID(facility.getFctRowID())
                        .facilityName(facility.getFacilityName())
                        .facilityType(facility.getFacilityType())
                        .facilityExtOwned(facility.getFacilityExtOwned())
                        .facilityStatus(facility.getFacilityStatus())
                        .facilityExtExposed(facility.getFacilityExtExposed())
                        .facilityURL(facility.getFacilityURL())
                        .facilityDODAAC(facility.getFacilityDODAAC())
                        .facilityAddress(facilityAddressDataList)
                        .facilityParentDetails(facilityParentDataList)
                        .facilityDetail(facilityDetailsDataList)
                        .facilityAlternateCodes(facilityAltCdDataList)
                        .facilityOpeningHours(facilityOpeningHrList)
                        .facilityTransportModes(facilityTransportDataList)
                        .facilityServices(facilityServiceDataList)
                        .facilityFence(facilityFenceDataList)
                        .facilityBda(facilityBdaDataList)
                        .facilityContactDetail(facilityContactDataList)
                        .build()).build()).build();
    }

}
