package net.apmm.mdm.ops.geo.dao;

import net.apmm.mdm.ops.geo.dao.model.GeographyData;

public interface GeographyDao {

    public GeographyData retrieveGeographyDetailsByRowid(String geoRowID);
}
