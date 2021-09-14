package net.apmm.mdm.ops.facility.dao;

import net.apmm.mdm.ops.facility.dao.model.OpsFacilityBdaAltCdData;

import java.util.List;

public interface OpsFacilityBdaAltCdDao {

    public List<OpsFacilityBdaAltCdData> retrieveFctBdaAltCdDtlByFctRowId(String fctRowID);;
}
