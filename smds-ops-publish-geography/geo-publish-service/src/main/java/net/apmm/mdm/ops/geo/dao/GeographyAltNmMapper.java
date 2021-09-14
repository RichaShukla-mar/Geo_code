package net.apmm.mdm.ops.geo.dao;

import net.apmm.mdm.ops.geo.dao.model.GeographyAlternateNameData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GeographyAltNmMapper implements RowMapper<GeographyAlternateNameData> {
    @Override
    public GeographyAlternateNameData mapRow(ResultSet resultSet, int i) throws SQLException {

        return GeographyAlternateNameData.builder()
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .status(resultSet.getString("status"))
                .build();
            }
}
