package net.apmm.mdm.ops.geo.dao.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class GeographyBDALocationsDetailsData {
    private String bdaLocRowID;
    private String name;
    private String type;
    private String status;
    List<GeographyBDALocationAlternateCodeData> BDALocationAlternateCodeData;
}
