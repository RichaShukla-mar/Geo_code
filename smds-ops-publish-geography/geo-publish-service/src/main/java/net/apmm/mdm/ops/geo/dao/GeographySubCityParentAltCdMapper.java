package net.apmm.mdm.ops.geo.dao;


import net.apmm.mdm.ops.geo.dao.model.GeographySubCityParentAlternateCodeData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GeographySubCityParentAltCdMapper implements RowMapper<GeographySubCityParentAlternateCodeData> {


    @Override
    public GeographySubCityParentAlternateCodeData mapRow(ResultSet resultSet, int i) throws SQLException {
        return GeographySubCityParentAlternateCodeData.builder()
                .codeType(resultSet.getString("codeType"))
                .code(resultSet.getString("code"))
                .build();
    }
}
