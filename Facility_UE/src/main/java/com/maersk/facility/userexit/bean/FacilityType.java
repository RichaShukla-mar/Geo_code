package com.maersk.facility.userexit.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author AJA350
 *
 */

@XmlAccessorType(XmlAccessType.FIELD)
public class FacilityType {

	
	private String	Name;
	private String	Code;
	private String	MasterType;
	private String	ValidThroughDate;
	/**
	 * @return the name
	 */
	public String getName() {
		return Name;
	}
	/**
	 * @param name the name to set
	 */
	
	public void setName(String Name) {
		this.Name = Name;
	}
	/**
	 * @return the code
	 */
	public String getCode() {
		return Code;
	}
	/**
	 * @param code the code to set
	 */
	
	public void setCode(String Code) {
		this.Code = Code;
	}
	/**
	 * @return the masterType
	 */
	public String getMasterType() {
		return MasterType;
	}
	/**
	 * @param masterType the masterType to set
	 */
	
	public void setMasterType(String MasterType) {
		this.MasterType = MasterType;
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
		return "FacilityType [Name=" + Name + ", Code=" + Code + ", MasterType=" + MasterType + ", ValidThroughDate="
				+ ValidThroughDate + "]";
	}
	
}
