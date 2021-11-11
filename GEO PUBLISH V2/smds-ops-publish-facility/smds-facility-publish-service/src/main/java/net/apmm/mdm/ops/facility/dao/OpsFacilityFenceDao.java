package net.apmm.mdm.ops.facility.dao;

import net.apmm.mdm.ops.facility.dao.model.OpsFacilityFenceData;

import java.util.List;

public interface OpsFacilityFenceDao {

    public List<OpsFacilityFenceData> retrieveFctFenceDtlByFctRowId(String fctRowID);;
}
