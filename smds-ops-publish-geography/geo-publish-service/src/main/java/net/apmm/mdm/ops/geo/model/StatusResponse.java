package net.apmm.mdm.ops.geo.model;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class StatusResponse {
    private String geoRequestId;
    private String geoRowID;
}

