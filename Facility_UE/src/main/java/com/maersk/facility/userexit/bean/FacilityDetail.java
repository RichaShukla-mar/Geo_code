package com.maersk.facility.userexit.bean;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"WeightLimitCraneKg", "WeightLimitYardKg", "VesselAgent", "GPSFlag", "GSMFlag", "OceanFreightPricing", "FacilityTypes"})
@XmlAccessorType(XmlAccessType.FIELD)
public class FacilityDetail {
  
  private String WeightLimitCraneKg;
  
  private String WeightLimitYardKg;
  
  private String VesselAgent;
  
  private String GPSFlag;
  
  private String GSMFlag;
  
  private String OceanFreightPricing;
 
  
  @XmlElement(name = "FacilityType")
  private List<FacilityType> FacilityTypes;


/**
 * @return the WeightLimitCraneKg
 */
public String getWeightLimitCraneKg() {
	return WeightLimitCraneKg;
}


/**
 * @param weightLimitCraneKg the weightLimitCraneKg to set
 */

public void setWeightLimitCraneKg(String WeightLimitCraneKg) {
	this.WeightLimitCraneKg = WeightLimitCraneKg;
}


/**
 * @return the weightLimitYardKg
 */
public String getWeightLimitYardKg() {
	return WeightLimitYardKg;
}


/**
 * @param weightLimitYardKg the weightLimitYardKg to set
 */

public void setWeightLimitYardKg(String WeightLimitYardKg) {
	this.WeightLimitYardKg = WeightLimitYardKg;
}


/**
 * @return the VesselAgent
 */
public String getVesselAgent() {
	return VesselAgent;
}


/**
 * @param VesselAgent the VesselAgent to set
 */

public void setVesselAgent(String VesselAgent) {
	this.VesselAgent = VesselAgent;
}


/**
 * @return the gPSFlag
 */
public String getGPSFlag() {
	return GPSFlag;
}


/**
 * @param gPSFlag the gPSFlag to set
 */

public void setGPSFlag(String GPSFlag) {
	this.GPSFlag = GPSFlag;
}


/**
 * @return the gSMFlag
 */
public String getGSMFlag() {
	return GSMFlag;
}


/**
 * @param gSMFlag the gSMFlag to set
 */

public void setGSMFlag(String GSMFlag) {
	this.GSMFlag = GSMFlag;
}


/**
 * @return the oceanFreightPricing
 */
public String getOceanFreightPricing() {
	return OceanFreightPricing;
}


/**
 * @param oceanFreightPricing the oceanFreightPricing to set
 */

public void setOceanFreightPricing(String OceanFreightPricing) {
	this.OceanFreightPricing = OceanFreightPricing;
}


/**
 * @return the facilityTypes
 */
public List<FacilityType> getFacilityTypes() {
	return FacilityTypes;
}


/**
 * @param facilityTypes the facilityTypes to set
 */

public void setFacilityTypes(List<FacilityType> FacilityTypes) {
	this.FacilityTypes = FacilityTypes;
}


@Override
public String toString() {
	return "FacilityDetail [WeightLimitCraneKg=" + WeightLimitCraneKg + ", WeightLimitYardKg=" + WeightLimitYardKg
			+ ", VesselAgent=" + VesselAgent + ", GPSFlag=" + GPSFlag + ", GSMFlag=" + GSMFlag
			+ ", OceanFreightPricing=" + OceanFreightPricing + ", facilityTypes=" + FacilityTypes + "]";
}



 }
