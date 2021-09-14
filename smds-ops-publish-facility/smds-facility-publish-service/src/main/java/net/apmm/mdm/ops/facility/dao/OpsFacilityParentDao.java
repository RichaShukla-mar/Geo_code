package net.apmm.mdm.ops.facility.dao;

import net.apmm.mdm.ops.facility.dao.model.OpsFacilityParentDetailsData;

import java.util.List;

public interface OpsFacilityParentDao {

    public List<OpsFacilityParentDetailsData>  retrieveParentDtlByFctRowId(String fctRowID);
}
