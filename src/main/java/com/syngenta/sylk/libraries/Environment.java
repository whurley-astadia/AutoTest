package com.syngenta.sylk.libraries;

public interface Environment {

	public static long DEFAULT_WAIT_TIME = EnvironmentPropertyUtil
			.getInstance().getDefaultWaitTime();
	public static String PRODUCT_URL = EnvironmentPropertyUtil.getInstance()
			.getUrl();
	public static String browser = EnvironmentPropertyUtil.getInstance()
			.getBrowser();
}
