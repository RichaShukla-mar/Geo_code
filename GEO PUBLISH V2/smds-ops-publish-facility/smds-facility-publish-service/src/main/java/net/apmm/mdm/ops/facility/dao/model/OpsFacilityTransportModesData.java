package net.apmm.mdm.ops.facility.dao.model;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class OpsFacilityTransportModesData {
    private String transportMode;
    private String transportCode;
    private String transportDescription;
    private long validThroughDate;

}
