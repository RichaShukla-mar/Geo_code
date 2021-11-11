package net.apmm.mdm.ops.geo.dao;

import net.apmm.mdm.ops.geo.dao.model.GeographyCountryData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GeographyCountryMapper implements RowMapper<GeographyCountryData> {


    @Override
    public GeographyCountryData mapRow(ResultSet resultSet, int i) throws SQLException {
        return GeographyCountryData.builder()
                .countryRowID(resultSet.getString("countryRowID"))
                .name(resultSet.getString("name"))
                .type(resultSet.getString("type"))
                .build();
    }
}
