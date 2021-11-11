package net.apmm.mdm.ops.geo.dao;



import net.apmm.mdm.ops.geo.dao.model.GeographySubCityParentDetailsData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GeographySubCityParentMapper implements RowMapper<GeographySubCityParentDetailsData> {


    @Override
    public GeographySubCityParentDetailsData mapRow(ResultSet resultSet, int i) throws SQLException {
        return GeographySubCityParentDetailsData.builder()
                .parentRowId(resultSet.getString("parentRowId"))
                .name(resultSet.getString("name"))
                .type(resultSet.getString("type"))
                .bdatype(resultSet.getString("bdatype"))
                .build();
    }
}
