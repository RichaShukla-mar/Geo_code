package net.apmm.mdm.ops.facility.dao;

import lombok.extern.slf4j.Slf4j;

import net.apmm.mdm.ops.facility.dao.model.OpsFacilityParentAltCodeData;
import net.apmm.mdm.ops.facility.exception.DataRetrievalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class OpsFacilityParentAltDaoImpl implements OpsFacilityParentAltDao {

    private final JdbcTemplate smdsJdbcTemplate;

    @Qualifier("retrieveParentAltCodeByParentId")
    @Autowired
    String retrieveParentAltCodeByParentId;

    @Autowired
    public OpsFacilityParentAltDaoImpl(@Qualifier("smdsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.smdsJdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<OpsFacilityParentAltCodeData> retrieveParentAltCodeDtlByParentId(String fctRowID) {
        log.debug("Fetching ParentDetails for GeoRowid :: " + fctRowID);
        try {
            return smdsJdbcTemplate.query(retrieveParentAltCodeByParentId,
                    new Object[]{fctRowID},
                    new OpsFacilityParentAltMapper()
            );
        } catch (Exception e) {
            throw new DataRetrievalException("Error getting ParentAltDetails Details:: " + e);
        }
    }
}
