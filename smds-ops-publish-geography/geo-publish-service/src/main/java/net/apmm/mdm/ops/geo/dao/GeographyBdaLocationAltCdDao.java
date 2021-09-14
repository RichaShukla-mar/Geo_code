package net.apmm.mdm.ops.geo.dao;

import net.apmm.mdm.ops.geo.dao.model.GeographyBDALocationAlternateCodeData;

import java.util.List;

public interface GeographyBdaLocationAltCdDao {

    public List<GeographyBDALocationAlternateCodeData> retrieveBdaLocationdtlsAltCodeByBdaId(String bdaLocRowID);

}
