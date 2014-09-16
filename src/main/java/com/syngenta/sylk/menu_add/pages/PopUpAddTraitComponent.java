package com.syngenta.sylk.menu_add.pages;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.syngenta.sylk.libraries.PageTitles;
import com.syngenta.sylk.main.pages.BasePage;

public class PopUpAddTraitComponent extends BasePage {

	protected PopUpAddTraitComponent(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(id = "dialogTCName")
	private WebElement traittext;

	@FindBy(css = "input[id='dialogTCAdd']")
	private WebElement add;

	private void enterText(String text) {
		this.traittext.sendKeys(text);
		try {
			Thread.sleep(800);
		} catch (InterruptedException e) {
		}
		Actions action = new Actions(this.driver);
		action.sendKeys(this.traittext, Keys.ARROW_DOWN).sendKeys(Keys.ENTER);
		action.perform();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
	}

	private void clickOnAdd() {
		this.add.click();
	}
	public BasePage addTrait(String text) {
		this.enterText(text);
		this.clickOnAdd();
		this.waitForPageToLoad();

		String title = this.getPageTitle();
		if (StringUtils
				.equalsIgnoreCase(
						title,
						PageTitles.Create_Literature_Evidence_Details_for_GF_page_title)) {
			CreateLiteratureEvidenceDetailsForGeneticFeaturePage page = new CreateLiteratureEvidenceDetailsForGeneticFeaturePage(
					this.driver);
			PageFactory.initElements(this.driver, page);
			return page;
		} else if (StringUtils
				.equalsIgnoreCase(
						title,
						PageTitles.Create_Literature_Evidence_Details_for_Sequence_page_title)) {
			CreateLiteratureEvidenceDetailsForGeneticFeaturePage page = new CreateLiteratureEvidenceDetailsForGeneticFeaturePage(
					this.driver);
			PageFactory.initElements(this.driver, page);
			return page;
		} else if (StringUtils
				.equalsIgnoreCase(
						title,
						PageTitles.Create_Literature_Evidence_Details_for_RNAi_page_title)) {
			CreateLiteratureEvidenceDetailsForRNAiPage page = new CreateLiteratureEvidenceDetailsForRNAiPage(
					this.driver);
			PageFactory.initElements(this.driver, page);
			return page;
		}

		return null;
	}

}
