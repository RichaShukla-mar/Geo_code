package net.apmm.mdm.ops.facility.dao;


import net.apmm.mdm.ops.facility.dao.model.OpsFacilityBdaData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OpsFacilityBdaMapper implements RowMapper<OpsFacilityBdaData> {
    @Override
    public OpsFacilityBdaData mapRow(ResultSet resultSet, int i) throws SQLException {
        return OpsFacilityBdaData.builder()
                .bdaRowID(resultSet.getString("bdaRowID"))
                .name(resultSet.getString("name"))
                .type(resultSet.getString("type"))
                .bdaType(resultSet.getString("bdaType"))
                .build();


    }


}
