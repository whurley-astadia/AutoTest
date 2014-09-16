package com.syngenta.sylk.menu_add.pages;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.syngenta.sylk.main.pages.MenuPage;

public class GeneticFeatureManualEntryPage extends MenuPage {

	protected GeneticFeatureManualEntryPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(id = "sequenceSearchData.gDNASequence")
	private WebElement gDNA;
	@FindBy(id = "sequenceSearchData.cDNASequence")
	private WebElement cDNA;
	@FindBy(id = "sequenceSearchData.cdsSequence")
	private WebElement cDS;
	@FindBy(id = "sequenceSearchData.proteinSequence")
	private WebElement protein;
	@FindBy(id = "checkDuplicateButton")
	private WebElement duplicateCheck;
	@FindBy(id = "cancelButton")
	private WebElement cancel;
	@FindBy(name = "formsItemInputWrapper")
	private WebElement squenceData;
	@FindBy(className = "validationerror")
	private WebElement validationError;

	private String invalidProteinErrorMsg = "Invalid protein sequence data.";

	public void EntergDNASequence(String data) {
		this.gDNA.clear();
		this.gDNA.sendKeys(data);

	}

	public void EntercDNASequence(String data) {
		this.cDNA.clear();
		this.cDNA.sendKeys(data);
	}
	public void EntercDSSequence(String data) {
		this.cDS.clear();
		this.cDS.sendKeys(data);
	}

	public void EnterProteinSequence(String data) {
		this.protein.clear();
		this.protein.sendKeys(data);
	}

	public BLASTSearchResultPage clickDuplicateCheck() {
		this.duplicateCheck.click();
		this.waitForPageToLoad();
		BLASTSearchResultPage page = new BLASTSearchResultPage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public PopUpWarningUnsavedDataPage clickCancel() {
		this.cancel.click();
		this.waitForPopUpToLoad();
		this.waitForAjax();
		PopUpWarningUnsavedDataPage Page = new PopUpWarningUnsavedDataPage(
				this.driver);
		PageFactory.initElements(this.driver, Page);
		return Page;

	}

	public String getGdnaSequence() {
		String gdnaSeq = this.gDNA.getText();
		return gdnaSeq;
	}

	public String getcDNASequence() {
		String cDNASeq = this.cDNA.getText();
		return cDNASeq;
	}

	public String getCDSSequence() {
		String cDSSeq = this.cDS.getText();
		return cDSSeq;
	}

	// gets the test entered in the sequence
	public String getTextInProteinSequence() {
		String text = this.protein.getAttribute("value");
		if (StringUtils.isBlank(text)) {
			text = null;
		}
		return text;
	}

	public String getValidationError() {
		String error = this.validationError.getText();
		return error;
	}

}
