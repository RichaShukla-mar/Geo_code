package net.apmm.mdm.ops.geo.dao;

import net.apmm.mdm.ops.geo.dao.model.GeographyAlternateCodesData;

import java.util.List;

public interface GeographyAltCdDao {

    public List<GeographyAlternateCodesData> retrieveAltCodeDetailsByGeoRowId(String geoRowID);

}
