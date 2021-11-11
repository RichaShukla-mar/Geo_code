package net.apmm.mdm.ops.facility.dao;

import net.apmm.mdm.ops.facility.dao.model.OpsFacilityFenceData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OpsFacilityFenceMapper implements RowMapper<OpsFacilityFenceData> {
    @Override
    public OpsFacilityFenceData mapRow(ResultSet resultSet, int i) throws SQLException {
        return OpsFacilityFenceData.builder()
                .name(resultSet.getString("name"))
                .fenceType(resultSet.getString("fenceType"))
                .build();


    }


}
