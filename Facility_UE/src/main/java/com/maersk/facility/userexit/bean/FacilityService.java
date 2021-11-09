package com.maersk.facility.userexit.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author AJA350
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class FacilityService {

	
	private String	ServiceDesc;
	private String	FacilityServicesCd;
	private String	ServiceName;
	private String	ValidThroughDate;
	/**
	 * @return the serviceDesc
	 */
	public String getServiceDesc() {
		return ServiceDesc;
	}
	/**
	 * @param serviceDesc the serviceDesc to set
	 */
	
	public void setServiceDesc(String ServiceDesc) {
		this.ServiceDesc = ServiceDesc;
	}
	/**
	 * @return the serviceName
	 */
	public String getServiceName() {
		return ServiceName;
	}
	/**
	 * @param serviceName the serviceName to set
	 */
	
	public void setServiceName(String ServiceName) {
		this.ServiceName = ServiceName;
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
	/**
	 * @return the facilityServicesCd
	 */
	public String getFacilityServicesCd() {
		return FacilityServicesCd;
	}
	/**
	 * @param facilityServicesCd the facilityServicesCd to set
	 */
	public void setFacilityServicesCd(String facilityServicesCd) {
		FacilityServicesCd = facilityServicesCd;
	}
	@Override
	public String toString() {
		return "FacilityService [ServiceDesc=" + ServiceDesc + ", FacilityServicesCd=" + FacilityServicesCd
				+ ", ServiceName=" + ServiceName + ", ValidThroughDate=" + ValidThroughDate + "]";
	}
	

}
