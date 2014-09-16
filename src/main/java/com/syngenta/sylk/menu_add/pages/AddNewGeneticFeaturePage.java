package com.syngenta.sylk.menu_add.pages;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.syngenta.sylk.libraries.PageTitles;
import com.syngenta.sylk.libraries.SyngentaException;
import com.syngenta.sylk.main.pages.BasePage;
import com.syngenta.sylk.main.pages.MenuPage;

public class AddNewGeneticFeaturePage extends MenuPage {

	public AddNewGeneticFeaturePage(WebDriver driver) {
		super(driver);
	}

	@FindBy(id = "geneType")
	private WebElement geneType;

	@FindBy(id = "sequence")
	private WebElement sequence;

	@FindBy(name = "sequenceType")
	private List<WebElement> radios;

	@FindBy(id = "findMatchButton")
	private WebElement findmatches;

	@FindBy(id = "clearButton")
	private WebElement clear;

	@FindBy(className = "validationerror")
	private WebElement validationError;

	@FindBy(css = ".formsFormHd.clearfix")
	private WebElement pageHeader;

	@FindBy(css = "#appIdentity h2")
	private WebElement pageTitle;

	private String ErrorMsgForAccessionNoExceeding32Char = "The accession number can not exceed 32 characters.";

	// selects the type of Gene from the dropdown
	public void selectGeneType(String selection) {
		List<WebElement> elements = this.geneType.findElements(By
				.tagName("option"));
		for (WebElement e : elements) {
			if (e.getText().equalsIgnoreCase(selection)) {
				e.click();
			}
		}
	}

	// clicks the sequance type radio button
	public void clickSequanceType(String selection) {
		for (WebElement radio : this.radios) {
			if (StringUtils.equalsIgnoreCase(radio.getAttribute("value"),
					selection)) {
				radio.click();
			}
		}
	}

	// checks which type of Gene is selected
	public String getSelectedSequanceType() {
		for (WebElement radio : this.radios) {
			if (!StringUtils.equalsIgnoreCase(radio.getAttribute("disabled"),
					"disabled")) {
				if (radio.isSelected()) {
					return radio.getAttribute("value");
				}
			}
		}

		// if none selected.. i.e if loop does not return
		return "none";

	}

	public boolean isSequenceTypeEnabled() {
		return this.radios.get(0).isEnabled();
	}

	// entering sequence for a Gene type
	public void enterTextInSequence(String text) {
		text = StringUtils.trim(text);
		this.sequence.clear();
		// this.sequence.sendKeys(text);

		Actions action = new Actions(this.driver);
		action.sendKeys(this.sequence, text);
		action.perform();
		// .sendKeys(this.sequence, Keys.DOWN)
		// .sendKeys(this.sequence, Keys.SPACE)
		// .sendKeys(this.sequence, Keys.BACK_SPACE)
		this.waitUntilClickable(By.name("sequenceType"), 10000);
	}

	public void waitUntilClickable(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(this.driver, timeout);
		wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	// gets the test entered in the sequence
	public String getTextInSequence() {
		String text = this.sequence.getAttribute("value");
		if (StringUtils.isBlank(text)) {
			text = null;
		}
		return text;
	}

	// navigates to the appropriate page after clicking FindMatch
	public BasePage clickFindMatches() {
		BasePage page = null;
		this.findmatches.click();
		this.waitForPageToLoad();
		String title = StringUtils.substringBefore(this.getPageTitle(), ",");

		// if duplicate search result page appears
		if (StringUtils.equalsIgnoreCase(title,
				PageTitles.duplicate_search_result_page_title)) {
			page = new DuplicateSearchResultPage(this.driver);

		}
		// if Genetic feature Page appears
		else if (StringUtils.equalsIgnoreCase(title,
				PageTitles.genetic_feature_page_title)) {
			page = new GeneticFeaturePage(this.driver);

			// if Genetic Feature Manual Entry page (duplicate check) appears
		} else if (StringUtils.equalsIgnoreCase(title,
				PageTitles.genetic_Feature_Manual_Entry_page_title)) {
			page = new GeneticFeatureManualEntryPage(this.driver);
		} else if (StringUtils.equalsIgnoreCase(title,
				PageTitles.add_new_genetic_feature_page_title)) {
			page = new GeneticFeatureManualEntryPage(this.driver);

		} else {
			throw new SyngentaException(
					"Click on Find Match did not open up a valid page. The page taht opened up was '"
							+ this.getPageTitle() + "'");
		}
		PageFactory.initElements(this.driver, page);
		return page;
	}

	// clears the fields
	public void clickClear() {
		this.clear.click();
	}

	// checks for the validation error
	public String getValidationerror() {
		String error = null;
		try {
			this.validationError = this.driver.findElement(By
					.className("validationerror"));
			error = this.validationError.getText();
		} catch (Exception e) {

		}

		return error;
	}

	// gets the page header
	public String getPageHeader() {
		return this.driver.findElement(
				By.cssSelector(".formsFormHd.clearfix h3")).getText();
	}

}
