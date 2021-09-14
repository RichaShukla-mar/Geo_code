package net.apmm.mdm.ops.geo.dao.model;

import java.util.List;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class GeographyBDADetailsData {
    private String bdaRowID;
    private String name;
    private String type;
    private String bdaType;
    List<GeographyBDAAlternateCodeData> bdaAlternateCodeData;
}
