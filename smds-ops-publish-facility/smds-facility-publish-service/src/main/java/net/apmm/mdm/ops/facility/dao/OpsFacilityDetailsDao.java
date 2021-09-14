package net.apmm.mdm.ops.facility.dao;



import net.apmm.mdm.ops.facility.dao.model.OpsFacilityDetailData;

import java.util.List;

public interface OpsFacilityDetailsDao {

    public List<OpsFacilityDetailData>  retrieveFctDetailsDtlByFctRowId(String fctRowID,String fctRowID1);
}
