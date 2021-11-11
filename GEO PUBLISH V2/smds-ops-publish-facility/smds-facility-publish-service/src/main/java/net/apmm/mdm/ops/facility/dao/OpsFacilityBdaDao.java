package net.apmm.mdm.ops.facility.dao;


import net.apmm.mdm.ops.facility.dao.model.OpsFacilityBdaData;

import java.util.List;

public interface OpsFacilityBdaDao {

    public List<OpsFacilityBdaData> retrieveFctBdaDtlByFctRowId(String fctRowID);;
}
