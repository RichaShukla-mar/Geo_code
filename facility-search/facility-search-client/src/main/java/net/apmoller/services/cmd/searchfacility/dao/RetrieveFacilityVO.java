package net.apmoller.services.cmd.searchfacility.dao;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.XMLGregorianCalendar;

public class RetrieveFacilityVO implements Serializable , Comparable<RetrieveFacilityVO>{
	private static final long serialVersionUID = 1L;
	protected String facilityID;
	protected String geoID;
    protected String rkstCode;
    protected String facilityName;
    protected String facilityCategory;
    protected List<FacilityTypeVO> facilityTypes;
    protected String facilityLifecycleStatus;
    protected String url;
    protected String dodaac;
    protected String weightLimitOnCranes;
    protected String vesselOperator;
    protected String weightLimitInYard;
    protected String externallyExposed;
    protected Map<String, List<DayScheduleVO>> dayScheduleMap;
    protected List<TransportModesVO> transportModes;
	protected String houseNo;
    protected String streetName;
    protected String poBox;
    protected String building;
    protected String suburb;
    protected String district;
    protected String city;
    protected String region;
    protected String isoCountryCode;
    protected String postalCode;
    protected BigInteger taxJurisdictionCode;
    protected XMLGregorianCalendar AddCreationDate;
    protected String creationUser;
    protected XMLGregorianCalendar lastUpdateDate;
    protected String lastUpdateUser;
    protected String addressWorkflowIndicator;
    protected Integer addressMailabilityScore;
    protected String addressMatchScore;
    protected String lang;
    protected Map<String, List<OfferingVO>> facilityOfferingGroupMap;
    protected String facilityROWID;
    protected String gpsFlag;
    protected String gsmFlag;
    protected XMLGregorianCalendar fctCreationDate;
    protected String fctCreationUser;
    protected XMLGregorianCalendar fctLastUpdateDate;
    protected String fctLastUpdateUser;
    protected XMLGregorianCalendar creationDate;
    protected XMLGregorianCalendar deletionDate;
    protected String cityGeoID;
    protected String countryGeoID;
    protected String regionGeoID;
    protected String latitude;
    protected String longitude;
    protected String addrRowID;
    protected XMLGregorianCalendar validFrom;
    protected XMLGregorianCalendar validTo;
    protected String externallyOwned;
    protected String vesselAgent;
    protected String oceanFreightPricing;
    protected String brandCode;
    protected String brandName;
    protected String functionCode;
    protected String functionName;
    protected String dailingCode;
    protected String dailingDesc;
    protected String telecomNumber;
    protected String impMail;
    protected String expMail;
    protected String commercialFctType;

    protected String busniessUnitId;

	public String getCommercialFctType() {
		return commercialFctType;
	}

	public void setCommercialFctType(String commercialFctType) {
		this.commercialFctType = commercialFctType;
	}

