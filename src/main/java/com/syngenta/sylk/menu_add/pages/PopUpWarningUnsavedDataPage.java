package com.syngenta.sylk.menu_add.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.syngenta.sylk.main.pages.BasePage;

public class PopUpWarningUnsavedDataPage extends BasePage {

	protected PopUpWarningUnsavedDataPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(css = "div.ui-dialog-buttonset button[text='Continue Without Saving']")
	private WebElement continueWithoutSaving;
	@FindBy(css = "button[text='Cancel'][value='Cancel']")
	private WebElement cancel;

	public AddNewGeneticFeaturePage clickContinueWithoutSavingAndGoToAddNewGFPage() {
		this.continueWithoutSaving.click();
		AddNewGeneticFeaturePage page = new AddNewGeneticFeaturePage(
				this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public BLASTSearchResultPage clickContinueWithoutSavingAndGoToBLASTsearchPage() {
		this.continueWithoutSaving.click();
		this.waitForPageToLoad();
		BLASTSearchResultPage page = new BLASTSearchResultPage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}
	public GeneticFeatureManualEntryPage clickCancel() {
		this.cancel.click();
		GeneticFeatureManualEntryPage page = new GeneticFeatureManualEntryPage(
				this.driver);
		PageFactory.initElements(this.driver, page);
		return page;

	}

}
