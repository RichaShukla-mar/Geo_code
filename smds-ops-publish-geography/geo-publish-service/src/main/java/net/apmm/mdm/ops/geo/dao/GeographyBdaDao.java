package net.apmm.mdm.ops.geo.dao;


import net.apmm.mdm.ops.geo.dao.model.GeographyBDADetailsData;

import java.util.List;

public interface GeographyBdaDao {

    public List<GeographyBDADetailsData> retrieveBdaDetailsByGeoRowId(String geoRowID);

}
