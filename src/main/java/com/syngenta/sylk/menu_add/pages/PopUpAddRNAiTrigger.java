package com.syngenta.sylk.menu_add.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.syngenta.sylk.main.pages.BasePage;

public class PopUpAddRNAiTrigger extends BasePage {

	protected PopUpAddRNAiTrigger(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(id = "dialogRNAIName")
	private WebElement TextBox;

	@FindBy(id = "dialogRNAIAdd")
	private WebElement add;

	public void enterText(String data) {
		this.TextBox.sendKeys(data);
		try {
			Thread.sleep(800);
		} catch (InterruptedException e) {
		}
		Actions action = new Actions(this.driver);
		action.sendKeys(this.TextBox, Keys.ARROW_DOWN).sendKeys(Keys.ENTER);
		action.perform();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
	}

	public CreateLiteratureEvidenceDetailsForRNAiPage clickAdd() {
		try {
			this.add.click();
			this.waitForPageToLoad();
		} catch (Exception e) {
			/*
			 * Do nothing
			 * 
			 * Because this method 'clickAdd' is called after enterText method
			 * enterText method has a logic to press down arrow key and make the
			 * selection and hit enter. If that happens successfully the 'Enter'
			 * key cause the trait popup to close In that case click on add will
			 * result in exception.
			 * 
			 * We are ignoring any exception that is caused due to teh above
			 * scenario.
			 */
		}
		CreateLiteratureEvidenceDetailsForRNAiPage page = new CreateLiteratureEvidenceDetailsForRNAiPage(
				this.driver);
		PageFactory.initElements(this.driver, page);
		return page;

	}
}
