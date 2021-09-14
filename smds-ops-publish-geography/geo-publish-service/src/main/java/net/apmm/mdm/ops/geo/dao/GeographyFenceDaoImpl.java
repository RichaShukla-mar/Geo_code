package net.apmm.mdm.ops.geo.dao;

import lombok.extern.slf4j.Slf4j;
import net.apmm.mdm.ops.geo.dao.model.GeographyFenceData;
import net.apmm.mdm.ops.geo.exception.DataRetrievalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class GeographyFenceDaoImpl implements GeographyFenceDao
{
    private final JdbcTemplate smdsJdbcTemplate;

    @Qualifier("retrieveFencebyGeoRowId")
    @Autowired
    String retrieveFencebyGeoRowId;

    @Autowired
    public GeographyFenceDaoImpl(@Qualifier("smdsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.smdsJdbcTemplate = jdbcTemplate;
    }



    @Override
    public List<GeographyFenceData> retrieveFenceDetailsbyGeoRowId(String geoRowID) {
        log.debug("Fetching GeoFence Details for GeoRowid :: " + geoRowID);
        try {
            return smdsJdbcTemplate.query(retrieveFencebyGeoRowId,
                    new Object[]{geoRowID},
                    new GeographyFenceMapper()
            );
        } catch (Exception e) {
            throw new DataRetrievalException("Error getting GeoFence Details:: " + e);
        }
    }
}
