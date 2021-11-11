package net.apmm.mdm.ops.geo.dao;

import net.apmm.mdm.ops.geo.dao.model.GeographyBDADetailsData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GeographyBdaMapper implements RowMapper<GeographyBDADetailsData> {


    @Override
    public GeographyBDADetailsData mapRow(ResultSet resultSet, int i) throws SQLException {
        return GeographyBDADetailsData.builder()
                .bdaRowID(resultSet.getString("bdaRowID"))
                .name(resultSet.getString("name"))
                .type(resultSet.getString("type"))
                .bdaType(resultSet.getString("bdaType"))
                .build();
    }
}
