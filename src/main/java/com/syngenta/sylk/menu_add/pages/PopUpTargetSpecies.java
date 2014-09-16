package com.syngenta.sylk.menu_add.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.syngenta.sylk.main.pages.BasePage;

public class PopUpTargetSpecies extends BasePage {

	protected PopUpTargetSpecies(WebDriver driver) {
		super(driver);
	}

	@FindBy(css = "input[id='dialogAdd'][value='Add']")
	private WebElement add;

	@FindBy(css = "input[id='dialogSpecies']")
	private WebElement targetSpeciestestbox;

	private void enterTargetSpecies(String text) {
		this.targetSpeciestestbox.sendKeys(text);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
		Actions action = new Actions(this.driver);
		action.sendKeys(this.targetSpeciestestbox, Keys.ARROW_DOWN).sendKeys(
				Keys.ENTER);
		action.perform();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
	}

	public ConstructNominationPage enterTargetSpeciesClickAdd(String text) {
		this.enterTargetSpecies(text);
		this.add.click();
		this.waitForPageToLoad();
		ConstructNominationPage page = new ConstructNominationPage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}
}
