package net.apmoller.services.cmd.searchfacility.util;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;


// TODO: Auto-generated Javadoc
/**
 * The Class SearchUtil.
 */
public class SearchUtil {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(SearchUtil.class
			.getName());

	/**
	 * Load properties.
	 *
	 * @param propertyFileLocation            the property file location
	 * @return the properties
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	/*public Properties loadProperties(String propertyFileLocation) throws IOException {
		Properties properties = null;
		try (java.io.InputStream propertyStream = Thread.currentThread()
				.getContextClassLoader()
				.getResourceAsStream(propertyFileLocation);) {
			properties = new Properties();
			properties.load(propertyStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOGGER.error(e.getLocalizedMessage());
			throw e;
		}
		return properties;

	}*/





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
