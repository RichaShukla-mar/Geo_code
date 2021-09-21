package net.apmoller.maersk.services.fct.geowrite.messaging;

import javax.ejb.Local;
import javax.naming.NamingException;

// TODO: Auto-generated Javadoc
/**
 * The Interface BvdPublishManagerLocal.
 */
@Local
public interface GeoPublishManagerLocal {

	
	/**
	 * H beat.
	 *
	 * @return the string
	 * @throws NamingException the naming exception
	 */
	String hBeat() throws NamingException;
}
