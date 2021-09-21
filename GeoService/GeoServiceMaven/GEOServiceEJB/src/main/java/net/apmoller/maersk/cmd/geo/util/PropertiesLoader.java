package net.apmoller.maersk.cmd.geo.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;


public class PropertiesLoader {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(PropertiesLoader.class.getName());

	/**
	 * Load properties.
	 *
	 * @param propertyFileLocation
	 *            the property file location
	 * @return the properties
	 */
	public Properties loadProperties(String propertyFileLocation) throws FileNotFoundException, IOException {
		Properties properties = null;
		try (java.io.InputStream propertyStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(propertyFileLocation);) {
			properties = new Properties();
			properties.load(propertyStream);
		} catch (FileNotFoundException e) {
			LOGGER.fatal(e.getLocalizedMessage());
			throw e;
		} catch (IOException e) {
			LOGGER.fatal(e.getLocalizedMessage());
			throw e;
		}
		return properties;

	}

	/**
	 * Gets the runtime system.
	 *
	 * @return the runtime system
	 */
	public Enum<ServerEnvironmentEnum> getRuntimeSystem() {
		Enum<ServerEnvironmentEnum> value = null;
		try {
			value = ServerEnvironmentEnum.valueOf(System.getenv("CMD_ENVIRONMENT"));
		} catch (Exception e) {
			LOGGER.warn(e.getLocalizedMessage());
			throw e;
		}
		if (null == value) {
			value = ServerEnvironmentEnum.TEST;
		}
		return value;
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
	
	/*
	static public Properties loadProperties(){
	
		Properties prop = new Properties();
		
		try{
			
			InputStream stream = PropertiesLoader.class.getResourceAsStream ("multiupdate.properties");
			
			prop.load(stream);
			
			
		}catch(Exception exp){
			exp.printStackTrace();
		}
		
		return prop;
	}
	*/
}
