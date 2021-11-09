package com.maersk.facility.userexit.bean;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author AJA350
 *
 */


@XmlRootElement(name="SMDSFacilityEvent")
@XmlAccessorType(XmlAccessType.FIELD)
public class SMDSFacilityEvent{

	private static final long serialVersionUID = 1L;

	@XmlElement(name = "Facility")
	private Facility Facility;

	@XmlElement(name = "FacilityAddress")
	@XmlElementWrapper(name = "FacilityAddresses")
	private List<FacilityAddress> FacilityAddress;
	

	@XmlElement(name = "FacilityParent")
	private FacilityParent facilityParent ;

	@XmlElement(name = "FacilityDetail")
	@XmlElementWrapper(name = "FacilityDetails")
	private List<FacilityDetail> FacilityDetail;

	@XmlElement(name = "FacilityAlternateCode")
	@XmlElementWrapper(name = "FacilityAlternateCodes")
	private List<FacilityAltCode> FacilityAltCode;

	@XmlElement(name = "FacilityOpeningHour")
	@XmlElementWrapper(name = "FacilityOpeningHours")
	private List<FacilityOpeningHour> FacilityOpeningHour;

	@XmlElement(name = "FacilityTransportMode")
	@XmlElementWrapper(name = "FacilityTransportModes")
	private List<FacilityTransportDetail> FacilityTransDetail;

	@XmlElement(name = "FacilityService")
	@XmlElementWrapper(name = "FacilityServices")
	private List<FacilityService> FacilityService;

	@XmlElement(name = "FacilityContactDetail")
	@XmlElementWrapper(name = "FacilityContactDetails")
	private List<FacilityContactDetail> FacilityContactDetail;

	@XmlElement(name = "FacilityWTCSMtch")
	@XmlElementWrapper(name = "FacilityWTCSMtchs")
	private List<FacilityWTCSMatch> FacilityWTCSMtch;
	
	

	/**
	 * @return the facility
	 */
	public Facility getFacility() {
		return Facility;
	}

	/**
	 * @param facility the facility to set
	 */
	public void setFacility(Facility facility) {
		Facility = facility;
	}

	/**
	 * @return the facilityDetail
	 */
	public List<FacilityDetail> getFacilityDetail() {
		return FacilityDetail;
	}

	/**
	 * @param FacilityDetail the FacilityDetail to set
	 */
	public void setFacilityDetail(List<FacilityDetail> FacilityDetail) {
		this.FacilityDetail = FacilityDetail;
	}

	/**
	 * @return the FacilityAltCode
	 */
	public List<FacilityAltCode> getFacilityAltCode() {
		return FacilityAltCode;
	}

	/**
	 * @param FacilityAltCode the FacilityAltCode to set
	 */
	public void setFacilityAltCode(List<FacilityAltCode> FacilityAltCode) {
		this.FacilityAltCode = FacilityAltCode;
	}

	/**
	 * @return the FacilityOpeningHour
	 */
	public List<FacilityOpeningHour> getFacilityOpeningHour() {
		return FacilityOpeningHour;
	}

	/**
	 * @param FacilityOpeningHour the FacilityOpeningHour to set
	 */
	public void setFacilityOpeningHour(List<FacilityOpeningHour> FacilityOpeningHour) {
		this.FacilityOpeningHour = FacilityOpeningHour;
	}

	/**
	 * @return the FacilityTransDetail
	 */
	public List<FacilityTransportDetail> getFacilityTransDetail() {
		return FacilityTransDetail;
	}

	/**
	 * @param FacilityTransDetail the FacilityTransDetail to set
	 */
	public void setFacilityTransDetail(List<FacilityTransportDetail> FacilityTransDetail) {
		this.FacilityTransDetail = FacilityTransDetail;
	}

	/**
	 * @return the FacilityAddress
	 */
	public List<FacilityAddress> getFacilityAddress() {
		return FacilityAddress;
	}

	/**
	 * @param FacilityAddress the FacilityAddress to set
	 */
	public void setFacilityAddress(List<FacilityAddress> FacilityAddress) {
		this.FacilityAddress = FacilityAddress;
	}

	/**
	 * @return the FacilityService
	 */
	public List<FacilityService> getFacilityService() {
		return FacilityService;
	}

	/**
	 * @param FacilityService the FacilityService to set
	 */
	public void setFacilityService(List<FacilityService> FacilityService) {
		this.FacilityService = FacilityService;
	}

	/**
	 * @return the FacilityContactDetail
	 */
	public List<FacilityContactDetail> getFacilityContactDetail() {
		return FacilityContactDetail;
	}

	/**
	 * @param FacilityContactDetail the FacilityContactDetail to set
	 */
	public void setFacilityContactDetail(List<FacilityContactDetail> FacilityContactDetail) {
		this.FacilityContactDetail = FacilityContactDetail;
	}

	/**
	 * @return the FacilityWTCSMtch
	 */
	public List<FacilityWTCSMatch> getFacilityWTCSMtch() {
		return FacilityWTCSMtch;
	}

	/**
	 * @param FacilityWTCSMtch the FacilityWTCSMtch to set
	 */
	public void setFacilityWTCSMtch(List<FacilityWTCSMatch> FacilityWTCSMtch) {
		this.FacilityWTCSMtch = FacilityWTCSMtch;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the facilityParent
	 */
	public FacilityParent getFacilityParent() {
		return facilityParent;
	}

	/**
	 * @param facilityParent the facilityParent to set
	 */
	public void setFacilityParent(FacilityParent facilityParent) {
		this.facilityParent = facilityParent;
	}

	@Override
	public String toString() {
		return "SMDSFacilityEvent [Facility=" + Facility + ", FacilityAddress=" + FacilityAddress + ", FacilityDetail="
				+ FacilityDetail + ", FacilityAltCode=" + FacilityAltCode + ", FacilityOpeningHour="
				+ FacilityOpeningHour + ", FacilityTransDetail=" + FacilityTransDetail + ", FacilityService="
				+ FacilityService + ", FacilityContactDetail=" + FacilityContactDetail + ", FacilityWTCSMtch="
				+ FacilityWTCSMtch + ", facilityParent=" + facilityParent + "]";
	}	
}
