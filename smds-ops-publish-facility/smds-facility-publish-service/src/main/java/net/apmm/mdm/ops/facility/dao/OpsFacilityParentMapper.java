package net.apmm.mdm.ops.facility.dao;

import net.apmm.mdm.ops.facility.dao.model.OpsFacilityParentDetailsData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OpsFacilityParentMapper implements RowMapper<OpsFacilityParentDetailsData> {

    @Override
    public OpsFacilityParentDetailsData mapRow(ResultSet resultSet, int i) throws SQLException {
        return OpsFacilityParentDetailsData.builder()
                .parentRowID(resultSet.getString("parentRowID"))
                .name(resultSet.getString("name"))
                .type(resultSet.getString("type"))
                .build();
    }
}
