package net.apmm.mdm.ops.geo.dao;

import org.springframework.jdbc.core.RowMapper;
import net.apmm.mdm.ops.geo.dao.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GeographyMapper implements RowMapper<GeographyData> {
    @Override
    public GeographyData mapRow(ResultSet resultSet, int i) throws SQLException {
        return GeographyData.builder().geoRowID(resultSet.getString("geoRowID"))
                .geoType(resultSet.getString("geoType"))
                .name(resultSet.getString("name"))
                .status(resultSet.getString("status"))
                .validFrom(resultSet.getString("validFrom"))
                .validTo(resultSet.getString("validTo"))
                .longitude(resultSet.getString("longitude"))
                .latitude(resultSet.getString("latitude"))
                .timeZone(resultSet.getString("timeZone"))
                .daylightSavingTime(resultSet.getString("daylightSavingTime"))
                .utcOffsetMinutes(resultSet.getString("utcOffsetMinutes"))
                .daylightSavingStart(resultSet.getString("daylightSavingStart"))
                .daylightSavingEnd(resultSet.getString("daylightSavingEnd"))
                .daylightSavingShiftMinutes(resultSet.getString("daylightSavingShiftMinutes"))
                .description(resultSet.getString("description"))
                .workaroundReason(resultSet.getString("workaroundReason"))
                .restricted(resultSet.getString("restricted"))
                .siteType(resultSet.getString("siteType"))
                .gpsFlag(resultSet.getString("gpsFlag"))
                .gsmFlag(resultSet.getString("gsmFlag"))
                .streetNumber(resultSet.getString("streetNumber"))
                .addressLine1(resultSet.getString("addressLine1"))
                .addressLine2(resultSet.getString("addressLine2"))
                .addressLine3(resultSet.getString("addressLine3"))
                .postalCode(resultSet.getString("postalCode"))
                .postalCodeMandatoryFlag(resultSet.getString("postalCodeMandatoryFlag"))
                .stateProvinceMandatory(resultSet.getString("stateProvinceMandatory"))
                .dialingCode(resultSet.getString("dialingCode"))
                .dialingCodeDescription(resultSet.getString("dialingCodeDescription"))
                .portFlag(resultSet.getString("portFlag"))
                .olsonTimezone(resultSet.getString("olsonTimezone"))
                .bdaType(resultSet.getString("bdaType"))
                .build();
    }
}
