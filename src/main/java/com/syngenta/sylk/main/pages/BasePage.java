package com.syngenta.sylk.main.pages;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;
import com.syngenta.sylk.libraries.AutomationWebDriver;
import com.syngenta.sylk.libraries.CommonLibrary;
import com.syngenta.sylk.libraries.Environment;
import com.syngenta.sylk.libraries.PageTitles;
import com.syngenta.sylk.libraries.SyngentaException;
import com.syngenta.sylk.menu_add.pages.BLASTSearchResultPage;
import com.syngenta.sylk.menu_add.pages.GeneticFeaturePage;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

public class BasePage implements Environment {

	protected WebDriver driver;
	protected LandingPage landingPage;
	protected BasePage(WebDriver driver) {
		this.driver = driver;
		this.waitForPageToLoad();
	}

	public BasePage browserBack() {
		this.driver.navigate().back();
		this.waitForPageToLoad();
		new CommonLibrary().slowDown();
		String title = this.getPageTitle();
		if (StringUtils.equalsIgnoreCase(title,
				PageTitles.blast_search_results_page_title)) {
			BLASTSearchResultPage page = new BLASTSearchResultPage(this.driver);
			PageFactory.initElements(this.driver, page);
			return page;
		} else if (StringUtils.equalsIgnoreCase(title,
				PageTitles.genetic_feature_page_title)) {
			GeneticFeaturePage page = new GeneticFeaturePage(this.driver);
			PageFactory.initElements(this.driver, page);
			return page;
		}

		return null;
	}
	protected BasePage(String url) {

		CommonLibrary common = new CommonLibrary();
		try {
			// Process P = null;
			// if (!StringUtils.equalsIgnoreCase(Environment.browser, "ie")) {
			// URL u = this.getClass().getResource("/EnterUserNamePwd.exe");
			// P = Runtime.getRuntime().exec(u.getPath());
			// }
			this.createWebDriver();
			// url = "http://"+userName+"."+pwd+"@"+url;
			this.driver.get("http://" + url);
			// if (!StringUtils.equalsIgnoreCase(browser, browser)) {
			// P.destroy();
			// }
			this.waitForPageToLoad();
			if (!StringUtils.equalsIgnoreCase(this.getPageTitle(),
					PageTitles.landing_page_title)) {
				throw new SyngentaException("Could not login to url : " + url);
			}
		} catch (Exception e) {
			throw new SyngentaException(
					"Could not open application and login. Exception:"
							+ common.getStackTrace(e));
		}
	}
	private void createWebDriver() {

		this.driver = new AutomationWebDriver(this.browser).getDriver();
	}

	public WebDriverBackedSelenium getSeleniumHandle() {

		return new WebDriverBackedSelenium(this.driver,
				this.driver.getCurrentUrl());
	}

	// public void waitForPageToLoad() {
	//
	// this.getSeleniumHandle().waitForPageToLoad(
	// Long.toString(DEFAULT_WAIT_TIME));
	// }

	@SuppressWarnings("unchecked")
	public void waitForPageToLoad() {
		// @SuppressWarnings("rawtypes")
		// FluentWait wait = new WebDriverWait(this.driver, DEFAULT_WAIT_TIME);
		//
		// try {
		// wait.until(ExpectedConditions.visibilityOfElementLocated(By
		// .tagName("h1")));
		// } catch (Exception e) {
		// }
		this.getSeleniumHandle().waitForPageToLoad("" + DEFAULT_WAIT_TIME);

		@SuppressWarnings("rawtypes")
		FluentWait wait = new WebDriverWait(this.driver, DEFAULT_WAIT_TIME);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath("//*[not (.='')]")));
	}

	@SuppressWarnings("unchecked")
	public void waitForPopUpToLoad() {
		@SuppressWarnings("rawtypes")
		FluentWait wait = new WebDriverWait(this.driver, DEFAULT_WAIT_TIME);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath("//*[not (.='')]")));
	}

	public void waitForAjax() {
		int timeoutInSeconds = 30;
		System.out
				.println("Checking active ajax calls by calling jquery.active");
		try {
			if (this.driver instanceof JavascriptExecutor) {
				JavascriptExecutor jsDriver = (JavascriptExecutor) this.driver;

				for (int i = 0; i < timeoutInSeconds; i++) {
					Object numberOfAjaxConnections = jsDriver
							.executeScript("return jQuery.active");
					// return should be a number
					if (numberOfAjaxConnections instanceof Long) {
						Long n = (Long) numberOfAjaxConnections;
						System.out
								.println("Number of active jquery ajax calls: "
										+ n);
						if (n.longValue() == 0L) {
							break;
						}
					}
					Thread.sleep(1000);
				}
			} else {
				System.out.println("Web driver: " + this.driver
						+ " cannot execute javascript");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public WebElement waitForWebElement(final By locator) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(this.driver)
				.withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(5, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);

		WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				return driver.findElement(locator);
			}
		});

		return foo;
	}

	public String getPageTitle() {
		return this.driver.getTitle();
	}

	public void driverQuit() {
		if (this.driver != null) {
			this.driver.quit();
		}
	}

}
