package net.apmm.mdm.ops.geo.dao;

import net.apmm.mdm.ops.geo.dao.model.GeographyBDALocationsDetailsData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GeographyBdaLocationMapper implements RowMapper<GeographyBDALocationsDetailsData> {


    @Override
    public GeographyBDALocationsDetailsData mapRow(ResultSet resultSet, int i) throws SQLException {
        return GeographyBDALocationsDetailsData.builder()
                .bdaLocRowID(resultSet.getString("bdaLocRowID"))
                .name(resultSet.getString("name"))
                .type(resultSet.getString("type"))
                .status(resultSet.getString("status"))
                .build();
    }
}
