package com.syngenta.sylk.menu_add.pages;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.syngenta.sylk.main.pages.BasePage;

public class PopUpgDNASequenceDataPage extends BasePage {

	protected PopUpgDNASequenceDataPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(css = "div#sequenceDialog6_gdna input#sequenceAccessionField")
	private WebElement access_number;

	@FindBy(css = "div#sequenceDialog6_gdna textarea#sequenceSequenceField")
	private WebElement sequence;

	@FindBy(css = "div#sequenceDialog6_gdna")
	private WebElement cancel;

	@FindBy(id = "ui-dialog-title-clobDialog_gd")
	private WebElement close;

	// @FindBy(css = "span#ui-dialog-title-sequenceDialog6_gdna + a")
	// private WebElement closeGDNA;

	// click cancel button and close the popup
	public GeneticFeaturePage clickOnCancel() {
		GeneticFeaturePage page = null;
		List<WebElement> buttons = this.cancel
				.findElements(By.tagName("input"));
		for (WebElement button : buttons) {
			if (StringUtils.equalsIgnoreCase(button.getAttribute("value"),
					"cancel")) {
				button.click();
				this.waitForPageToLoad();
				page = new GeneticFeaturePage(this.driver);
				PageFactory.initElements(this.driver, page);
			}
		}
		return page;

	}

	// close the popup by clicking the X sign in the upper righthand corner.
	public GeneticFeaturePage clickOnClose() {
		this.close.findElement(By.tagName("a")).findElement(By.tagName("span"))
				.click();
		GeneticFeaturePage Page = new GeneticFeaturePage(this.driver);
		PageFactory.initElements(this.driver, Page);

		return Page;

	}

	// public GeneticFeaturePage clickOnCloseGDNA() {
	// this.closeGDNA.click();
	// GeneticFeaturePage page = new GeneticFeaturePage(this.driver);
	// PageFactory.initElements(this.driver, page);
	// return page;
	// }

}
