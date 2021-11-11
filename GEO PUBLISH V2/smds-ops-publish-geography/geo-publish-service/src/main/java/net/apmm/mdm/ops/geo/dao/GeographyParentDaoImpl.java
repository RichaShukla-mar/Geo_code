package net.apmm.mdm.ops.geo.dao;

import lombok.extern.slf4j.Slf4j;
import net.apmm.mdm.ops.geo.dao.model.GeographyParentDetailsData;
import net.apmm.mdm.ops.geo.exception.DataRetrievalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class GeographyParentDaoImpl implements GeographyParentDao
{
    private final JdbcTemplate smdsJdbcTemplate;

    @Qualifier("retrieveParentByGeoRowId")
    @Autowired
    String retrieveParentByGeoRowId;

    @Autowired
    public GeographyParentDaoImpl(@Qualifier("smdsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.smdsJdbcTemplate = jdbcTemplate;
    }



    @Override
    public List<GeographyParentDetailsData> retrieveParentDetailsByGeoRowId(String geoRowID,String geoRowID1) {
        log.debug("Fetching ParentDetails for GeoRowid :: " + geoRowID + geoRowID1);
        try {
            return smdsJdbcTemplate.query(retrieveParentByGeoRowId,
                    new Object[]{geoRowID,geoRowID1},
                    new GeographyParentMapper()
            );
        } catch (Exception e) {
            throw new DataRetrievalException("Error getting ParentDetails Details:: " + e);
        }
    }
}
