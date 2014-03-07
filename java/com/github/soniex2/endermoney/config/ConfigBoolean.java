package com.github.soniex2.endermoney.config;

@SuppressWarnings("unused")
public class ConfigBoolean {

	private String comment;
	private boolean value = false;

	private ConfigBoolean() {
	}

	public ConfigBoolean(String comment, boolean defaultValue) {
		this.comment = comment;
		this.value = defaultValue;
	}

	public boolean getValue() {
		return value;
	}
}
