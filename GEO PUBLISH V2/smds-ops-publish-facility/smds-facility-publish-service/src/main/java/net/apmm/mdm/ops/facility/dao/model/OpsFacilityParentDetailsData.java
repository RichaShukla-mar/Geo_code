package net.apmm.mdm.ops.facility.dao.model;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class OpsFacilityParentDetailsData {
    private String parentRowID;
    private String name;
    private String type;
    List<OpsFacilityParentAltCodeData> parentAltCodeData;
}
