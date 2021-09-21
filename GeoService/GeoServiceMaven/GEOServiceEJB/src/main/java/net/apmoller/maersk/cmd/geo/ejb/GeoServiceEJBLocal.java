package net.apmoller.maersk.cmd.geo.ejb;

import java.util.Map;
import java.util.Properties;

import javax.ejb.Local;

import com.siperian.sif.client.SiperianClient;

import net.apmoller.maersk.cmd.geo.bo.GEOBO;


@Local
public interface GeoServiceEJBLocal {

    public Map<String, String> upsertGEOData(GEOBO data);

	SiperianClient getSIFClient();

	Properties getEnvironmentProperties();

	String loadEjb();

	

}
