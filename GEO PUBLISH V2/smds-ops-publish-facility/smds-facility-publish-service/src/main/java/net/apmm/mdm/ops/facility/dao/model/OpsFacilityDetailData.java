package net.apmm.mdm.ops.facility.dao.model;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OpsFacilityDetailData {
    private String fctOpsRowid;
    private String weightLimitCraneKg;
    private String weightLimitYardKg;
    private String vesselAgent;
    private String gpsFlag;
    private String gsmFlag;
    private String oceanFreightPricing;
    private String commFacilityBrand;
    private String commFacilityType;
    private String commExportEnquiriesEmail;
    private String commImportEnquiriesEmail;
    private String commFacilityFunction;
    private String commFacilityFunctionDesc;
    private String commInternationalDialCode;
    private String telephoneNumber;
    List<OpsFacilityTypeData> facilityType;
}
