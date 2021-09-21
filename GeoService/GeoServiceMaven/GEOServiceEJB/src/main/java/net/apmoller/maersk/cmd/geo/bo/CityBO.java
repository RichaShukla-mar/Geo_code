package net.apmoller.maersk.cmd.geo.bo;

import java.io.Serializable;

public class CityBO extends GEOBO implements Serializable
{
	String cityname;
	String description;
	String maerskcityind;
	String city_valid_from;
	String city_valid_thru;
	String city_rel_valid_from;
	String city_rel_valid_thru;
	String latitude;
	String longitude;
	String status;
	String timezone;
	String daylightsaving;
	String oztimezone;
	String portExists;
	String workaround_type;
	String workaround_type_rowid;
	String relBdaRowid;
	String parentRowid;
	String GDARowid;
	String relRowid;
	String type;
	String codeType;
	String relType;
	String altName_rkst;
	String altCode_rkst;
	String altDesc_rkst;
	String altType_rkst;
	String altStatus_rkst;
	String altName_rkts;
	String altCode_rkts;
	String altDesc_rkts;
	String altType_rkts;
	String altStatus_rkts;
	String altName_model;
	String altCode_model;
	String altDesc_model;
	String altType_model;
	String altStatus_model;
	String bda;
	String bda_delete;
	String bda_insert;
	String bda_update;
	String bda_hub_ind;

