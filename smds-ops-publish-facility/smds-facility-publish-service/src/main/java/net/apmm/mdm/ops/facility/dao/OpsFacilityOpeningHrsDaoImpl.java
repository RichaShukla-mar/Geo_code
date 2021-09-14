package net.apmm.mdm.ops.facility.dao;

import lombok.extern.slf4j.Slf4j;
import net.apmm.mdm.ops.facility.dao.model.OpsFacilityOpeningHoursData;
import net.apmm.mdm.ops.facility.exception.DataRetrievalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class OpsFacilityOpeningHrsDaoImpl implements OpsFacilityOpeningHrsDao {
    private final JdbcTemplate smdsJdbcTemplate;

    @Qualifier("retrieveFctOpeningHrsFctRowId")
    @Autowired
    String retrieveFctOpeningHrsFctRowId;

    @Autowired
    public OpsFacilityOpeningHrsDaoImpl(@Qualifier("smdsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.smdsJdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<OpsFacilityOpeningHoursData> retrieveFctOpeningHrsDtlFctRowId(String fctRowID) {
        log.debug("Fetching fctRowID and header Info  :: " + fctRowID);
        try {
              return  smdsJdbcTemplate.query(retrieveFctOpeningHrsFctRowId,
                    new Object[]{fctRowID},
                    new OpsFacilityOpeningHrsMapper()
            );

        } catch (Exception e) {
            throw new DataRetrievalException("Error getting Facility OpeningHrs details  :: " + e);
        }

}

}
