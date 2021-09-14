package net.apmm.mdm.ops.geo.dao.model;

import java.util.List;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class GeographyParentDetailsData {
    private String parentRowId;
    private String name;
    private String type;
    private String bdatype;
    List<GeographyParentAlternateCodeData> parentAlternateCode;
}
