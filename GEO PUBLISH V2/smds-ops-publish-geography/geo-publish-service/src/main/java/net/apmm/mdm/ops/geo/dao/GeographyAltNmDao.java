package net.apmm.mdm.ops.geo.dao;

import net.apmm.mdm.ops.geo.dao.model.GeographyAlternateNameData;


import java.util.List;

public interface GeographyAltNmDao {

    public List<GeographyAlternateNameData> retrieveAltNameDetailsByGeoRowId(String geoRowID);
}
