package com.syngenta.sylk.menu_add.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.syngenta.sylk.main.pages.BasePage;

public class PopUpAddGeneSymbol extends BasePage {

	protected PopUpAddGeneSymbol(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(id = "dialogGeneticFeatureName")
	private WebElement TextBox;

	@FindBy(id = "dialogGeneticFeatureAdd")
	private WebElement add;

	public void enterText(String data) {
		this.TextBox.sendKeys(data);

	}

	public CreateLiteratureEvidenceDetailsForGeneticFeaturePage clickAdd() {
		this.add.click();
		this.waitForPageToLoad();
		CreateLiteratureEvidenceDetailsForGeneticFeaturePage page = new CreateLiteratureEvidenceDetailsForGeneticFeaturePage(
				this.driver);
		PageFactory.initElements(this.driver, page);
		return page;

	}
}
