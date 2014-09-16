package com.syngenta.sylk.menu_add.pages;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.syngenta.sylk.main.pages.MenuPage;

public class ProjectLeadPage extends MenuPage {

	@FindBy(css = "form#projectLeadDataEntryForm div.formsFormBd div.formsFormBdHalf.formFirstHalf div.formsFormBlock:nth-child(1) div:nth-child(2)")
	WebElement leadName;

	@FindBy(css = "form#projectLeadDataEntryForm div.formsFormBd div.formsFormBdHalf.formFirstHalf div.formsFormBlock:nth-child(2) div:nth-child(2)")
	WebElement promotingPersonName;

	protected ProjectLeadPage(WebDriver driver) {
		super(driver);
	}

	public String getLeadName() {
		return this.leadName.getText();
	}

	public String getPromotingPersonName() {
		return this.promotingPersonName.getText();
	}

	public void enterNominationRationale(String string) {
		WebElement textArea = this.driver.findElement(By
				.id("projectLeadWrapper.projectLead.nominationRationale"));
		textArea.sendKeys(string);
	}
	public GeneticFeaturePage clickOnSave() {
		List<WebElement> buttons = this.driver.findElements(By
				.cssSelector("input.formBtn"));
		for (WebElement save : buttons) {
			if (StringUtils
					.equalsIgnoreCase(save.getAttribute("value"), "save")) {
				save.click();
				break;
			}
		}
		this.waitForPageToLoad();
		this.waitForAjax();
		GeneticFeaturePage page = new GeneticFeaturePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}
}
