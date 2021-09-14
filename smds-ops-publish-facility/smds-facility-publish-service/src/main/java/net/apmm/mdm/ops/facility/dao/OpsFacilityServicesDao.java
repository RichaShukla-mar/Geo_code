package net.apmm.mdm.ops.facility.dao;

import net.apmm.mdm.ops.facility.dao.model.OpsFacilityServicesData;


import java.util.List;

public interface OpsFacilityServicesDao {

    public List<OpsFacilityServicesData> retrieveFctServiceDtlByFctRowId(String fctRowID);;
}
