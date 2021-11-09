package com.maersk.facility.userexit.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author AJA350
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class FacilityContactDetail {

	
	private String	FirstName;
	private String	LastName;
	private String	JobTitle;
	private String	Department;
	private String	InternationalDialingCdPhone;
	private String	Extension;
	private String	PhoneNumber;
	private String	InternationalDialingCdMobile;
	private String	MobileNumber;
	private String	InternaltionalDialingCodeFax;
	private String	FaxNmbr;
	private String	EmailAddress;
	private String	ValidThroughDate;
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return FirstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	
	public void setFirstName(String FirstName) {
		this.FirstName = FirstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return LastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	
	public void setLastName(String LastName) {
		this.LastName = LastName;
	}
	/**
	 * @return the jobTitle
	 */
	public String getJobTitle() {
		return JobTitle;
	}
	/**
	 * @param jobTitle the jobTitle to set
	 */
	
	public void setJobTitle(String JobTitle) {
		this.JobTitle = JobTitle;
	}
	/**
	 * @return the department
	 */
	public String getDepartment() {
		return Department;
	}
	/**
	 * @param department the department to set
	 */
	
	public void setDepartment(String Department) {
		this.Department = Department;
	}
	/**
	 * @return the internationalDialingCdPhone
	 */
	public String getInternationalDialingCdPhone() {
		return InternationalDialingCdPhone;
	}
	/**
	 * @param internationalDialingCdPhone the internationalDialingCdPhone to set
	 */
	
	public void setInternationalDialingCdPhone(String InternationalDialingCdPhone) {
		this.InternationalDialingCdPhone = InternationalDialingCdPhone;
	}
	/**
	 * @return the extension
	 */
	public String getExtension() {
		return Extension;
	}
	/**
	 * @param extension the extension to set
	 */
	
	public void setExtension(String Extension) {
		this.Extension = Extension;
	}
	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return PhoneNumber;
	}
	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	
	public void setPhoneNumber(String PhoneNumber) {
		this.PhoneNumber = PhoneNumber;
	}
	/**
	 * @return the internationalDialingCdMobile
	 */
	public String getInternationalDialingCdMobile() {
		return InternationalDialingCdMobile;
	}
	/**
	 * @param internationalDialingCdMobile the internationalDialingCdMobile to set
	 */
	
	public void setInternationalDialingCdMobile(String InternationalDialingCdMobile) {
		this.InternationalDialingCdMobile = InternationalDialingCdMobile;
	}
	/**
	 * @return the mobileNumber
	 */
	public String getMobileNumber() {
		return MobileNumber;
	}
	/**
	 * @param mobileNumber the mobileNumber to set
	 */
	
	public void setMobileNumber(String MobileNumber) {
		this.MobileNumber = MobileNumber;
	}
	/**
	 * @return the internaltionalDialingCodeFax
	 */
	public String getInternaltionalDialingCodeFax() {
		return InternaltionalDialingCodeFax;
	}
	/**
	 * @param internaltionalDialingCodeFax the internaltionalDialingCodeFax to set
	 */
	
	public void setInternaltionalDialingCodeFax(String InternaltionalDialingCodeFax) {
		this.InternaltionalDialingCodeFax = InternaltionalDialingCodeFax;
	}
	/**
	 * @return the faxNmbr
	 */
	public String getFaxNmbr() {
		return FaxNmbr;
	}
	/**
	 * @param faxNmbr the faxNmbr to set
	 */
	
	public void setFaxNmbr(String FaxNmbr) {
		this.FaxNmbr = FaxNmbr;
	}
	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return EmailAddress;
	}
	/**
	 * @param emailAddress the emailAddress to set
	 */
	
	public void setEmailAddress(String EmailAddress) {
		this.EmailAddress = EmailAddress;
	}
	/**
	 * @return the validThroughDate
	 */
	public String getValidThroughDate() {
		return ValidThroughDate;
	}
	/**
	 * @param validThroughDate the validThroughDate to set
	 */
	
	public void setValidThroughDate(String ValidThroughDate) {
		this.ValidThroughDate = ValidThroughDate;
	}
	@Override
	public String toString() {
		return "FacilityContactDetail [FirstName=" + FirstName + ", LastName=" + LastName + ", JobTitle=" + JobTitle
				+ ", Department=" + Department + ", InternationalDialingCdPhone=" + InternationalDialingCdPhone
				+ ", Extension=" + Extension + ", PhoneNumber=" + PhoneNumber + ", InternationalDialingCdMobile="
				+ InternationalDialingCdMobile + ", MobileNumber=" + MobileNumber + ", InternaltionalDialingCodeFax="
				+ InternaltionalDialingCodeFax + ", FaxNmbr=" + FaxNmbr + ", EmailAddress=" + EmailAddress
				+ ", ValidThroughDate=" + ValidThroughDate + "]";
	}
	
}
