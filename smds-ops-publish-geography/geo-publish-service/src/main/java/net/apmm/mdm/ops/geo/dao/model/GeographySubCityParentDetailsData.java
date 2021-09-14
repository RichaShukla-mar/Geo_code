package net.apmm.mdm.ops.geo.dao.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class GeographySubCityParentDetailsData {
    private String parentRowId;
    private String name;
    private String type;
    private String bdatype;
    List<GeographySubCityParentAlternateCodeData> subCityParentAlternateCode;
}