	public String getCityname() {
		return cityname;
	}
	public void setCityname(String cityname) {
		this.cityname = cityname;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getmaerskcityind() {
		return maerskcityind;
	}
	public void setmaerskcityind(String maerskcityind) {
		this.maerskcityind = maerskcityind;
	}
	public String getCity_Valid_from() {
		return city_valid_from;
	}
	public void setCity_Valid_from(String valid_from) {
		this.city_valid_from = valid_from;
	}
	public String getCity_Valid_thru() {
		return city_valid_thru;
	}
	public void setCity_Valid_thru(String valid_thru) {
		this.city_valid_thru = valid_thru;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTimezone() {
		return timezone;
	}
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
	public String getDaylightsaving() {
		return daylightsaving;
	}
	public void setDaylightsaving(String daylightsaving) {
		this.daylightsaving = daylightsaving;
	}
	public String getOztimezone() {
		return oztimezone;
	}
	public void setOztimezone(String oztimezone) {
		this.oztimezone = oztimezone;
	}
	public String getPortExists() {
		return portExists;
	}
	public void setPortExists(String portExists) {
		this.portExists = portExists;
	}
	public String getWorkaround_type() {
		return workaround_type;
	}
	public void setWorkaround_type(String workaround_type) {
		this.workaround_type = workaround_type;
	}
	public String getWorkaround_type_rowid() {
		return workaround_type_rowid;
	}
	public void setWorkaround_type_rowid(String workaround_type_rowid) {
		this.workaround_type_rowid = workaround_type_rowid;
	}
	public String getRelBdaRowid() {
		return relBdaRowid;
	}
	public void setRelBdaRowid(String relBdaRowid) {
		this.relBdaRowid = relBdaRowid;
	}
	public String getParentRowid() {
		return parentRowid;
	}
	public void setParentRowid(String parentRowid) {
		this.parentRowid = parentRowid;
	}
	public String getGDARowid() {
		return GDARowid;
	}
	public void setGDARowid(String gDARowid) {
		GDARowid = gDARowid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCodeType() {
		return codeType;
	}
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}
	public String getRelType() {
		return relType;
	}
	public void setRelType(String relType) {
		this.relType = relType;
	}
	public String getAltName_rkst() {
		return altName_rkst;
	}
	public void setAltName_rkst(String altName_rkst) {
		this.altName_rkst = altName_rkst;
	}
	public String getAltCode_rkst() {
		return altCode_rkst;
	}
	public void setAltCode_rkst(String altCode_rkst) {
		this.altCode_rkst = altCode_rkst;
	}
	public String getAltDesc_rkst() {
		return altDesc_rkst;
	}
	public void setAltDesc_rkst(String altDesc_rkst) {
		this.altDesc_rkst = altDesc_rkst;
	}
	public String getAltType_rkst() {
		return altType_rkst;
	}
	public void setAltType_rkst(String altType_rkst) {
		this.altType_rkst = altType_rkst;
	}
	public String getAltStatus_rkst() {
		return altStatus_rkst;
	}
	public void setAltStatus_rkst(String altStatus_rkst) {
		this.altStatus_rkst = altStatus_rkst;
	}
	public String getAltName_rkts() {
		return altName_rkts;
	}
	public void setAltName_rkts(String altName_rkts) {
		this.altName_rkts = altName_rkts;
	}
	public String getAltCode_rkts() {
		return altCode_rkts;
	}
	public void setAltCode_rkts(String altCode_rkts) {
		this.altCode_rkts = altCode_rkts;
	}
	public String getAltDesc_rkts() {
		return altDesc_rkts;
	}
	public void setAltDesc_rkts(String altDesc_rkts) {
		this.altDesc_rkts = altDesc_rkts;
	}
	public String getAltType_rkts() {
		return altType_rkts;
	}
	public void setAltType_rkts(String altType_rkts) {
		this.altType_rkts = altType_rkts;
	}
	public String getAltStatus_rkts() {
		return altStatus_rkts;
	}
	public void setAltStatus_rkts(String altStatus_rkts) {
		this.altStatus_rkts = altStatus_rkts;
	}
	public String getAltName_model() {
		return altName_model;
	}
	public void setAltName_model(String altName_model) {
		this.altName_model = altName_model;
	}
	public String getAltCode_model() {
		return altCode_model;
	}
	public void setAltCode_model(String altCode_model) {
		this.altCode_model = altCode_model;
	}
	public String getAltDesc_model() {
		return altDesc_model;
	}
	public void setAltDesc_model(String altDesc_model) {
		this.altDesc_model = altDesc_model;
	}
	public String getAltType_model() {
		return altType_model;
	}
	public void setAltType_model(String altType_model) {
		this.altType_model = altType_model;
	}
	public String getAltStatus_model() {
		return altStatus_model;
	}
	public void setAltStatus_model(String altStatus_model) {
		this.altStatus_model = altStatus_model;
	}
	public String getCity_rel_valid_from() {
		return city_rel_valid_from;
	}
	public void setCity_rel_valid_from(String city_rel_valid_from) {
		this.city_rel_valid_from = city_rel_valid_from;
	}
	public String getCity_rel_valid_thru() {
		return city_rel_valid_thru;
	}
	public void setCity_rel_valid_thru(String city_rel_valid_thru) {
		this.city_rel_valid_thru = city_rel_valid_thru;
	}
	public String getBda() {
		return bda;
	}
	public void setBda(String bda) {
		this.bda = bda;
	}
	public String getBda_delete() {
		return bda_delete;
	}
	public void setBda_delete(String bda_delete) {
		this.bda_delete = bda_delete;
	}
	public String getBda_insert() {
		return bda_insert;
	}
	public void setBda_insert(String bda_insert) {
		this.bda_insert = bda_insert;
	}
	public String getBda_update() {
		return bda_update;
	}
	public void setBda_update(String bda_update) {
		this.bda_update = bda_update;
	}
	public String getBda_hub_ind() {
		return bda_hub_ind;
	}
	public void setBda_hub_ind(String bda_hub_ind) {
		this.bda_hub_ind = bda_hub_ind;
	}
	public String getRelRowid() {
		return relRowid;
	}
	public void setRelRowid(String relRowid) {
		this.relRowid = relRowid;
	}
	@Override
	public String toString() {
		return "CityBO [cityname=" + cityname + ", description=" + description
				+ ", maerskcityind=" + maerskcityind
				+ ", city_valid_from=" + city_valid_from + ", city_valid_thru="
				+ city_valid_thru + ", city_rel_valid_from="
				+ city_rel_valid_from + ", city_rel_valid_thru="
				+ city_rel_valid_thru + ", latitude=" + latitude
				+ ", longitude=" + longitude + ", status=" + status
				+ ", timezone=" + timezone + ", daylightsaving="
				+ daylightsaving + ", oztimezone=" + oztimezone
				+ ", portExists=" + portExists + ", workaround_type="
				+ workaround_type + ", relBdaRowid=" + relBdaRowid
				+ ", parentRowid=" + parentRowid + ", GDARowid=" + GDARowid
				+ ", type=" + type + ", codeType=" + codeType + ", relType="
				+ relType + ", altName_rkst=" + altName_rkst
				+ ", altCode_rkst=" + altCode_rkst + ", altDesc_rkst="
				+ altDesc_rkst + ", altType_rkst=" + altType_rkst
				+ ", altStatus_rkst=" + altStatus_rkst + ", altName_rkts="
				+ altName_rkts + ", altCode_rkts=" + altCode_rkts
				+ ", altDesc_rkts=" + altDesc_rkts + ", altType_rkts="
				+ altType_rkts + ", altStatus_rkts=" + altStatus_rkts
				+ ", altName_model=" + altName_model + ", altCode_model="
				+ altCode_model + ", altDesc_model=" + altDesc_model
				+ ", altType_model=" + altType_model + ", altStatus_model="
				+ altStatus_model + ", bda=" + bda + ", bda_delete="
				+ bda_delete + ", bda_insert=" + bda_insert + ", bda_update="
				+ bda_update + ", bda_hub_ind=" + bda_hub_ind + "]";
	}


}
