package com.maersk.facility.userexit.bean;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class FacilityParent {
	
	private String ParentName;

	@XmlElement(name = "AlternateCodes")
	 private List<FacilityAltCode> facilityParentAltCodes;

	/**
	 * @return the parentName
	 */
	public String getParentName() {
		return ParentName;
	}

	/**
	 * @param parentName the parentName to set
	 */
	public void setParentName(String parentName) {
		this.ParentName = parentName;
	}

	/**
	 * @return the facilityParentAltCodes
	 */
	public List<FacilityAltCode> getFacilityParentAltCodes() {
		return facilityParentAltCodes;
	}

	/**
	 * @param facilityParentAltCodes the facilityParentAltCodes to set
	 */
	public void setFacilityParentAltCodes(List<FacilityAltCode> facilityParentAltCodes) {
		this.facilityParentAltCodes = facilityParentAltCodes;
	}
	

}