	public String getBrandCode() {
		return brandCode;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getFunctionCode() {
		return functionCode;
	}

	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getDailingCode() {
		return dailingCode;
	}

	public void setDailingCode(String dailingCode) {
		this.dailingCode = dailingCode;
	}

	public String getDailingDesc() {
		return dailingDesc;
	}

	public void setDailingDesc(String dailingDesc) {
		this.dailingDesc = dailingDesc;
	}

	public String getTelecomNumber() {
		return telecomNumber;
	}

	public void setTelecomNumber(String telecomNumber) {
		this.telecomNumber = telecomNumber;
	}

	public String getImpMail() {
		return impMail;
	}

	public void setImpMail(String impMail) {
		this.impMail = impMail;
	}

	public String getExpMail() {
		return expMail;
	}

	public void setExpMail(String expMail) {
		this.expMail = expMail;
	}

	public String getDodaac() {
		return dodaac;
	}

	public void setDodaac(String dodaac) {
		this.dodaac = dodaac;
	}

	public String getExternallyOwned() {
		return externallyOwned;
	}

	public void setExternallyOwned(String externallyOwned) {
		this.externallyOwned = externallyOwned;
	}

	public String getVesselAgent() {
		return vesselAgent;
	}

	public void setVesselAgent(String vesselAgent) {
		this.vesselAgent = vesselAgent;
	}

	public String getOceanFreightPricing() {
		return oceanFreightPricing;
	}

	public void setOceanFreightPricing(String oceanFreightPricing) {
		this.oceanFreightPricing = oceanFreightPricing;
	}

	public XMLGregorianCalendar getFctCreationDate() {
		return fctCreationDate;
	}

	public void setFctCreationDate(XMLGregorianCalendar fctCreationDate) {
		this.fctCreationDate = fctCreationDate;
	}

	public String getFctCreationUser() {
		return fctCreationUser;
	}

	public List<TransportModesVO> getTransportModes() {
		return transportModes;
	}

	public void setTransportModes(List<TransportModesVO> transportModes) {
		this.transportModes = transportModes;
	}

	public void setFacilityTypes(List<FacilityTypeVO> facilityTypes) {
		this.facilityTypes = facilityTypes;
	}
	public List<FacilityTypeVO> getFacilityTypes() {
		return facilityTypes;
	}

	public void setFctCreationUser(String fctCreationUser) {
		this.fctCreationUser = fctCreationUser;
	}

	public XMLGregorianCalendar getFctLastUpdateDate() {
		return fctLastUpdateDate;
	}

	public void setFctLastUpdateDate(XMLGregorianCalendar fctLastUpdateDate) {
		this.fctLastUpdateDate = fctLastUpdateDate;
	}

	public String getFctLastUpdateUser() {
		return fctLastUpdateUser;
	}

	public void setFctLastUpdateUser(String fctLastUpdateUser) {
		this.fctLastUpdateUser = fctLastUpdateUser;
	}

	public XMLGregorianCalendar getAddCreationDate() {
		return AddCreationDate;
	}

	public void setAddCreationDate(XMLGregorianCalendar addCreationDate) {
		AddCreationDate = addCreationDate;
	}

	public String getCreationUser() {
		return creationUser;
	}

	public void setCreationUser(String creationUser) {
		this.creationUser = creationUser;
	}

	public XMLGregorianCalendar getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(XMLGregorianCalendar lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public void setAddressWorkflowIndicator(String addressWorkflowIndicator) {
		this.addressWorkflowIndicator = addressWorkflowIndicator;
	}



	public Map<String, List<DayScheduleVO>> getDayScheduleMap() {
		return dayScheduleMap;
	}

	public void setDayScheduleMap(Map<String, List<DayScheduleVO>> dayScheduleMap) {
		this.dayScheduleMap = dayScheduleMap;
	}

	public Map<String, List<OfferingVO>> getFacilityOfferingGroupMap() {
		return facilityOfferingGroupMap;
	}

	public void setFacilityOfferingGroupMap(Map<String, List<OfferingVO>> facilityOfferingGroupMap) {
		this.facilityOfferingGroupMap = facilityOfferingGroupMap;
	}

	public String getAddressWorkflowIndicator() {
		return addressWorkflowIndicator;
	}

	public String getFacilityID() {
		return facilityID;
	}

	public void setFacilityID(String facilityID) {
		this.facilityID = facilityID;
	}

	public String getFacilityROWID() {
		return facilityROWID;
	}

	public void setFacilityROWID(String facilityROWID) {
		this.facilityROWID = facilityROWID;
	}



	public String getGpsFlag() {
		return gpsFlag;
	}

	public void setGpsFlag(String gpsFlag) {
		this.gpsFlag = gpsFlag;
	}

	public String getGsmFlag() {
		return gsmFlag;
	}

	public void setGsmFlag(String gsmFlag) {
		this.gsmFlag = gsmFlag;
	}

	public XMLGregorianCalendar getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(XMLGregorianCalendar creationDate) {
		this.creationDate = creationDate;
	}

	public XMLGregorianCalendar getDeletionDate() {
		return deletionDate;
	}

	public void setDeletionDate(XMLGregorianCalendar deletionDate) {
		this.deletionDate = deletionDate;
	}

	public String getCityGeoID() {
		return cityGeoID;
	}

	public void setCityGeoID(String cityGeoID) {
		this.cityGeoID = cityGeoID;
	}

	public String getCountryGeoID() {
		return countryGeoID;
	}

	public void setCountryGeoID(String countryGeoID) {
		this.countryGeoID = countryGeoID;
	}

	public String getRegionGeoID() {
		return regionGeoID;
	}

	public void setRegionGeoID(String regionGeoID) {
		this.regionGeoID = regionGeoID;
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

	public String getAddrRowID() {
		return addrRowID;
	}

	public void setAddrRowID(String addrRowID) {
		this.addrRowID = addrRowID;
	}

	public XMLGregorianCalendar getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(XMLGregorianCalendar validFrom) {
		this.validFrom = validFrom;
	}

	public XMLGregorianCalendar getValidTo() {
		return validTo;
	}

	public void setValidTo(XMLGregorianCalendar validTo) {
		this.validTo = validTo;
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

	public String getFacilityLifecycleStatus() {
		return facilityLifecycleStatus;
	}

	public void setFacilityLifecycleStatus(String facilityLifecycleStatus) {
		this.facilityLifecycleStatus = facilityLifecycleStatus;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getWeightLimitOnCranes() {
		return weightLimitOnCranes;
	}

	public void setWeightLimitOnCranes(String weightLimitOnCranes) {
		this.weightLimitOnCranes = weightLimitOnCranes;
	}

	public String getVesselOperator() {
		return vesselOperator;
	}

	public void setVesselOperator(String vesselOperator) {
		this.vesselOperator = vesselOperator;
	}

	public String getWeightLimitInYard() {
		return weightLimitInYard;
	}

	public void setWeightLimitInYard(String weightLimitInYard) {
		this.weightLimitInYard = weightLimitInYard;
	}

	public String getExternallyExposed() {
		return externallyExposed;
	}

	public void setExternallyExposed(String externallyExposed) {
		this.externallyExposed = externallyExposed;
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

	public String getPoBox() {
		return poBox;
	}

	public void setPoBox(String poBox) {
		this.poBox = poBox;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getSuburb() {
		return suburb;
	}

	public void setSuburb(String suburb) {
		this.suburb = suburb;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getIsoCountryCode() {
		return isoCountryCode;
	}

	public void setIsoCountryCode(String isoCountryCode) {
		this.isoCountryCode = isoCountryCode;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public BigInteger getTaxJurisdictionCode() {
		return taxJurisdictionCode;
	}

	public void setTaxJurisdictionCode(BigInteger taxJurisdictionCode) {
		this.taxJurisdictionCode = taxJurisdictionCode;
	}

	public Integer getAddressMailabilityScore() {
		return addressMailabilityScore;
	}

	public void setAddressMailabilityScore(Integer addressMailabilityScore) {
		this.addressMailabilityScore = addressMailabilityScore;
	}

	public String getAddressMatchScore() {
		return addressMatchScore;
	}

	public void setAddressMatchScore(String addressMatchScore) {
		this.addressMatchScore = addressMatchScore;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getGeoID() {
		return geoID;
	}

	public void setGeoID(String geoID) {
		this.geoID = geoID;
	}

	public String getRkstCode() {
		return rkstCode;
	}

	public void setRkstCode(String rkstCode) {
		this.rkstCode = rkstCode;
	}

	public String getBusniessUnitId() {
		return busniessUnitId;
	}

	public void setBusniessUnitId(String busniessUnitId) {
		this.busniessUnitId = busniessUnitId;
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
		RetrieveFacilityVO other = (RetrieveFacilityVO) obj;
		if (facilityID == null) {
			if (other.facilityID != null)
				return false;
		} else if (!facilityID.equals(other.facilityID))
			return false;
		return true;
	}
	@Override
	public int compareTo(RetrieveFacilityVO o) {
		return this.facilityID.compareTo(o.facilityID);
	}

}
