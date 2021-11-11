package net.apmm.mdm.ops.facility.dao;

import net.apmm.mdm.ops.facility.dao.model.OpsFacilityAlternateCodesData;
import net.apmm.mdm.ops.facility.dao.model.OpsFacilityOpeningHoursData;

import java.util.List;

public interface OpsFacilityOpeningHrsDao {

    public List<OpsFacilityOpeningHoursData> retrieveFctOpeningHrsDtlFctRowId(String fctRowID);;
}
