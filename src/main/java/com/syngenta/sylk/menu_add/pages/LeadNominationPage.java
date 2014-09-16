package com.syngenta.sylk.menu_add.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.syngenta.sylk.main.pages.MenuPage;

public class LeadNominationPage extends MenuPage {
	public LeadNominationPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(id = "addNewTraitComponentButton")
	private WebElement addProjectName;

	@FindBy(id = "leadNominationDetails.lead.leadName")
	private WebElement leadName;

	@FindBy(id = "leadNominationDetails.lead.leadSourceCode")
	private WebElement leadSource;

	@FindBy(id = "countryCode")
	private WebElement countryOfOrigin;

	@FindBy(id = "leadNominationDetails.lead.rationaleForInclusion")
	private WebElement rationaleForInclusion;

	@FindBy(id = "leadNominationDetails.lead.leadTypeCode")
	private WebElement leadType;

	@FindBy(id = "leadNominationDetails.lead.leadFunctions")
	private WebElement leadFunctions;

	public PopUpAddSuggestedProjectNamePage clickOnAddSuggestedProjectName() {
		this.addProjectName.click();
		PopUpAddSuggestedProjectNamePage page = new PopUpAddSuggestedProjectNamePage(
				this.driver);
		PageFactory.initElements(this.driver, page);
		this.waitForPageToLoad();
		return page;
	}

	public void selectLeadSource(String string) {
		WebElement select = this.driver.findElement(By
				.id("leadNominationDetails.lead.leadSourceCode"));
		List<WebElement> elements = select.findElements(By.tagName("option"));
		for (WebElement e : elements) {
			if (e.getText().equalsIgnoreCase(string)) {
				e.click();
			}
		}
	}

	public void selectLeadType(String string) {
		WebElement select = this.driver.findElement(By
				.id("leadNominationDetails.lead.leadTypeCode"));
		List<WebElement> elements = select.findElements(By.tagName("option"));
		for (WebElement e : elements) {
			if (e.getText().equalsIgnoreCase(string)) {
				e.click();
			}
		}
	}

	public void enterLeadFunction(String string) {
		WebElement element = this.driver.findElement(By
				.id("leadNominationDetails.lead.leadFunctions"));
		element.sendKeys(string);
	}

	public PopUpAddSourceOfLeadFunctionInfo clickOnAddLeadFunctionInfo() {
		WebElement button = this.driver
				.findElement(By
						.cssSelector("input[value='Add Source Of Lead Function Info']"));
		button.click();
		this.waitForPopUpToLoad();
		this.waitForAjax();
		PopUpAddSourceOfLeadFunctionInfo page = new PopUpAddSourceOfLeadFunctionInfo(
				this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public GeneticFeaturePage clickOnAddLeadNomination() {

		WebElement button = this.driver.findElement(By
				.cssSelector("input[value='Add Lead Nomination']"));
		button.click();
		this.waitForPageToLoad();
		this.waitForAjax();
		GeneticFeaturePage page = new GeneticFeaturePage(this.driver);
		PageFactory.initElements(this.driver, page);

		return page;
	}

	public void enterRationale(String string) {
		WebElement testA = this.driver.findElement(By
				.id("leadNominationDetails.lead.rationaleForInclusion"));
		testA.sendKeys(string);
	}
}
