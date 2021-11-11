package net.apmm.mdm.ops.facility.dao;

import net.apmm.mdm.ops.facility.dao.model.OpsFacilityAddressData;

import java.util.List;

public interface OpsFacilityAddressDao {

    public List<OpsFacilityAddressData> retrieveFctAddressDtlByFctRowId(String fctRowID);;
}
