package net.apmm.mdm.ops.facility.dao;

import net.apmm.mdm.ops.facility.dao.model.OpsFacilityParentAltCodeData;
import net.apmm.mdm.ops.facility.dao.model.OpsFacilityTypeData;
import net.apmm.mdm.ops.facility.util.util;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OpsFacilityTypeMapper implements RowMapper<OpsFacilityTypeData> {

    @Override
    public OpsFacilityTypeData mapRow(ResultSet resultSet, int i) throws SQLException {
        return OpsFacilityTypeData.builder()
                .code(resultSet.getString("code"))
                .name(resultSet.getString("name"))
                .masterType(resultSet.getString("masterType"))
                //.validThroughDate(resultSet.getString("validThroughDate"))
                .validThroughDate(resultSet.getTimestamp("validThroughDate")!=null? util.changeInputCreateDate(resultSet.getTimestamp("validThroughDate")) : 0)
                .build();
    }
}
