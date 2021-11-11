package net.apmm.mdm.ops.facility.dao;

import net.apmm.mdm.ops.facility.dao.model.OpsFacilityAddressData;
import net.apmm.mdm.ops.facility.dao.model.OpsFacilityAlternateCodesData;

import java.util.List;

public interface OpsFacilityAlternateCdDao {

    public List<OpsFacilityAlternateCodesData> retrieveFctAltCodeDtlByFctRowId(String fctRowID);;
}
