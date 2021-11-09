package com.maersk.facility.userexit.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Facility {
	
	private String	FacilityEventType;
	private String 	FacilityType;
	private String	FacilityExtExposed;
	private String	FacilityExtOwned;
	private String	FacilityName;
	private String	FacilityStatus;
	private String	FacilityUrl;
	private String	FacilityCreateDate;
	private String	FacilityUpdatedBy;
	private String	FacilityCreatedBy;
	
	
	/**
	 * @return the facilityEventType
	 */
	public String getFacilityEventType() {
		return FacilityEventType;
	}
	/**
	 * @param facilityEventType the FacilityEventType to set
	 */
	
	public void setFacilityEventType(String FacilityEventType) {
		this.FacilityEventType = FacilityEventType;
	}
	/**
	 * @return the FacilityCreateDate
	 */
	public String getFacilityCreateDate() {
		return FacilityCreateDate;
	}
	/**
	 * @param FacilityCreateDate the FacilityCreateDate to set
	 */
	
	public void setFacilityCreateDate(String FacilityCreateDate) {
		this.FacilityCreateDate = FacilityCreateDate;
	}
	/**
	 * @return the FacilityUpdatedBy
	 */
	public String getFacilityUpdatedBy() {
		return FacilityUpdatedBy;
	}
	/**
	 * @param FacilityUpdatedBy the FacilityUpdatedBy to set
	 */
	
	public void setFacilityUpdatedBy(String FacilityUpdatedBy) {
		this.FacilityUpdatedBy = FacilityUpdatedBy;
	}
	/**
	 * @return the FacilityCreatedBy
	 */
	public String getFacilityCreatedBy() {
		return FacilityCreatedBy;
	}
	/**
	 * @param FacilityCreatedBy the FacilityCreatedBy to set
	 */
	
	public void setFacilityCreatedBy(String FacilityCreatedBy) {
		this.FacilityCreatedBy = FacilityCreatedBy;
	}
	/**
	 * @return the FacilityName
	 */
	public String getFacilityName() {
		return FacilityName;
	}
	/**
	 * @param FacilityName the FacilityName to set
	 */
	
	public void setFacilityName(String FacilityName) {
		this.FacilityName = FacilityName;
	}
	/**
	 * @return the FacilityExtOwned
	 */
	public String getFacilityExtOwned() {
		return FacilityExtOwned;
	}
	/**
	 * @param FacilityExtOwned the FacilityExtOwned to set
	 */
	
	public void setFacilityExtOwned(String FacilityExtOwned) {
		this.FacilityExtOwned = FacilityExtOwned;
	}
	/**
	 * @return the FacilityStatus
	 */
	public String getFacilityStatus() {
		return FacilityStatus;
	}
	/**
	 * @param FacilityStatus the FacilityStatus to set
	 */
	
	public void setFacilityStatus(String FacilityStatus) {
		this.FacilityStatus = FacilityStatus;
	}
	/**
	 * @return the FacilityExtExposed
	 */
	public String getFacilityExtExposed() {
		return FacilityExtExposed;
	}
	/**
	 * @param FacilityExtExposed the FacilityExtExposed to set
	 */
	
	public void setFacilityExtExposed(String FacilityExtExposed) {
		this.FacilityExtExposed = FacilityExtExposed;
	}
	/**
	 * @return the FacilityUrl
	 */
	public String getFacilityUrl() {
		return FacilityUrl;
	}
	/**
	 * @param FacilityUrl the FacilityUrl to set
	 */
	
	public void setFacilityUrl(String FacilityUrl) {
		this.FacilityUrl = FacilityUrl;
	}
	/**
	 * @return the FacilityType
	 */
	public String getFacilityType() {
		return FacilityType;
	}
	/**
	 * @param FacilityType the FacilityType to set
	 */
	
	public void setFacilityType(String FacilityType) {
		this.FacilityType = FacilityType;
	}
	@Override
	public String toString() {
		return "Facility [FacilityEventType=" + FacilityEventType + ", FacilityCreateDate=" + FacilityCreateDate
				+ ", FacilityUpdatedBy=" + FacilityUpdatedBy + ", FacilityCreatedBy=" + FacilityCreatedBy
				+ ", FacilityName=" + FacilityName + ", FacilityExtOwned=" + FacilityExtOwned + ", FacilityStatus="
				+ FacilityStatus + ", FacilityExtExposed=" + FacilityExtExposed + ", FacilityUrl=" + FacilityUrl
				+ ", FacilityType=" + FacilityType + "]";
	}
		
}
