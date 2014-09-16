package com.syngenta.sylk.menu_add.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.syngenta.sylk.main.pages.MenuPage;
import com.syngenta.sylk.menu_find.pages.RNAiPage;

public class ViewLiteratureEvidenceDetailsRNAI extends MenuPage {

	public ViewLiteratureEvidenceDetailsRNAI(WebDriver driver) {
		super(driver);
	}

	@FindBy(id = "backButton")
	private WebElement back;

	@FindBy(id = "editEvidence")
	private WebElement edit;

	@FindBy(id = "deleteEvidence")
	private WebElement delete;

	public RNAiPage clickBack() {
		this.back.click();
		this.waitForPageToLoad();
		RNAiPage page = new RNAiPage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;

	}

	public RNAiPage clickDeleteToDeleteThisLiteratureEvidence() {
		this.delete.click();
		WebDriverWait wait = new WebDriverWait(this.driver, 10);
		wait.until(ExpectedConditions.alertIsPresent());
		this.driver.switchTo().alert().accept();
		this.waitForPageToLoad();
		this.waitForAjax();
		RNAiPage page = new RNAiPage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

}
