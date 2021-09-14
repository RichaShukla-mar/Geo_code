package net.apmm.mdm.ops.geo.dao.model;

import java.util.List;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class GeographyCountryData {
    private String countryRowID;
    private String name;
    private String type;
    List<GeographyCountryAltCdData> countryAltCdData;
}
