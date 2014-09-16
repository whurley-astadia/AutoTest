package com.syngenta.sylk.menu_add.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.syngenta.sylk.main.pages.MenuPage;

public class ViewLiteratureEvidenceDetailsGF extends MenuPage {

	protected ViewLiteratureEvidenceDetailsGF(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(id = "backButton")
	private WebElement back;

	@FindBy(id = "editEvidence")
	private WebElement edit;

	@FindBy(id = "deleteEvidence")
	private WebElement delete;

	public GeneticFeaturePage clickBack() {
		this.back.click();
		this.waitForPageToLoad();
		GeneticFeaturePage page = new GeneticFeaturePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;

	}

	public GeneticFeaturePage clickDeleteToDeleteThisLiteratureEvidence() {
		this.delete.click();
		WebDriverWait wait = new WebDriverWait(this.driver, 10);
		wait.until(ExpectedConditions.alertIsPresent());
		this.driver.switchTo().alert().accept();
		this.waitForPageToLoad();
		this.waitForAjax();
		GeneticFeaturePage page = new GeneticFeaturePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

}
