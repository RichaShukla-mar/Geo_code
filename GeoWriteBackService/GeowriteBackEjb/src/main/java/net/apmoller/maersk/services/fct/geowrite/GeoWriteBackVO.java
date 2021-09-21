package net.apmoller.maersk.services.fct.geowrite;

import java.sql.Date;

public class GeoWriteBackVO {
	
	private String addressRowid;
	private String typtypecode;
	private Date   validFromDate;
	private Date   validThruDate;
	public String getAddressRowid() {
		return addressRowid;
	}
	public void setAddressRowid(String addressRowid) {
		this.addressRowid = addressRowid;
	}
	
	public String getTyptypecode() {
		return typtypecode;
	}
	public void setTyptypecode(String typtypecode) {
		this.typtypecode = typtypecode;
	}
	public Date getValidFromDate() {
		return validFromDate;
	}
	public void setValidFromDate(Date validFromDate) {
		this.validFromDate = validFromDate;
	}
	public Date getValidThruDate() {
		return validThruDate;
	}
	public void setValidThruDate(Date validThruDate) {
		this.validThruDate = validThruDate;
	}
	
	
		
}
