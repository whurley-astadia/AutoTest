package com.syngenta.sylk.menu_add.pages;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.syngenta.sylk.main.pages.BasePage;

public class PopUpProteinSequenceDataPage extends BasePage {

	protected PopUpProteinSequenceDataPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(id = "sequenceAccessionField")
	private WebElement accessionNo;

	@FindBy(id = "sequenceSequenceField")
	private WebElement sequence;

	@FindBy(css = "div#sequenceDialog6_protein")
	private WebElement cancel;

	@FindBy(css = "span#ui-dialog-title-clobDialog_protein + a")
	private WebElement closeProtein;

	public GeneticFeaturePage clickOnCloseProtein() {
		this.closeProtein.click();
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

	public String getTextFromSeqData() {
		String text = null;
		try {
			text = this.sequence.getText();
		} catch (Exception e) {
			WebElement ele = this.driver
					.findElement(By.id("clobSequenceField"));
			text = ele.getText();
		}
		return text;

	}

}
