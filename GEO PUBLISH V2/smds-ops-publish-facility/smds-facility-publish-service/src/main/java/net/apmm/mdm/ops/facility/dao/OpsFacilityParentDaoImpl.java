package net.apmm.mdm.ops.facility.dao;

import lombok.extern.slf4j.Slf4j;

import net.apmm.mdm.ops.facility.dao.model.OpsFacilityParentDetailsData;
import net.apmm.mdm.ops.facility.exception.DataRetrievalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class OpsFacilityParentDaoImpl implements OpsFacilityParentDao {

    private final JdbcTemplate smdsJdbcTemplate;

    @Qualifier("retrieveParentByFctRowId")
    @Autowired
    String retrieveParentByFctRowId;

    @Autowired
    public OpsFacilityParentDaoImpl(@Qualifier("smdsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.smdsJdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<OpsFacilityParentDetailsData> retrieveParentDtlByFctRowId(String fctRowID) {
        log.debug("Fetching ParentDetails for GeoRowid :: " + fctRowID);
        try {
            return smdsJdbcTemplate.query(retrieveParentByFctRowId,
                    new Object[]{fctRowID},
                    new OpsFacilityParentMapper()
            );
        } catch (Exception e) {
            throw new DataRetrievalException("Error getting ParentDetails Details:: " + e);
        }
    }
}
