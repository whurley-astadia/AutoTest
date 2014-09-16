package com.syngenta.sylk.menu_add.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.syngenta.sylk.main.pages.MenuPage;

public class AddPromoterPage extends MenuPage {

	public AddPromoterPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(id = "sequence")
	private WebElement sequence;

	@FindBy(css = "input[value='Duplicate Check']")
	private WebElement duplicateCheck;

	@FindBy(css = "input[value='Clear']")
	private WebElement clear;

	public void enterSequence(String data) {
		this.sequence.sendKeys(data);
	}

	public void clickDuplicateCheck() {
		this.duplicateCheck.click();
	}

	public void clickClear() {
		this.clear.click();
	}

}
