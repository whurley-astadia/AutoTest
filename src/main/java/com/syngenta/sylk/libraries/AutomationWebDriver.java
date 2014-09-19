package com.syngenta.sylk.libraries;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class AutomationWebDriver {

	private String browser;

	public AutomationWebDriver(String browser) {
		this.browser = browser;
	}

	public WebDriver getDriver() {
		if (StringUtils.equalsIgnoreCase(this.browser, "ie")
				|| StringUtils.equalsIgnoreCase(
						StringUtils.deleteWhitespace(this.browser),
						"internetexplorer")) {
						
			// Went back to var code			
			System.setProperty("webdriver.ie.driver", this.getClass()
					.getResource("/IEDriverServer.exe").getPath());
			//System.setProperty("webdriver.ie.driver","C:\\Program Files (x86)\\Jenkins\\workspace\\MvnSylk\\AutoTest\\target\\classes\\IEDriverServer.exe");
					

			// WebDriver driver;
			// DesiredCapabilities caps =
			// DesiredCapabilities.internetExplorer();
			// caps.setCapability("nativeEvents", false);
			// driver = new InternetExplorerDriver(caps);
			DesiredCapabilities capabilities = DesiredCapabilities
					.internetExplorer();

			// Method and Description - void setCapability(java.lang.String
			// capabilityName, boolean value)
			// capabilities
			// .setCapability(
			// InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
			// true);
			capabilities.setCapability(
					InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
			// InternetExplorerDriver(Capabilities capabilities)
			WebDriver driver = new InternetExplorerDriver(capabilities);

			driver.manage().window().maximize();

			return driver;

		} else if (StringUtils.equalsIgnoreCase(this.browser, "chrome")) {
			System.setProperty("webdriver.chrome.driver",
					"C:\\Nisha\\Syngenta\\src\\main\\resources\\chromedriver.exe");
			Point targetPosition = new Point(0, 0);
			ChromeDriver driver = new ChromeDriver();
			driver.manage().window().setPosition(targetPosition);

			Dimension targetSize = new Dimension(1920, 1080);
			driver.manage().window().setSize(targetSize);
			return driver;
		} else {

			return new FirefoxDriver();
		}
	}
}
