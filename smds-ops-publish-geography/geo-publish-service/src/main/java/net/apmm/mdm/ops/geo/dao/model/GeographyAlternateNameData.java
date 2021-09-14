package net.apmm.mdm.ops.geo.dao.model;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class GeographyAlternateNameData {
    private String name;
    private String description;
    private String status;
}
