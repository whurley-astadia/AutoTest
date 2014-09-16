package com.syngenta.sylk.menu_add.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.syngenta.sylk.main.pages.MenuPage;

public class NoMatchInExternalDataBasePage extends MenuPage {

	protected NoMatchInExternalDataBasePage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(name = "modifySearch")
	private WebElement modifySearch;
	@FindBy(css = "input[value='Manual Entry']")
	private WebElement manualEntry;

	public void clickModifySearch() {
		this.modifySearch.click();
	}

	public void clickManualEntry() {
		this.manualEntry.click();

	}

	public AddNewGeneticFeaturePage goToAddNewGeneticFeaturePage() {
		this.clickModifySearch();
		AddNewGeneticFeaturePage page = new AddNewGeneticFeaturePage(
				this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public GeneticFeatureManualEntryPage goToDuplicateCheckPage() {
		this.clickManualEntry();
		GeneticFeatureManualEntryPage page = new GeneticFeatureManualEntryPage(
				this.driver);
		PageFactory.initElements(this.driver, page);
		return page;

	}

}
