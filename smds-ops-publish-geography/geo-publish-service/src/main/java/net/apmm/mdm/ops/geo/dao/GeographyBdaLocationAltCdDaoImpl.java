package net.apmm.mdm.ops.geo.dao;

import lombok.extern.slf4j.Slf4j;
import net.apmm.mdm.ops.geo.dao.model.GeographyBDAAlternateCodeData;
import net.apmm.mdm.ops.geo.dao.model.GeographyBDALocationAlternateCodeData;
import net.apmm.mdm.ops.geo.exception.DataRetrievalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class GeographyBdaLocationAltCdDaoImpl implements GeographyBdaLocationAltCdDao {
    private final JdbcTemplate smdsJdbcTemplate;

    @Qualifier("retrieveBdaLocationAltCodeByBdaId")
    @Autowired
    String retrieveBdaLocationAltCodeByBdaId;

    @Autowired
    public GeographyBdaLocationAltCdDaoImpl(@Qualifier("smdsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.smdsJdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<GeographyBDALocationAlternateCodeData> retrieveBdaLocationdtlsAltCodeByBdaId(String bdaLocRowID) {
        log.debug("Fetching BDAALTCODE Details for GeoRowid :: " + bdaLocRowID);
        try {
            return smdsJdbcTemplate.query(retrieveBdaLocationAltCodeByBdaId,
                    new Object[]{bdaLocRowID},
                    new GeographyBdaLocationAltCdMapper()
            );
        } catch (Exception e) {
            throw new DataRetrievalException("Error getting BDAALTCODE Details :: " + e);
        }
    }

}
