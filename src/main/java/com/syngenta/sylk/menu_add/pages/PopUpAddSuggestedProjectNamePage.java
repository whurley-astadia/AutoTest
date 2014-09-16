package com.syngenta.sylk.menu_add.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.syngenta.sylk.main.pages.BasePage;

public class PopUpAddSuggestedProjectNamePage extends BasePage {

	protected PopUpAddSuggestedProjectNamePage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(id = "project.projectName")
	private WebElement suggestedProjectName;

	@FindBy(css = "div.ui-dialog-buttonset button")
	private WebElement save;

	public void enterSuggestedProjectName(String text) {
		this.suggestedProjectName.sendKeys(text);

	}

	public LeadNominationPage clickAdd() {
		this.save.click();
		LeadNominationPage page = new LeadNominationPage(this.driver);
		PageFactory.initElements(this.driver, page);
		this.waitForPageToLoad();
		return page;
	}

}
