package com.syngenta.sylk.menu_add.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.syngenta.sylk.libraries.SyngentaException;
import com.syngenta.sylk.main.pages.MenuPage;

public class ImportGeneticFeaturePage extends MenuPage {

	protected ImportGeneticFeaturePage(WebDriver driver) {
		super(driver);
	}

	@FindBy(id = "selectedAccessions")
	private WebElement relatedAccessionNumberSelect;

	@FindBy(css = "input[value='Duplicate Check']")
	private WebElement duplicateCheck;
	@FindBy(css = "input[value='Back']")
	private WebElement back;

	@FindBy(id = "addAsNewGeneticFeature")
	private WebElement addSequenceAsaNewGeneticFeature;

	@FindBy(id = "straightBLAST0")
	private WebElement resultForBlast0;
	@FindBy(id = "straightBLAST1")
	private WebElement resultForBlast1;
	private String HashMap;

	@FindBy(className = "formsItemInputWrapper")
	private WebElement access_number;

	public void CheckMatches() {
		List<WebElement> element = this.resultForBlast0.findElements(By
				.tagName("label"));
		for (WebElement e : element) {
			this.HashMap = e.getText();
		}
		System.out.println("value of Hashmap=" + this.HashMap);
	}

	public List<String> getRelatedAccessionNumbers() {
		List<String> accNums = new ArrayList<String>();
		List<WebElement> options = this.relatedAccessionNumberSelect
				.findElements(By.tagName("option"));
		for (WebElement option : options) {
			accNums.add(option.getText());
		}

		return accNums;
	}

	public void clickAddSequenceAsaNewGeneticFeature() {
		this.addSequenceAsaNewGeneticFeature.click();
	}

	public ImportGeneticFeatureDetailPage clickDuplicate() {
		this.duplicateCheck.click();
		this.waitForPageToLoad();
		ImportGeneticFeatureDetailPage page = new ImportGeneticFeatureDetailPage(
				this.driver);
		if (!page.isThisGeneticFeatureDetailPage()) {
			throw new SyngentaException(
					"Click on duplicate check button on Import genetic feature did not open genetic feature detail page.");
		}

		PageFactory.initElements(this.driver, page);
		return page;
	}
	public void clickBack() {
		this.back.click();
	}

	public NoMatchInSylkPage clickBackAndgoToNoMatchInSylkPage() {
		this.clickBack();
		NoMatchInSylkPage page = new NoMatchInSylkPage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public ImportGeneticFeaturePage clickDuplicateCheckAndGoToAddGeneticFeaturePage() {
		this.duplicateCheck.click();
		ImportGeneticFeaturePage page = new ImportGeneticFeaturePage(
				this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public String getAccessionNumber() {
		return this.access_number.getText();
	}

}
