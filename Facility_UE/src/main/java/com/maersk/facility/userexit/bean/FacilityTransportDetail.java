package com.maersk.facility.userexit.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author AJA350
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class FacilityTransportDetail {

	
	private String	TransportMode;
	private String	TransportCode;
	private String	TransportDescription;
	private String	ValidThroughDate;
	/**
	 * @return the transportMode
	 */
	public String getTransportMode() {
		return TransportMode;
	}
	/**
	 * @param transportMode the transportMode to set
	 */
	
	public void setTransportMode(String TransportMode) {
		this.TransportMode = TransportMode;
	}
	/**
	 * @return the transportCode
	 */
	public String getTransportCode() {
		return TransportCode;
	}
	/**
	 * @param transportCode the transportCode to set
	 */
	
	public void setTransportCode(String TransportCode) {
		this.TransportCode = TransportCode;
	}
	/**
	 * @return the transportDescription
	 */
	public String getTransportDescription() {
		return TransportDescription;
	}
	/**
	 * @param transportDescription the transportDescription to set
	 */
	
	public void setTransportDescription(String TransportDescription) {
		this.TransportDescription = TransportDescription;
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
		return "FacilityTransportDetail [TransportMode=" + TransportMode + ", TransportCode=" + TransportCode
				+ ", TransportDescription=" + TransportDescription + ", ValidThroughDate=" + ValidThroughDate + "]";
	}

}
