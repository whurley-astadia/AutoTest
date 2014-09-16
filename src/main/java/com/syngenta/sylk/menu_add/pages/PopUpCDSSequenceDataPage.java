package com.syngenta.sylk.menu_add.pages;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.syngenta.sylk.main.pages.BasePage;

public class PopUpCDSSequenceDataPage extends BasePage {

	protected PopUpCDSSequenceDataPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(id = "sequenceAccessionField")
	private WebElement AccessionNo;

	@FindBy(id = "sequenceSequenceField")
	private WebElement sequence;

	@FindBy(css = "div#sequenceDialog6_cds")
	private WebElement cancel;

	@FindBy(id = "ui-dialog-title-sequenceDialog6_cdna")
	private WebElement close;

	public GeneticFeaturePage clickOnClose() {
		this.close.findElement(By.tagName("a")).findElement(By.tagName("span"))
				.click();
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
