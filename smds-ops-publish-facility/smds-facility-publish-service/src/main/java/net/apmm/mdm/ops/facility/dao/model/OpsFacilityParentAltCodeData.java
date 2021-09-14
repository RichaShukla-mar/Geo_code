package net.apmm.mdm.ops.facility.dao.model;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class OpsFacilityParentAltCodeData {
    private String codeType;
    private String code;
   }
