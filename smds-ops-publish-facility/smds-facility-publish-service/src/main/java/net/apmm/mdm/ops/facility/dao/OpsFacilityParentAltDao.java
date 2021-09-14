package net.apmm.mdm.ops.facility.dao;

import net.apmm.mdm.ops.facility.dao.model.OpsFacilityParentAltCodeData;

import java.util.List;

public interface OpsFacilityParentAltDao {

    public List<OpsFacilityParentAltCodeData>  retrieveParentAltCodeDtlByParentId(String fctRowID);
}
