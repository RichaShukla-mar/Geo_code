package net.apmoller.maersk.services.fct.geowrite.messaging;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

// TODO: Auto-generated Javadoc
/**
 * The Class SearchUtil.
 */
public class PropUtil {

	/** The Constant LOGGER. */
	private static final Logger	LOGGER		= Logger.getLogger(PropUtil.class.getName());
	
	/** The properties. */
	private static Properties	properties	= null;

	/**
	 * Load properties.
	 *
	 * @param propertyFileLocation
	 *            the property file location
	 * @return the properties
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public Properties loadProperties(String propertyFileLocation) throws IOException {
		readProperties(propertyFileLocation);
		return properties;

	}

	/**
	 * Read properties.
	 *
	 * @param propertyFileLocation the property file location
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected synchronized void readProperties(String propertyFileLocation) throws IOException {
		try (java.io.InputStream propertyStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(propertyFileLocation);) {
			properties = new Properties();
			properties.load(propertyStream);
		} catch (IOException e) {

			try (java.io.InputStream propertyStream = this.getClass().getResourceAsStream(propertyFileLocation);) {

				properties = new Properties();
				properties.load(propertyStream);
			} catch (IOException e1) {
				LOGGER.fatal(e1);
				throw e1;
			}

			LOGGER.fatal(e);
			throw e;
		}
	}

	/**
	 * Join strings.
	 *
	 * @param strings
	 *            the strings
	 * @param separator
	 *            the separator
	 * @return the string
	 */
	public String joinStrings(Iterable<String> strings, String separator) {
		StringBuilder sb = new StringBuilder();
		String sep = "";
		for (String s : strings) {
			sb.append(sep).append(s);
			sep = separator;
		}
		return sb.toString();
	}

}
