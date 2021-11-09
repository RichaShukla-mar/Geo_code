package com.maersk.facility.userexit.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

 

/**
 * @author AJA350
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class FacilityAltCode {

	
	private String	AltCode;
	private String	TypeCode;
	
	/**
	 * @return the typeCode
	 */
	public String getTypeCode() {
		return TypeCode;
	}
	/**
	 * @param typeCode the typeCode to set
	 */
	
	public void setTypeCode(String TypeCode) {
		this.TypeCode = TypeCode;
	}
	/**
	 * @return the altCode
	 */
	public String getAltCode() {
		return AltCode;
	}
	/**
	 * @param altCode the altCode to set
	 */
	
	public void setAltCode(String AltCode) {
		this.AltCode = AltCode;
	}
	@Override
	public String toString() {
		return "FacilityAltCode [TypeCode=" + TypeCode + ", AltCode=" + AltCode + "]";
	}

}
