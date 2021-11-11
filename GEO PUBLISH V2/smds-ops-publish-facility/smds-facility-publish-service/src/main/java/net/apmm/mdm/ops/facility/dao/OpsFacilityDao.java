package net.apmm.mdm.ops.facility.dao;

import net.apmm.mdm.ops.facility.dao.model.OpsFacilityData;

public interface OpsFacilityDao {

    public OpsFacilityData retrieveOpsFacilityDtlByRowid(String fctRowID);
}
