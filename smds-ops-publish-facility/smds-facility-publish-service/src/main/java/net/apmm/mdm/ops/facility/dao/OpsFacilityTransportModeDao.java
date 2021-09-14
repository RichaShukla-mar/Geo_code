package net.apmm.mdm.ops.facility.dao;

import net.apmm.mdm.ops.facility.dao.model.OpsFacilityTransportModesData;

import java.util.List;

public interface OpsFacilityTransportModeDao {

    public List<OpsFacilityTransportModesData> retrieveFctTransportModeDtlByFctRowId(String fctRowID);;
}
