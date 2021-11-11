package net.apmm.mdm.ops.geo.dao;

import net.apmm.mdm.ops.geo.util.util;
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
                .validFrom(resultSet.getTimestamp("validFrom")!=null? util.changeInputCreateDate(resultSet.getTimestamp("validFrom")) : 0)
                .validTo(resultSet.getTimestamp("validTo")!=null? util.changeInputCreateDate(resultSet.getTimestamp("validTo")) : 0)
                .longitude(resultSet.getString("longitude"))
                .latitude(resultSet.getString("latitude"))
                .timeZone(resultSet.getString("timeZone"))
                .daylightSavingTime(resultSet.getString("daylightSavingTime"))
                .utcOffsetMinutes(resultSet.getString("utcOffsetMinutes"))
                .daylightSavingStart(resultSet.getTimestamp("daylightSavingStart")!=null? util.changeInputCreateDate(resultSet.getTimestamp("daylightSavingStart")) : 0)
                .daylightSavingEnd(resultSet.getTimestamp("daylightSavingEnd")!=null? util.changeInputCreateDate(resultSet.getTimestamp("daylightSavingEnd")) : 0)
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
                .hsudName(resultSet.getString("hsudName"))
                .build();
    }
}
