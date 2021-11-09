package com.maersk.facility.userexit.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author AJA350
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class FacilityOpeningHour {

	
	
	private String	CloseTimeMinutes;
	private String	Day;
	private String	OpenTimeHours;
	private String	OpenTimeMinutes;
	private String	CloseTimeHours;
	
	/**
	 * @return the day
	 */
	public String getDay() {
		return Day;
	}
	/**
	 * @param day the day to set
	 */
	
	
	
	
	public void setDay(String Day) {
		this.Day = Day;
	}
	/**
	 * @return the openTimeHours
	 */
	public String getOpenTimeHours() {
		return OpenTimeHours;
	}
	/**
	 * @param openTimeHours the openTimeHours to set
	 */
	
	public void setOpenTimeHours(String OpenTimeHours) {
		this.OpenTimeHours = OpenTimeHours;
	}
	/**
	 * @return the openTimeMinutes
	 */
	public String getOpenTimeMinutes() {
		return OpenTimeMinutes;
	}
	/**
	 * @param openTimeMinutes the openTimeMinutes to set
	 */
	
	public void setOpenTimeMinutes(String OpenTimeMinutes) {
		this.OpenTimeMinutes = OpenTimeMinutes;
	}
	/**
	 * @return the closeTimeHours
	 */
	public String getCloseTimeHours() {
		return CloseTimeHours;
	}
	/**
	 * @param closeTimeHours the closeTimeHours to set
	 */
	
	public void setCloseTimeHours(String CloseTimeHours) {
		this.CloseTimeHours = CloseTimeHours;
	}
	/**
	 * @return the closeTimeMinutes
	 */
	public String getCloseTimeMinutes() {
		return CloseTimeMinutes;
	}
	/**
	 * @param closeTimeMinutes the closeTimeMinutes to set
	 */
	
	public void setCloseTimeMinutes(String CloseTimeMinutes) {
		this.CloseTimeMinutes = CloseTimeMinutes;
	}
	@Override
	public String toString() {
		return "FacilityOpeningHour [Day=" + Day + ", OpenTimeHours=" + OpenTimeHours + ", OpenTimeMinutes="
				+ OpenTimeMinutes + ", CloseTimeHours=" + CloseTimeHours + ", CloseTimeMinutes=" + CloseTimeMinutes
				+ "]";
	}

}
