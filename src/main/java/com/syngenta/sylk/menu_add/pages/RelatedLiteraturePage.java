package com.syngenta.sylk.menu_add.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.syngenta.sylk.main.pages.MenuPage;

public class RelatedLiteraturePage extends MenuPage {

	protected RelatedLiteraturePage(WebDriver driver) {
		super(driver);
	}

	@FindBy(css = "tr.alt td:nth-child(5)")
	private WebElement viewDetails;

	public LiteratureDetailPage clickViewDetail() {
		String script = "return jQuery.find(\"input[value='View Details']\")[0]";;
		JavascriptExecutor js = (JavascriptExecutor) this.driver;
		WebElement element = (WebElement) js.executeScript(script);
		element.click();
		this.waitForPageToLoad();
		this.waitForAjax();
		LiteratureDetailPage page = new LiteratureDetailPage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}
}
