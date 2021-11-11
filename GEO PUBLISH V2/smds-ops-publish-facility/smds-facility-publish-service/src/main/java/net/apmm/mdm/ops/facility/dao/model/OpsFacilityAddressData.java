package net.apmm.mdm.ops.facility.dao.model;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class OpsFacilityAddressData {
    private String houseNumber;
    private String street;
    private String city;
    private String postalCode;
    private String poBox;
    private String district;
    private String territory;
    private String countryName;
    private String countryCode;
    private String addressLine2;
    private String addressLine3;
    private String latitude;
    private String longitude;
    private String addressQualityCheckIndicator;

}
