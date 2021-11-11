package net.apmm.mdm.ops.facility.dao;

import net.apmm.mdm.ops.facility.dao.model.OpsFacilityContactDetailData;

import java.util.List;

public interface OpsFacilityContactDtlsDao {

    public List<OpsFacilityContactDetailData> retrieveFctContactInfoDtlByFctRowId(String fctRowID);;
}
