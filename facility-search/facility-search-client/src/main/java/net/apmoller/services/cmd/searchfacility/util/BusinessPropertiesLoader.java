package net.apmoller.services.cmd.searchfacility.util;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class BusinessPropertiesLoader {
	private static final String BUNDLE_NAME = "business"; 
	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(BusinessPropertiesLoader.class.getName());  


	 private static final ResourceBundle RESOURCE_BUNDLE =
	 ResourceBundle.getBundle(BUNDLE_NAME,Locale.getDefault(),Thread.currentThread().getContextClassLoader());



	private BusinessPropertiesLoader() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			LOGGER.info(e);
			return '!' + key + '!';
		}
	}
}
