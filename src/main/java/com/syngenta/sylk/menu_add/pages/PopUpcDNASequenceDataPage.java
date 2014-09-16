package com.syngenta.sylk.menu_add.pages;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.syngenta.sylk.main.pages.BasePage;

public class PopUpcDNASequenceDataPage extends BasePage {

	protected PopUpcDNASequenceDataPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(id = "clobAccessionField")
	private WebElement accessionNumber;

	@FindBy(id = "clobSequenceField")
	private WebElement sequence;

	@FindBy(css = "span#ui-dialog-title-clobDialog_cdna + a.ui-dialog-titlebar-close.ui-corner-all")
	private WebElement close;

	@FindBy(css = "div#sequenceDialog6_cdna")
	private WebElement cancel;

	public GeneticFeaturePage closePopUpcDNASeq() {

		try {
			this.close.findElement(By.tagName("span")).click();
		} catch (Exception e) {
			WebElement span = this.driver
					.findElement(By
							.cssSelector("a.ui-dialog-titlebar-close.ui-corner-all span.ui-icon.ui-icon-closethick"));
			span.click();
		}
		GeneticFeaturePage page = new GeneticFeaturePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;

	}

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
}
