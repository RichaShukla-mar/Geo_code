package net.apmm.mdm.ops.geo.dao;

import lombok.extern.slf4j.Slf4j;
import net.apmm.mdm.ops.geo.dao.model.GeographySubCityParentDetailsData;
import net.apmm.mdm.ops.geo.exception.DataRetrievalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class GeographySubCityParentDaoImpl implements GeographySubCityParentDao
{
    private final JdbcTemplate smdsJdbcTemplate;

    @Qualifier("retrieveSubCityParentByGeoRowId")
    @Autowired
    String retrieveSubCityParentByGeoRowId;

    @Autowired
    public GeographySubCityParentDaoImpl(@Qualifier("smdsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.smdsJdbcTemplate = jdbcTemplate;
    }



    @Override
    public List<GeographySubCityParentDetailsData> retrieveSubCityParentDetailsByGeoRowId(String geoRowID) {
        log.debug("Fetching ParentDetails for GeoRowid :: " + geoRowID);
        try {
            return smdsJdbcTemplate.query(retrieveSubCityParentByGeoRowId,
                    new Object[]{geoRowID},
                    new GeographySubCityParentMapper()
            );
        } catch (Exception e) {
            throw new DataRetrievalException("Error getting ParentDetails Details:: " + e);
        }
    }


}
