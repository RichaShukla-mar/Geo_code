package net.apmm.mdm.ops.geo.dao;




import net.apmm.mdm.ops.geo.dao.model.GeographyBDAAlternateCodeData;

import java.util.List;

public interface GeographyBdaAltCdDao {

    public List<GeographyBDAAlternateCodeData> retrieveBdaAltDetailsCodeByBdaId(String bdaRowID);

}
