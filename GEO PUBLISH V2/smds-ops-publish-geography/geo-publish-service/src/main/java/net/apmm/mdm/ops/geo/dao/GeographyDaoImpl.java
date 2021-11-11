package net.apmm.mdm.ops.geo.dao;

import lombok.extern.slf4j.Slf4j;
import net.apmm.mdm.ops.geo.dao.model.GeographyData;
import net.apmm.mdm.ops.geo.exception.DataRetrievalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class GeographyDaoImpl implements GeographyDao{

    private final JdbcTemplate smdsJdbcTemplate;

    @Qualifier("retrieveGeographyByRowId")
    @Autowired
    String retrieveGeographyByRowId;

    @Autowired
   public GeographyDaoImpl(@Qualifier("smdsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.smdsJdbcTemplate = jdbcTemplate;
    }


    public GeographyData retrieveGeographyDetailsByRowid(String geoRowID) {
        log.debug("Fetching geoRowID and header Info  :: " + geoRowID);
        try{
            List<GeographyData> GeographyData = (List<GeographyData>) smdsJdbcTemplate.query(retrieveGeographyByRowId,
                    new Object[]{geoRowID},
                    new GeographyMapper()
            );
        return GeographyData.get(0);
    }
       catch (IndexOutOfBoundsException e) {
        throw new DataRetrievalException("GeoRowID not found :: " + e);
    }

        catch (Exception e) {
        throw new DataRetrievalException("Error getting GeoData details  :: " + e);
    }


    }
}
