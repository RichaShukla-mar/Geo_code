package net.apmm.mdm.ops.facility.dao.model;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class OpsFacilityContactDetailData {
    private String firstName;
    private String lastName;
    private String jobTitle;
    private String department;
    private String internationalDialingCdPhone;
    private String extension;
    private String phoneNumber;
    private String internationalDialingCdMobile;
    private String mobileNumber;
    private String internationalDialingCdFax;
    private String faxNumber;
    private String emailAddress;
    private String validThroughDate;

}
