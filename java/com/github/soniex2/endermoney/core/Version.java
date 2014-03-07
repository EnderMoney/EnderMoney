package com.github.soniex2.endermoney.core;

public final class Version {

	/**
	 * Mod version.
	 */
	public static final String VERSION = "@VERSION@";

	public static final String CODEBASE = "@CODEBASE@";
	
	public static final String BUILD_NUMBER = "@BUILD_NUMBER@";
	/**
	 * Version string used by the main mod class.
	 */
	public static final String MOD_VERSION = VERSION + "." + BUILD_NUMBER + " " + CODEBASE;
}
