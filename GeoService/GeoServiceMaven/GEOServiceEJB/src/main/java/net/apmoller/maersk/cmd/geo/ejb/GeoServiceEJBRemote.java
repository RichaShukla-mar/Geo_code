package net.apmoller.maersk.cmd.geo.ejb;

import java.util.Map;
import java.util.Properties;

import javax.ejb.Remote;

import com.siperian.sif.client.SiperianClient;

import net.apmoller.maersk.cmd.geo.bo.GEOBO;


@Remote
public interface GeoServiceEJBRemote {

	public Map<String, String> upsertGEOData(GEOBO data);
	
	SiperianClient getSIFClient();

	Properties getEnvironmentProperties();

	String loadEjb();
}
