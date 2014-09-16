package com.syngenta.sylk.menu_add.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.syngenta.sylk.main.pages.MenuPage;

public class PopUpAddSequenceAccession extends MenuPage {

	@FindBy(id = "dialogSequenceName")
	private WebElement textBox;

	@FindBy(css = "input[id='dialogSequenceAdd']")
	private WebElement addButton;

	protected PopUpAddSequenceAccession(WebDriver driver) {
		super(driver);
	}

	private void enterSequenceName(String text) {
		this.textBox.sendKeys(text);
		try {
			Thread.sleep(800);
		} catch (InterruptedException e) {
		}
		Actions action = new Actions(this.driver);
		action.sendKeys(this.textBox, Keys.ARROW_DOWN).sendKeys(Keys.ENTER);
		action.perform();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
	}

	private void clickOnAdd() {
		this.addButton.click();
	}

	public CreateLiteratureEvidenceDetailsForGeneticFeaturePage addSequenceName(
			String name) {
		this.enterSequenceName(name);
		this.clickOnAdd();
		this.waitForPageToLoad();
		this.waitForAjax();
		CreateLiteratureEvidenceDetailsForGeneticFeaturePage page = new CreateLiteratureEvidenceDetailsForGeneticFeaturePage(
				this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}
}
