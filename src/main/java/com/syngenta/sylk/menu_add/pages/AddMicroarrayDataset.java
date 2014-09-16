package com.syngenta.sylk.menu_add.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.syngenta.sylk.main.pages.MenuPage;

public class AddMicroarrayDataset extends MenuPage {

	public AddMicroarrayDataset(WebDriver driver) {
		super(driver);
	}

	@FindBy(id = "projectId")
	private WebElement ProjectID;

	@FindBy(id = "biotrackerId")
	private WebElement biotrackerID;

	@FindBy(id = "proposalName")
	private WebElement proposalName;

	@FindBy(id = "experimentName")
	private WebElement datasetName;

	@FindBy(id = "reportLink")
	private WebElement reportLink;

	@FindBy(id = "genevestigatorId")
	private WebElement genevestigatorID;

	@FindBy(id = "chipId")
	private WebElement chipID;

	@FindBy(id = "cdfLink")
	private WebElement cDFFileLink;

	@FindBy(id = "uploadParametersFile")
	private WebElement experimentalParameters;

	@FindBy(id = "uploadFileButton")
	private WebElement upLoadParametersFiles;

	@FindBy(id = "addMicroarrayGene")
	private WebElement addToSyLK;

	@FindBy(id = "cancel")
	private WebElement cancel;

	@FindBy(id = "instructionButton")
	private WebElement linkDatasetDesWorksheet;

	// error masssage
	@FindBy(className = "validationerror")
	private WebElement errorMassage;

	private String errorMsg = "Select a parameter file first.";

	// enter the Project ID
	public void enterProjectID(String data) {
		this.ProjectID.sendKeys(data);
	}

	// enter biotracker
	public void enterbiotracker(String data) {
		this.biotrackerID.sendKeys(data);
	}

	// enter proposal name
	public void enterProposalName(String data) {
		this.proposalName.sendKeys(data);
	}

	// enter Dataset name
	public void enterDatasetName(String data) {
		this.datasetName.sendKeys(data);
	}

	// enter Report link
	public void enterReportLink(String data) {
		this.reportLink.sendKeys(data);
	}

	// enter genevestigatgor ID
	public void enterGenevestigatorID(String data) {
		this.genevestigatorID.sendKeys(data);
	}

	// enter chip ID
	public void enterChipID(String data) {
		this.chipID.sendKeys(data);
	}

	// enter CDF File link
	public void enterCDFfileLink(String data) {
		this.cDFFileLink.sendKeys(data);
	}

	// click the browse button for experimental parameters
	public void enterExperimentalParameters(String data) {
		// incomplete has to write a method to browse.
	}

	// click the up load parameters button
	public void clickUpLoadParametersFiles() {
		this.upLoadParametersFiles.click();
	}

	// click add to Sylk button
	public void clickAddToSylk() {
		this.addToSyLK.click();
	}

	// click cancel button
	public void clickCancel() {
		this.cancel.click();
	}

	// click the link for Dataset description worksheet
	public void clicklinkDatasetDescriptionWorksheet() {
		this.linkDatasetDesWorksheet.click();
	}

}