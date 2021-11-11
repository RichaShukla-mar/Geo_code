package net.apmm.mdm.ops.facility.dao.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class OpsFacilityData {
    private String fctRowID;
    private String facilityName;
    private String facilityType;
    private String facilityExtOwned;
    private String facilityStatus;
    private String facilityExtExposed;
    private String facilityURL;
    private String facilityDODAAC;
    List<OpsFacilityAddressData> facilityAddress;
    List<OpsFacilityParentDetailsData> facilityParentDetails;
    List<OpsFacilityDetailData> facilityDetail;
    List<OpsFacilityAlternateCodesData> facilityAlternateCodes;
    List<OpsFacilityOpeningHoursData> facilityOpeningHours;
    List<OpsFacilityTransportModesData> facilityTransportModes;
    List<OpsFacilityServicesData> facilityServices;
    List<OpsFacilityFenceData> facilityFence;
    List<OpsFacilityBdaData> facilityBda;
    List<OpsFacilityContactDetailData> facilityContactDetail;


}
