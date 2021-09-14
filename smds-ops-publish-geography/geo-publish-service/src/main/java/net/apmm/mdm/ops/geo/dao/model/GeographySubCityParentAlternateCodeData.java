package net.apmm.mdm.ops.geo.dao.model;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class GeographySubCityParentAlternateCodeData {
    private String codeType;
    private String code;
}
