package net.apmm.mdm.ops.geo.dao.model;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class GeographyCountryAltCdData {
    private String codeType;
    private String code;

}
