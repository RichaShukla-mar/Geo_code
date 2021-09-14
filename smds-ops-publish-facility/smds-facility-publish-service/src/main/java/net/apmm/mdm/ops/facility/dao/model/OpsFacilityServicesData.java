package net.apmm.mdm.ops.facility.dao.model;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OpsFacilityServicesData {
    private String serviceName;
    private String serviceCode;
    private String serviceDescription;
    private String validThroughDate;

}
