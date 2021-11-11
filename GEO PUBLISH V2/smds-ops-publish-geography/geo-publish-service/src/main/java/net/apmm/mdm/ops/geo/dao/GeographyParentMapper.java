package net.apmm.mdm.ops.geo.dao;



import net.apmm.mdm.ops.geo.dao.model.GeographyParentDetailsData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GeographyParentMapper implements RowMapper<GeographyParentDetailsData> {


    @Override
    public GeographyParentDetailsData mapRow(ResultSet resultSet, int i) throws SQLException {
        return GeographyParentDetailsData.builder()
                .parentRowId(resultSet.getString("parentRowId"))
                .name(resultSet.getString("name"))
                .type(resultSet.getString("type"))
                .bdatype(resultSet.getString("bdatype"))
                .build();
    }
}
