package net.apmm.mdm.ops.facility.dao;

import lombok.extern.slf4j.Slf4j;
import net.apmm.mdm.ops.facility.dao.model.OpsFacilityDetailData;
import net.apmm.mdm.ops.facility.exception.DataRetrievalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class OpsFacilityDetailsDaoImpl implements OpsFacilityDetailsDao {

    private final JdbcTemplate smdsJdbcTemplate;

    @Qualifier("retrieveFctDetailsByFctRowId")
    @Autowired
    String retrieveFctDetailsByFctRowId;

    @Autowired
    public OpsFacilityDetailsDaoImpl(@Qualifier("smdsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.smdsJdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<OpsFacilityDetailData> retrieveFctDetailsDtlByFctRowId(String fctRowID,String fctRowID1) {
        log.debug("Fetching ParentDetails for fctRowID :: " + fctRowID);
        try {
            return smdsJdbcTemplate.query(retrieveFctDetailsByFctRowId,
                    new Object[]{fctRowID,fctRowID1},
                    new OpsFacilityDetailsMapper()
            );
        } catch (Exception e) {
            throw new DataRetrievalException("Error getting FacilityDetails Info:: " + e);
        }
    }
}
