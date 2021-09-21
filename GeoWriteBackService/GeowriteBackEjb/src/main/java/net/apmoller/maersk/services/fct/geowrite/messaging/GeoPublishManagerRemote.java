package net.apmoller.maersk.services.fct.geowrite.messaging;

import javax.ejb.Remote;
import javax.naming.NamingException;

// TODO: Auto-generated Javadoc
/**
 * The Interface BvdPublishManagerRemote.
 */
@Remote
public interface GeoPublishManagerRemote {
	/**
	 * H beat.
	 *
	 * @return the string
	 * @throws NamingException the naming exception
	 */
	String hBeat() throws NamingException;

}
