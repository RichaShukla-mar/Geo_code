package net.apmm.mdm.ops.geo.dao;


import net.apmm.mdm.ops.geo.dao.model.GeographyFenceData;

import java.util.List;

public interface GeographyFenceDao {

    public List<GeographyFenceData> retrieveFenceDetailsbyGeoRowId(String geoRowID);

}
