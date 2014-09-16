package com.syngenta.sylk.menu_add.pages;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.syngenta.sylk.libraries.CommonLibrary;
import com.syngenta.sylk.libraries.SyngentaException;
import com.syngenta.sylk.main.pages.MenuPage;

public class ConstructNominationPage extends MenuPage {

	public ConstructNominationPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(css = "div#cassette table tbody tr:nth-child(2) td:nth-child(2)")
	private WebElement getSequence;

	@FindBy(css = "input[id='deleteConstruct']")
	private WebElement deleteconstruct;

	@FindBy(css = "input[id='saveNomination'][value='Save']")
	private WebElement save;

	@FindBy(id = "rationale")
	private WebElement rationale;

	public void addRationale(String text) {
		this.rationale.sendKeys(text);
		this.waitForPageToLoad();

	}

	public void addVectorFunctionDescription(String string) {
		WebElement textA = this.driver.findElement(By.id("description"));
		textA.sendKeys(string);

	}

	public void checkSBC() {
		WebElement element = this.driver.findElement(By.id("sbcPipeline"));
		element.click();
	}

	public void enterSuggestedGOIName(String string) {
		WebElement element = this.driver.findElement(By
				.name("cassettes[0].goiName"));
		element.sendKeys(string);
	}

	public void selectSequence(String select) {
		WebElement element = this.driver.findElement(By
				.id("cassettes[0].sequenceInVectorType"));
		List<WebElement> elements = element.findElements(By.tagName("option"));
		for (WebElement e : elements) {
			if (e.getText().equalsIgnoreCase(select)) {
				e.click();
				break;
			}
		}
		this.waitForPageToLoad();
		this.waitForAjax();
	}

	public PopUpTargetSpecies clickOnAddTargetSpecies() {
		WebElement button = this.driver.findElement(By
				.cssSelector("div#feedback input.floatright.btn"));
		button.click();
		this.waitForWebElement(By
				.cssSelector("input[id='dialogAdd'][value='Add']"));
		PopUpTargetSpecies page = new PopUpTargetSpecies(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}
	public ConstructNominationPage clickOnSave() {
		this.save.click();
		this.waitForPageToLoad();
		this.waitForAjax();
		String message = null;
		WebElement saveMessage = this.waitForWebElement(By.id("saveMessage"));
		message = saveMessage.getText();

		if (!StringUtils.equalsIgnoreCase(message, "Construct Saved")) {
			throw new SyngentaException(
					"Construct saved message did not appear on click on save with valid values in Construct Nomination page.");
		}

		new CommonLibrary().slowDown();

		ConstructNominationPage page = new ConstructNominationPage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public String getSequenceText() {
		WebElement textA = null;
		String text = null;
		int a = 0;
		CommonLibrary common = new CommonLibrary();
		while (true) {
			try {
				textA = this.driver.findElement(By.id("sequenceClob0"));
				text = textA.getAttribute("value");
			} catch (Exception e) {
			}
			if (a < 20) {
				a++;
			} else {
				break;
			}
			common.slowDown();
		}

		return text;
	}

	public ConstructStatusSearchPage clickDelete() {
		this.deleteconstruct.click();
		WebDriverWait wait = new WebDriverWait(this.driver, 10);
		wait.until(ExpectedConditions.alertIsPresent());
		this.driver.switchTo().alert().accept();
		this.waitForPageToLoad();
		this.waitForAjax();
		ConstructStatusSearchPage page = new ConstructStatusSearchPage(
				this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

}
