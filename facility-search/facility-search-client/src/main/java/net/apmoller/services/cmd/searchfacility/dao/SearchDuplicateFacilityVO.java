package net.apmoller.services.cmd.searchfacility.dao;

import java.io.Serializable;
import java.util.List;

public class SearchDuplicateFacilityVO implements Serializable, Comparable<SearchDuplicateFacilityVO> {
    
	private static final long serialVersionUID = 1L;
	protected String facilityName;	
    protected String facilityCategory;
    protected List<String> facilityTypes;
    protected String facilityLifecycleStatus;
    protected String region;
    protected String city;
    protected String isoCountryCode;
    protected String facilityGEOId;
    protected String facilityRKSTCode;
    protected List<String> offeringCode;
    protected String houseNo;
    protected String streetName;
    protected String postalCode;
    protected String latitude;
    protected String longitude;   
    protected String facilityID;
    
    
    public String getFacilityID() {
		return facilityID;
	}
	public void setFacilityID(String facilityID) {
		this.facilityID = facilityID;
	}
	public String getFacilityName() {
		return facilityName;
	}
	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}
	public String getFacilityCategory() {
		return facilityCategory;
	}
	public void setFacilityCategory(String facilityCategory) {
		this.facilityCategory = facilityCategory;
	}
	public List<String> getFacilityTypes() {
		return facilityTypes;
	}
	public void setFacilityTypes(List<String> facilityTypes) {
		this.facilityTypes = facilityTypes;
	}
	public String getFacilityLifecycleStatus() {
		return facilityLifecycleStatus;
	}
	public void setFacilityLifecycleStatus(String facilityLifecycleStatus) {
		this.facilityLifecycleStatus = facilityLifecycleStatus;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getIsoCountryCode() {
		return isoCountryCode;
	}
	public void setIsoCountryCode(String isoCountryCode) {
		this.isoCountryCode = isoCountryCode;
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
	public List<String> getOfferingCode() {
		return offeringCode;
	}
	public void setOfferingCode(List<String> offeringCode) {
		this.offeringCode = offeringCode;
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
		SearchDuplicateFacilityVO other = (SearchDuplicateFacilityVO) obj;
		if (facilityID == null) {
			if (other.facilityID != null)
				return false;
		} else if (!facilityID.equals(other.facilityID))         
			return false;
		return true;
	}
	@Override
	public int compareTo(SearchDuplicateFacilityVO o) {
		return this.facilityID.compareTo(o.facilityID);            
		
	}
}