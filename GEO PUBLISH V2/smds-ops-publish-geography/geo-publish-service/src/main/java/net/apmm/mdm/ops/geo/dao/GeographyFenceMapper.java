package net.apmm.mdm.ops.geo.dao;


import net.apmm.mdm.ops.geo.dao.model.GeographyFenceData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GeographyFenceMapper implements RowMapper<GeographyFenceData> {


    @Override
    public GeographyFenceData mapRow(ResultSet resultSet, int i) throws SQLException {
        return GeographyFenceData.builder()
                .name(resultSet.getString("name"))
                .geoFenceType(resultSet.getString("geoFenceType"))
                .build();
    }
}
