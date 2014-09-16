package com.syngenta.sylk.menu_add.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.syngenta.sylk.main.pages.MenuPage;

public class NoMatchInSylkPage extends MenuPage {

	protected NoMatchInSylkPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(css = "input[value='Modify Search']")
	private WebElement modifySearch;

	@FindBy(css = "input[value='Search External Databases']")
	private WebElement searchExternalDatatbases;

	public void clickModifySearch() {
		this.modifySearch.click();
	}

	public void clikSearchExternalDatabases() {
		this.searchExternalDatatbases.click();
	}

	public ImportGeneticFeaturePage goToImportGeneticFeaturePage() {
		ImportGeneticFeaturePage page = new ImportGeneticFeaturePage(
				this.driver);
		this.clikSearchExternalDatabases();
		PageFactory.initElements(this.driver, page);
		return page;

	}

	public AddNewGeneticFeaturePage goToAddNewGeneticFeaturePage() {
		AddNewGeneticFeaturePage page = new AddNewGeneticFeaturePage(
				this.driver);
		this.clickModifySearch();
		PageFactory.initElements(this.driver, page);
		return page;

	}

	public NoMatchInExternalDataBasePage goToNoMatchInExternalDataBasePage() {
		this.clikSearchExternalDatabases();
		NoMatchInExternalDataBasePage page = new NoMatchInExternalDataBasePage(
				this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

}
