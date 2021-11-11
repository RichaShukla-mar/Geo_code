package net.apmm.mdm.ops.geo.dao;

import net.apmm.mdm.ops.geo.dao.model.GeographyCountryAltCdData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GeographyCountryAltCdMapper implements RowMapper<GeographyCountryAltCdData> {


    @Override
    public GeographyCountryAltCdData mapRow(ResultSet resultSet, int i) throws SQLException {
        return GeographyCountryAltCdData.builder()
                .codeType(resultSet.getString("codeType"))
                .code(resultSet.getString("code"))
                .build();
    }
}
