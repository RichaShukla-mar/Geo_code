package net.apmm.mdm.ops.geo.dao;

import lombok.extern.slf4j.Slf4j;
import net.apmm.mdm.ops.geo.dao.model.GeographyAlternateCodesData;
import net.apmm.mdm.ops.geo.exception.DataRetrievalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@Slf4j
public class GeographyAltCdDaoImpl implements GeographyAltCdDao
{
    private final JdbcTemplate smdsJdbcTemplate;

    @Qualifier("retrieveAltCodeByGeoRowId")
    @Autowired
    String retrieveAltCodeByGeoRowId;

    @Autowired
    public GeographyAltCdDaoImpl(@Qualifier("smdsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.smdsJdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<GeographyAlternateCodesData> retrieveAltCodeDetailsByGeoRowId(String geoRowID) {
        log.debug("Fetching AltCode  Details for GeoRowid :: " + geoRowID);
        try {
            return smdsJdbcTemplate.query(retrieveAltCodeByGeoRowId,
                    new Object[]{geoRowID},
                    new GeographyAltCdMapper()
            );
        } catch (Exception e) {
            throw new DataRetrievalException("Error getting AltCode Details:: " + e);
        }
    }
}
