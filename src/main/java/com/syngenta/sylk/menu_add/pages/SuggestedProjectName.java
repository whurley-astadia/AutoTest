package com.syngenta.sylk.menu_add.pages;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.syngenta.sylk.main.pages.BasePage;

public class SuggestedProjectName extends BasePage {

	@FindBy(id = "project.projectName")
	private WebElement projectName;

	public SuggestedProjectName(WebDriver driver) {
		super(driver);
	}

	private void enterProjectName(String name) {
		this.projectName.sendKeys(name);
		Actions action = new Actions(this.driver);
		action.sendKeys(this.projectName, Keys.SPACE).sendKeys(
				this.projectName, Keys.BACK_SPACE);
		action.perform();

	}

	public void waitUntilClickable(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(this.driver, timeout);
		wait.until(ExpectedConditions.elementToBeClickable(locator));
	}
	private void clickAdd() {
		List<WebElement> buttons = this.driver.findElements(By
				.cssSelector("div#addSuggestedProjectNameDiv button"));
		for (WebElement addBtn : buttons) {
			if (StringUtils.equalsIgnoreCase(addBtn.getAttribute("value"),
					"Add")) {
				addBtn.click();
				break;
			}
		}
	}

	public LeadNominationPage addProjectName(String name) {
		this.enterProjectName(name);
		this.clickAdd();
		this.waitForPageToLoad();
		LeadNominationPage page = new LeadNominationPage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}
}
