package com.maersk.facility.userexit.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * @author AJA350
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class FacilityAddress {

	private String HouseNumber;
	private String Street;
	private String City;
	// private String CityCd;
	private String PostalCode;
	private String PoBox;
	private String District;
	private String Territory;
	private String Country;
	private String AddressLine2;
	private String AddressLine3;
	private String Latitude;
	private String Longitude;

	/**
	 * @return the houseNumber
	 */
	public String getHouseNumber() {
		return HouseNumber;
	}

	/**
	 * @param houseNumber the houseNumber to set
	 */

	public void setHouseNumber(String HouseNumber) {
		this.HouseNumber = HouseNumber;
	}

	/**
	 * @return the street
	 */
	public String getStreet() {
		return Street;
	}

	/**
	 * @param street the street to set
	 */

	public void setStreet(String Street) {
		this.Street = Street;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return City;
	}

	/**
	 * @param city the city to set
	 */

	public void setCity(String City) {
		this.City = City;
	}

	/**
	 * @return the postalCode
	 */
	public String getPostalCode() {
		return PostalCode;
	}

	/**
	 * @param postalCode the postalCode to set
	 */

	public void setPostalCode(String PostalCode) {
		this.PostalCode = PostalCode;
	}

	/**
	 * @return the poBox
	 */
	public String getPoBox() {
		return PoBox;
	}

	/**
	 * @param poBox the poBox to set
	 */

	public void setPoBox(String PoBox) {
		this.PoBox = PoBox;
	}

	/**
	 * @return the district
	 */
	public String getDistrict() {
		return District;
	}

	/**
	 * @param district the district to set
	 */

	public void setDistrict(String District) {
		this.District = District;
	}

	/**
	 * @return the territory
	 */
	public String getTerritory() {
		return Territory;
	}

	/**
	 * @param territory the territory to set
	 */

	public void setTerritory(String Territory) {
		this.Territory = Territory;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return Country;
	}

	/**
	 * @param country the country to set
	 */

	public void setCountry(String Country) {
		this.Country = Country;
	}

	/**
	 * @return the addressLine2
	 */
	public String getAddressLine2() {
		return AddressLine2;
	}

	/**
	 * @param addressLine2 the addressLine2 to set
	 */

	public void setAddressLine2(String AddressLine2) {
		this.AddressLine2 = AddressLine2;
	}

	/**
	 * @return the addressLine3
	 */
	public String getAddressLine3() {
		return AddressLine3;
	}

	/**
	 * @param addressLine3 the addressLine3 to set
	 */

	public void setAddressLine3(String AddressLine3) {
		this.AddressLine3 = AddressLine3;
	}

	/**
	 * @return the latitude
	 */
	public String getLatitude() {
		return Latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */

	public void setLatitude(String Latitude) {
		this.Latitude = Latitude;
	}

	/**
	 * @return the longitude
	 */
	public String getLongitude() {
		return Longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */

	public void setLongitude(String Longitude) {
		this.Longitude = Longitude;
	}

	@Override
	public String toString() {
		return "FacilityAddress [HouseNumber=" + HouseNumber + ", Street=" + Street + ", City=" + City + ", PostalCode="
				+ PostalCode + ", PoBox=" + PoBox + ", District=" + District + ", Territory=" + Territory + ", Country="
				+ Country + ", AddressLine2=" + AddressLine2 + ", AddressLine3=" + AddressLine3 + ", Latitude="
				+ Latitude + ", Longitude=" + Longitude + "]";
	}

	/**
	 * @return the cityCd
	 */
	/*
	 * public String getCityCd() { return CityCd; }
	 *//**
		 * @param cityCd the cityCd to set
		 *//*
			 * public void setCityCd(String cityCd) { CityCd = cityCd; }
			 */
	

}
