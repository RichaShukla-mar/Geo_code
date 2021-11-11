package net.apmm.mdm.ops.geo.dao.model;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class GeographyFenceData {
    private String name;
    private String geoFenceType;
}
