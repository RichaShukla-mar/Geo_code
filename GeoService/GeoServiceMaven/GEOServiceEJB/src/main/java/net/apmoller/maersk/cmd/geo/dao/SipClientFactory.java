package net.apmoller.maersk.cmd.geo.dao;

import java.io.IOException;
import java.util.Properties;

import net.apmoller.maersk.cmd.geo.util.GEOConstants;
import net.apmoller.maersk.cmd.geo.util.GEOUtils;
import net.apmoller.maersk.cmd.geo.util.PropertiesLoader;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import com.siperian.sif.client.EjbSiperianClient;
import com.siperian.sif.client.SiperianClient;

public class SipClientFactory extends BasePooledObjectFactory<SiperianClient> {

	public synchronized SiperianClient getSIFClient() {
		SiperianClient sifClient = null;
		PropertiesLoader searchUtil = new PropertiesLoader();
		try {
			sifClient = EjbSiperianClient.newSiperianClient(searchUtil.loadProperties(GEOConstants.SIP_PROPERTIES_FILE_NAME));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
		}
		return sifClient;

	}

	@Override
	public SiperianClient create() {
		return getSIFClient();
	}
	
	@Override
	public boolean validateObject(PooledObject<SiperianClient> p) {
		// TODO Auto-generated method stub
		return null!=p.getObject();
	}

	/**
	 * Use the default PooledObject implementation.
	 */
	@Override
	public PooledObject<SiperianClient> wrap(SiperianClient buffer) {
		return new DefaultPooledObject<SiperianClient>(buffer);
	}



	

}
