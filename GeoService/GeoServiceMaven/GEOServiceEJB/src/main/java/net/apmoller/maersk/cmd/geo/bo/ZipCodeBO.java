package net.apmoller.maersk.cmd.geo.bo;

public class ZipCodeBO extends GEOBO {
	
	String postalname;
	String postalcode;
	String valid_from;
	String valid_thru;
	String parentRowid;
	String parentName;
	String GDARowid;
	String type;
	String status;
	String description;
	String codeType;
	String relType;
	
	public ZipCodeBO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getPostalname() {
		return postalname;
	}
	public void setPostalname(String postalname) {
		this.postalname = postalname;
	}
	public String getPostalcode() {
		return postalcode;
	}
	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}
	public String getValid_thru() {
		return valid_thru;
	}
	public void setValid_thru(String valid_thru) {
		this.valid_thru = valid_thru;
	}
	public String getValid_from() {
		return valid_from;
	}
	public void setValid_from(String valid_from) {
		this.valid_from = valid_from;
	}
	public String getParentRowid() {
		return parentRowid;
	}
	public void setParentRowid(String parentRowid) {
		this.parentRowid = parentRowid;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "ZipCodeBO [postalname=" + postalname + ", postalcode="
				+ postalcode + ", valid_from=" + valid_from + ", valid_thru="
				+ valid_thru + ", parentRowid=" + parentRowid + ", parentName="
				+ parentName + ", GDARowid=" + GDARowid + ", type=" + type
				+ ", status=" + status + ", description=" + description
				+ ", codeType=" + codeType + ", relType=" + relType + "]";
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	
	
}
