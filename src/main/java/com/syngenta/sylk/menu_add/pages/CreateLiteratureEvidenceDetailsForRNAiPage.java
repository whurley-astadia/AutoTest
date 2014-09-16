package com.syngenta.sylk.menu_add.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.syngenta.sylk.main.pages.MenuPage;
import com.syngenta.sylk.menu_find.pages.RNAiPage;

public class CreateLiteratureEvidenceDetailsForRNAiPage extends MenuPage {

	protected CreateLiteratureEvidenceDetailsForRNAiPage(WebDriver driver) {
		super(driver);
	}
	@FindBy(css = "textarea[id='rationale'][name='evidence.rationale']")
	private WebElement rationale;

	@FindBy(id = "observation")
	private WebElement observation;

	@FindBy(css = "input[class='floatright btn'][value='Add RNAI']")
	private WebElement addRNAi;

	@FindBy(css = "input[class='floatright btn'][value='Add Trait Component']")
	private WebElement addTraitComponent;

	@FindBy(css = "input[class='floatright btn'][ value='Add Genetic Feature']")
	private WebElement addGeneticFearure;

	@FindBy(css = "select[id='traitComponents[0].selectedChangeDirection'][name='traitComponents[0].selectedChangeDirection']")
	private WebElement changeDirection;

	@FindBy(id = "saveEvidence")
	private WebElement save;

	@FindBy(id = "cancelButton")
	private WebElement cancel;

	@FindBy(id = "dup_error")
	private WebElement errorMassageDuplicate;

	public void enterRationale(String text) {
		this.rationale.sendKeys(text);
	}

	public void enterObservation(String text) {
		this.observation.sendKeys(text);
	}

	public PopUpAddRNAiTrigger clickAddRNAi() {
		this.addRNAi.click();
		this.waitForPopUpToLoad();
		PopUpAddRNAiTrigger page = new PopUpAddRNAiTrigger(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public PopUpAddTraitComponent clickAddTraitComponent() {
		this.waitForWebElement(By
				.cssSelector("input[class='floatright btn'][value='Add Trait Component']"));
		this.addTraitComponent.click();
		this.waitForPopUpToLoad();
		PopUpAddTraitComponent page = new PopUpAddTraitComponent(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;

	}
	public void selectChangeDirection(String selection) {
		List<WebElement> elements = this.changeDirection.findElements(By
				.tagName("options"));
		for (WebElement element : elements) {
			if (element.getText().equalsIgnoreCase(selection)) {
				element.click();
			}

		}

	}

	public RNAiPage clickSave() {
		this.save.click();
		this.waitForPageToLoad();
		this.waitForAjax();
		RNAiPage page = new RNAiPage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public void clickCancel() {
		this.cancel.click();
	}

}
