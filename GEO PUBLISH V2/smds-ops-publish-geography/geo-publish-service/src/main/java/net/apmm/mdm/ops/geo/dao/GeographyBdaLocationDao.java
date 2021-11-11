package net.apmm.mdm.ops.geo.dao;



import net.apmm.mdm.ops.geo.dao.model.GeographyBDALocationsDetailsData;

import java.util.List;

public interface GeographyBdaLocationDao {

    public List<GeographyBDALocationsDetailsData> retrieveBdaLocationDtlsByGeoRowId(String geoRowID);

}
