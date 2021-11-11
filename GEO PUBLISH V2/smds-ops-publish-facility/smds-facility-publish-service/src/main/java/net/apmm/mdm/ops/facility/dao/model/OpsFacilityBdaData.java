package net.apmm.mdm.ops.facility.dao.model;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OpsFacilityBdaData {
    private String bdaRowID;
    private String name;
    private String type;
    private String bdaType;
    List<OpsFacilityBdaAltCdData> bdaAlternateCodeData;
}
