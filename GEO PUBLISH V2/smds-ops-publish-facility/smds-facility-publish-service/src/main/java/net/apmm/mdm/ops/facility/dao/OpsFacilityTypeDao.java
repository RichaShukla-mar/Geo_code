package net.apmm.mdm.ops.facility.dao;

import net.apmm.mdm.ops.facility.dao.model.OpsFacilityTypeData;

import java.util.List;

public interface OpsFacilityTypeDao {

    public List<OpsFacilityTypeData>  retrieveFctTypeDtlByFctOpsRowId(String fctOpsRowid);
}
