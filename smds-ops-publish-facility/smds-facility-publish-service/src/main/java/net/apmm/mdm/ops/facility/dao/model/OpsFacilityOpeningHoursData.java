package net.apmm.mdm.ops.facility.dao.model;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class OpsFacilityOpeningHoursData {
    private String day;
    private String openTimeHours;
    private String openTimeMinutes;
    private String closeTimeHours;
    private String closeTimeMinutes;
}
