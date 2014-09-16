package com.syngenta.sylk.menu_add.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.syngenta.sylk.libraries.CommonLibrary;
import com.syngenta.sylk.main.pages.MenuPage;

public class LiteratureDetailPage extends MenuPage {

	protected LiteratureDetailPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(css = "input[value='Associate'][name='submit']")
	private WebElement associate;

	@FindBy(css = "input[ value='Back'][name='submit']")
	private WebElement back;

	public RelatedLiteraturePage clickBack() {
		this.back.click();
		this.waitForPageToLoad();
		RelatedLiteraturePage page = new RelatedLiteraturePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;

	}

	public CreateLiteratureEvidenceDetailsForGeneticFeaturePage clickAssociate() {
		this.associate.click();
		this.waitForPageToLoad();
		this.waitForAjax();
		new CommonLibrary().slowDown();
		this.waitForWebElement(By.cssSelector("textarea#rationale"));
		CreateLiteratureEvidenceDetailsForGeneticFeaturePage page = new CreateLiteratureEvidenceDetailsForGeneticFeaturePage(
				this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

}
