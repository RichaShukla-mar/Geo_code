package net.apmm.mdm.ops.facility.dao;

import lombok.extern.slf4j.Slf4j;

import net.apmm.mdm.ops.facility.dao.model.OpsFacilityData;
import net.apmm.mdm.ops.facility.exception.DataRetrievalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j

public class OpsFacilityDaoImpl implements OpsFacilityDao {

    private final JdbcTemplate smdsJdbcTemplate;

    @Qualifier("retrieveOpsFctByRowId")
    @Autowired
    String retrieveOpsFctByRowId;

    @Autowired
    public OpsFacilityDaoImpl(@Qualifier("smdsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.smdsJdbcTemplate = jdbcTemplate;
    }

    public OpsFacilityData retrieveOpsFacilityDtlByRowid(String fctRowID) {
        log.debug("Fetching fctRowID and header Info  :: " + fctRowID);
        try{
            List<OpsFacilityData> OpsFacilityData = (List<OpsFacilityData>) smdsJdbcTemplate.query(retrieveOpsFctByRowId,
                    new Object[]{fctRowID},
                    new OpsFacilityMapper()
            );
            return OpsFacilityData.get(0);
        }
        catch (IndexOutOfBoundsException e) {
            throw new DataRetrievalException("fctRowID not found :: " + e);
        }

        catch (Exception e) {
            throw new DataRetrievalException("Error getting GeoData details  :: " + e);
        }
 }
}
