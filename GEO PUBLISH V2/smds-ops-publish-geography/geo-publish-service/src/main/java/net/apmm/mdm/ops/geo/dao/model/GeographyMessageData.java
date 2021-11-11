package net.apmm.mdm.ops.geo.dao.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GeographyMessageData {
   /* private GeographyEntityData geographyEntity;*/
    private GeographyData geographyData;
}
