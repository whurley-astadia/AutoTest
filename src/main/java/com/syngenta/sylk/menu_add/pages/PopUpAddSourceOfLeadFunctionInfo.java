package com.syngenta.sylk.menu_add.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

import com.syngenta.sylk.main.pages.BasePage;

public class PopUpAddSourceOfLeadFunctionInfo extends BasePage {

	protected PopUpAddSourceOfLeadFunctionInfo(WebDriver driver) {
		super(driver);
	}

	private void selectLeadFunctionInformationAndClickAdd(String string) {
		WebElement select = this.driver.findElement(By
				.id("leadFunctionSource.leadFunctionSourceCode"));
		select.sendKeys(string);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
		Actions action = new Actions(this.driver);
		action.sendKeys(select, Keys.ARROW_DOWN).sendKeys(Keys.ENTER);
		action.perform();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
	}

	public LeadNominationPage addLeadFunctionInformationAndClickAdd(
			String string) {
		this.selectLeadFunctionInformationAndClickAdd(string);
		this.waitForPageToLoad();
		LeadNominationPage page = new LeadNominationPage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}
}
