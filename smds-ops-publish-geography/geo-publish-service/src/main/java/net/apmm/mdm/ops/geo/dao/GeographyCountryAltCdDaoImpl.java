package net.apmm.mdm.ops.geo.dao;

import lombok.extern.slf4j.Slf4j;
import net.apmm.mdm.ops.geo.dao.model.GeographyCountryAltCdData;
import net.apmm.mdm.ops.geo.exception.DataRetrievalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class GeographyCountryAltCdDaoImpl implements GeographyCountryAltCdDao
{
    private final JdbcTemplate smdsJdbcTemplate;

    @Qualifier("retrieveCountryAltCodeByCountryId")
    @Autowired
    String retrieveCountryAltCodeByCountryId;

    @Autowired
    public GeographyCountryAltCdDaoImpl(@Qualifier("smdsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.smdsJdbcTemplate = jdbcTemplate;
    }



    @Override
    public List<GeographyCountryAltCdData> retrieveCountryAltCodeDetailsByCountryId(String countryRowID) {
        log.debug("Fetching CountryAlt Details for GeoRowid :: " + countryRowID);
        try {
            return smdsJdbcTemplate.query(retrieveCountryAltCodeByCountryId,
                    new Object[]{countryRowID},
                    new GeographyCountryAltCdMapper()
            );
        } catch (Exception e) {
            throw new DataRetrievalException("Error getting CountryAltDetails Details:: " + e);
        }
    }
}
