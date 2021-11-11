package net.apmm.mdm.ops.facility.dao.model;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OpsFacilityFenceData {
    private String name;
    private String fenceType;
}
