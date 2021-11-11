package net.apmoller.services.cmd.searchfacility.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


/**
 *
 * Return results of the customer search (same for all search customer
 * operations except searchDuplicateCustomer?)
 *
 *
 *
 */

public class SearchFacilityVO implements Serializable , Comparable<SearchFacilityVO>  {

	private final static long serialVersionUID = 1L;
	 	protected String facilityID;
	    protected String facilityName;
	    protected String facilityCategory;
	    protected List<String> facilityTypes;
	    protected List<String> offeringCodes;
	    protected List<OfferingVO> facilityOffering;
	    protected String isoCountryCode;
	    protected String city;
	    protected String cityGeoID;
	    protected String facilityLifecycleStatus;
	    protected String houseNo;
	    protected String streetName;
	    protected String region;
	    protected String regionGeoID;
	    protected String postalCode;
	    protected String latitude;
	    protected String longitude;
	    protected String facilityGEOId;
	    protected String facilityRKSTCode;
	    protected String country;
	    protected String countryGeoID;
	    protected String brand;
	    protected String function;
	    protected String commType;
	    protected String businessUnitId;





	    public String getBrand() {
			return brand;
		}
		public void setBrand(String brand) {
			this.brand = brand;
		}
		public String getFunction() {
			return function;
		}
		public void setFunction(String function) {
			this.function = function;
		}
		public String getCommType() {
			return commType;
		}
		public void setCommType(String commType) {
			this.commType = commType;
		}
			public List<OfferingVO> getFacilityOffering() {
			return facilityOffering;
		}
		public void setFacilityOffering(List<OfferingVO> facilityOffering) {
			this.facilityOffering = facilityOffering;
		}
		public String getFacilityID() {
			return facilityID;
		}
		public void setFacilityID(String facilityID) {
			this.facilityID = facilityID;
		}
		public List<String> getFacilityTypes() {
			return facilityTypes;
		}
		public void setFacilityTypes(List<String> facilityTypes) {
			this.facilityTypes = facilityTypes;
		}
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		public String getFacilityGEOId() {
			return facilityGEOId;
		}
		public void setFacilityGEOId(String facilityGEOId) {
			this.facilityGEOId = facilityGEOId;
		}
		public String getFacilityRKSTCode() {
			return facilityRKSTCode;
		}
		public void setFacilityRKSTCode(String facilityRKSTCode) {
			this.facilityRKSTCode = facilityRKSTCode;
		}
		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		public List<String> getOfferingCodes() {
			return offeringCodes;
		}
		public String getFacilityCategory() {
			return facilityCategory;
		}

		public String getFacilityLifecycleStatus() {
			return facilityLifecycleStatus;
		}
		public void setOfferingCodes(List<String> offeringCodes) {
			this.offeringCodes = offeringCodes;
		}
		public void setFacilityCategory(String facilityCategory) {
			this.facilityCategory = facilityCategory;
		}

		public void setFacilityLifecycleStatus(String facilityLifecycleStatus) {
			this.facilityLifecycleStatus = facilityLifecycleStatus;
		}
		public String getFacilityName() {
			return facilityName;
		}
		public void setFacilityName(String facilityName) {
			this.facilityName = facilityName;
		}
		public String getIsoCountryCode() {
			return isoCountryCode;
		}
		public void setIsoCountryCode(String isoCountryCode) {
			this.isoCountryCode = isoCountryCode;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getHouseNo() {
			return houseNo;
		}
		public void setHouseNo(String houseNo) {
			this.houseNo = houseNo;
		}
		public String getStreetName() {
			return streetName;
		}
		public void setStreetName(String streetName) {
			this.streetName = streetName;
		}
		public String getRegion() {
			return region;
		}
		public void setRegion(String region) {
			this.region = region;
		}
		public String getPostalCode() {
			return postalCode;
		}
		public void setPostalCode(String postalCode) {
			this.postalCode = postalCode;
		}
		public String getLatitude() {
			return latitude;
		}
		public void setLatitude(String latitude) {
			this.latitude = latitude;
		}
		public String getLongitude() {
			return longitude;
		}
		public void setLongitude(String longitude) {
			this.longitude = longitude;
		}

		public String getCityGeoID() {
			return cityGeoID;
		}
		public void setCityGeoID(String cityGeoID) {
			this.cityGeoID = cityGeoID;
		}
		public String getRegionGeoID() {
			return regionGeoID;
		}
		public void setRegionGeoID(String regionGeoID) {
			this.regionGeoID = regionGeoID;
		}
		public String getCountryGeoID() {
			return countryGeoID;
		}
		public void setCountryGeoID(String countryGeoID) {
			this.countryGeoID = countryGeoID;
		}



















		public String getBusinessUnitId() {
			return businessUnitId;
		}
		public void setBusinessUnitId(String businessUnitId) {
			this.businessUnitId = businessUnitId;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((facilityID == null) ? 0 : facilityID.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			SearchFacilityVO other = (SearchFacilityVO) obj;
			if (facilityID == null) {
				if (other.facilityID != null)
					return false;
			} else if (!facilityID.equals(other.facilityID))
				return false;
			return true;
		}
		@Override
		public int compareTo(SearchFacilityVO o) {
			// TODO Auto-generated method stub
			return this.facilityID.compareTo(o.facilityID);

		}

}
