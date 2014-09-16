package com.syngenta.sylk.menu_add.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.syngenta.sylk.main.pages.MenuPage;

public class AddNewROIPage extends MenuPage {

	public AddNewROIPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(css = "input[id='findOrganismDiv_findButton']")
	private WebElement find;

	@FindBy(css = "input[id = 'findOrganismDiv_autoComplete']")
	private WebElement selectInformation;

	@FindBy(id = "findOrganismDiv_findButton")
	private WebElement sourceSpeciesFind;

	@FindBy(id = "findOrganismDiv_autoComplete")
	private WebElement selectInformationPopUpEnterData;

	@FindBy(css = "input[id = 'line']")
	private WebElement line;

	@FindBy(css = "select[id = 'refType'][name='referenceGenomeCode']")
	private WebElement ReferenceGenome;

	@FindBy(css = "input[id = 'chromosome']")
	private WebElement chromosome;

	@FindBy(css = "input[id = 'from']")
	private WebElement from;

	@FindBy(css = "input[id = 'to']")
	private WebElement to;

	@FindBy(id = "synonyms")
	private WebElement synonyms;

	@FindBy(css = "select[id ='type'][name='typeCode']")
	private WebElement RegionOfInteresttype;

	@FindBy(id = "descriptions")
	private WebElement descriptions;

	@FindBy(css = "input[id ='snps']")
	private WebElement SNPs;

	@FindBy(css = "input[id = 'markers']")
	private WebElement markers;

	@FindBy(css = "input[class='btn'][value='Add Genes to ROI']")
	private WebElement addGeneToROI;

	@FindBy(css = "input[class='btn'][value='Add Region Of Interest']")
	private WebElement addRegionOfInterest;

	@FindBy(css = "input[class='btn'][value='Clear']")
	private WebElement clear;

	// error messages
	@FindBy(css = "label[id = 'organism_error']")
	private WebElement sourceSpeciesError;

	@FindBy(css = "label[id = 'line_error']")
	private WebElement lineError;

	@FindBy(css = "label[id = 'genome_error']")
	private WebElement referenceGenomeError;

	@FindBy(css = "label[id = 'chromosome_error']")
	private WebElement chromosomeError;

	@FindBy(css = "label[id ='from_error']")
	private WebElement fromError;

	@FindBy(css = "label[id = 'to_error']")
	private WebElement toError;

	@FindBy(css = "label[id ='snps_error']")
	private WebElement SNPsError;

	@FindBy(css = "label[id='num_error']")
	private WebElement numericError;

	public void enterSourceSpecies(String data) {
		this.sourceSpeciesFind.click();
		this.waitForPageToLoad();
		this.selectInformationPopUpEnterData.sendKeys(data);
		WebElement ok = this.driver
				.findElement(By
						.cssSelector("button.ui-button.ui-widget.ui-state-default.ui-corner-all.ui-button-text-only"));
		ok.click();

	}

	public void enterLine(String data) {
		this.line.sendKeys(data);

	}

	public void selectReferenceGenome(String selection) {
		List<WebElement> elements = this.ReferenceGenome.findElements(By
				.tagName("option"));
		for (WebElement e : elements) {
			if (e.getText().equalsIgnoreCase(selection)) {
				e.click();
			}
		}
	}

	public void enterChromosome(String data) {
		this.chromosome.sendKeys(data);

	}

	public void enterFrom(String data) {
		this.from.sendKeys(data);
	}

	public void enterTo(String data) {
		this.to.sendKeys(data);
	}

	public void enterSynonyms(String data) {
		this.synonyms.sendKeys(data);
	}

	public void selectRegionOfInterestType(String selection) {
		List<WebElement> elements = this.RegionOfInteresttype.findElements(By
				.tagName("option"));
		for (WebElement e : elements) {
			if (e.getText().equalsIgnoreCase(selection)) {
				e.click();
			}
		}
	}

	public void enterDescription(String data) {
		this.descriptions.sendKeys(data);
	}

	public void enterSNPs(String data) {
		this.SNPs.sendKeys(data);
	}

	public void enterMarkers(String data) {
		this.markers.sendKeys(data);
	}

	public SearchTargetGenePage clickAddGeneToROI() {
		this.addGeneToROI.click();
		this.waitForPageToLoad();
		this.waitForAjax();
		SearchTargetGenePage page = new SearchTargetGenePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public ROIDetailPage ClickAddRegionOfInterest() {
		this.addRegionOfInterest.click();
		this.waitForPageToLoad();
		ROIDetailPage page = new ROIDetailPage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;

	}

	public void clickClear() {
		this.clear.click();
	}

}
