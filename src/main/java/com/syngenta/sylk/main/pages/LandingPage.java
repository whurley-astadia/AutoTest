package com.syngenta.sylk.main.pages;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.syngenta.sylk.libraries.PageTitles;
import com.syngenta.sylk.libraries.SyngentaException;

public class LandingPage extends BasePage {

	private LandingPage() {
		super(PRODUCT_URL);
	}

	public static LandingPage getLandingPage() {
		return new LandingPage();
	}

	public HomePage goToHomePage() {
		HomePage homePage = null;
		WebElement goToSylkButton = this.driver.findElement(By
				.cssSelector(".btn.floatright"));
		goToSylkButton.click();
		this.waitForPageToLoad();
		if (StringUtils.equalsIgnoreCase(this.getPageTitle(),
				PageTitles.home_page_title)) {
			homePage = new HomePage(this.driver);
			PageFactory.initElements(this.driver, homePage);
		} else {
			throw new SyngentaException("Home Page did not load up correctly.");
		}

		return homePage;
	}
	public void closeSession() {
		super.driverQuit();
	}
}
