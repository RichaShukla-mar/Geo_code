package net.apmm.mdm.ops.facility.dao.model;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OpsFacilityTypeData {
    private String code;
    private String name;
    private String masterType;
    private String validThroughDate;

}
