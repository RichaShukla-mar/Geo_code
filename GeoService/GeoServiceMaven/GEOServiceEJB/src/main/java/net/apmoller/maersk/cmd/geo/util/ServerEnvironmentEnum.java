package net.apmoller.maersk.cmd.geo.util;

public enum ServerEnvironmentEnum {

	/** The production. */
	PRODUCTION, 
 /** The test. */
 TEST, 
 /** The development. */
 DEVELOPMENT;

	/**
	 * Value.
	 *
	 * @return the string
	 */
	public String value() {
		return name();
	}

	/**
	 * From value.
	 *
	 * @param v the v
	 * @return the server environment enum
	 */
	public static ServerEnvironmentEnum fromValue(String v) {
		return valueOf(v);
	}

}
