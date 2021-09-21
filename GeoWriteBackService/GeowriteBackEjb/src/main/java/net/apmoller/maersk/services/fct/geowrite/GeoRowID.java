package net.apmoller.maersk.services.fct.geowrite;

import java.util.HashMap;
import java.util.Map;

public class GeoRowID {

	private String GdaAreaRowid;
	private String GdaAreaRelRowid;
	private String TmpPhylcnRowid;
	private String GdaSiteRowid;
	private Map<String, String> altcodemap = new HashMap<String, String>();
	private Map<String, String> unAltcodemap = new HashMap<String, String>();

	public Map<String, String> getAltcodemap() {
		return altcodemap;
	}

	public void setAltcodemap(Map<String, String> altcodemap) {
		this.altcodemap = altcodemap;
	}

	public String getGdaAreaRowid() {
		return GdaAreaRowid;
	}

	public void setGdaAreaRowid(String gdaAreaRowid) {
		GdaAreaRowid = gdaAreaRowid;
	}

	public String getGdaAreaRelRowid() {
		return GdaAreaRelRowid;
	}

	public void setGdaAreaRelRowid(String gdaAreaRelRowid) {
		GdaAreaRelRowid = gdaAreaRelRowid;
	}

	public String getTmpPhylcnRowid() {
		return TmpPhylcnRowid;
	}

	public void setTmpPhylcnRowid(String tmpPhylcnRowid) {
		TmpPhylcnRowid = tmpPhylcnRowid;
	}

	public String getGdaSiteRowid() {
		return GdaSiteRowid;
	}

	public void setGdaSiteRowid(String gdaSiteRowid) {
		GdaSiteRowid = gdaSiteRowid;
	}

	public Map<String, String> getUnAltcodemap() {
		return unAltcodemap;
	}

	public void setUnAltcodemap(Map<String, String> unAltcodemap) {
		this.unAltcodemap = unAltcodemap;
	}
	
}
