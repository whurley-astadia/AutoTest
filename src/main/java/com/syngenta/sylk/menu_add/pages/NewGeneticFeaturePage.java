package com.syngenta.sylk.menu_add.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.syngenta.sylk.main.pages.MenuPage;

public class NewGeneticFeaturePage extends MenuPage {

	protected NewGeneticFeaturePage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	// Nucleotide gDNA
	@FindBy(id = "associatedSequence.GDNA.source")
	private WebElement ngDNASource;
	@FindBy(id = "associatedSequence.GDNA.accessionNumber")
	private WebElement ngDNAAccessionNo;
	@FindBy(id = "associatedSequence.GDNA.giNumber")
	private WebElement ngDNAGiNo;
	@FindBy(id = "associatedSequence.GDNA.sequenceCLob")
	private WebElement ngDNASequence;

	// Nucleotide cDNA
	@FindBy(id = "associatedSequence.CDNA.source")
	private WebElement ncDNASource;
	@FindBy(id = "associatedSequence.CDNA.accessionNumber")
	private WebElement ncDNAaccessionNo;
	@FindBy(id = "associatedSequence.CDNA.giNumber")
	private WebElement ncDNAgiNo;
	@FindBy(id = "associatedSequence.CDNA.sequenceCLob")
	private WebElement ncDNASequence;

	// Protein
	@FindBy(id = "associatedSequence.protein.source")
	private WebElement ProteinSource;
	@FindBy(id = "associatedSequence.protein.accessionNumber")
	private WebElement ProteinAccessionNo;
	@FindBy(id = "associatedSequence.protein.giNumber")
	private WebElement ProteinGiNo;
	@FindBy(id = "associatedSequence.protein.sequenceCLob")
	private WebElement ProteinSequence;
	@FindBy(id = "associatedSequence.cds.source")
	private WebElement nucleotideCDSsource;
	@FindBy(id = "associatedSequence.cds.accessionNumber")
	private WebElement nucleotideCDSaccessionNo;
	@FindBy(id = "associatedSequence.cds.giNumber")
	private WebElement nucleotideCDSgiNo;
	@FindBy(id = "associatedSequence.cds.sequenceCLob")
	private WebElement nucleotideCDSSequence;

	// Nucleotide CDS :
	@FindBy(id = "associatedSequence.cds.source")
	private WebElement nCDSAsource;
	@FindBy(id = "associatedSequence.cds.accessionNumber")
	private WebElement nCDSAccessionNo;
	@FindBy(id = "associatedSequence.cds.giNumber")
	private WebElement nCDAgiNo;
	@FindBy(id = "associatedSequence.cds.sequenceCLob")
	private WebElement nCDSsequence;

	// Identification:
	@FindBy(id = "locus.identification.locusTag")
	private WebElement name;
	@FindBy(id = "locus.identification.symbol")
	private WebElement symbol;
	@FindBy(id = "locus.identification.synonyms")
	private WebElement synonyms;
	@FindBy(id = "locus.identification.description")
	private WebElement description;

	// Taxonomy:
	@FindBy(id = "findOrganismDiv_findButton")
	private WebElement sourceSpeciesFind;
	@FindBy(id = "findOrganismDiv_autoComplete")
	private WebElement popUpSelectInformationText;
	@FindBy(css = "input[value='Ok']")
	private WebElement popUpSelectInformationOk;
	@FindBy(id = "locus.taxonomy.taxonId")
	private WebElement taxonId;
	@FindBy(id = "locus.taxonomy.syngentaTaxonId")
	private WebElement syngentaTasonId;
	@FindBy(id = "locus.taxonomy.variety")
	private WebElement variety;
	@FindBy(id = "locus.taxonomy.line")
	private WebElement line;
	@FindBy(id = "locus.taxonomy.ecotype")
	private WebElement ecotpye;
	@FindBy(id = "locus.identification.chromosome")
	private WebElement chromosome;

	// select information
	@FindBy(id = "findOrganismDiv_autoComplete")
	private WebElement selectInformationPopUpEnterData;

	// Genomic Detail:
	@FindBy(id = "orientationValues.selectedItem")
	private WebElement orientation;
	@FindBy(id = "sourceSequence.fromLocation")
	private WebElement fromGD;
	@FindBy(id = "sourceSequence.toLocation")
	private WebElement toGD;
	@FindBy(id = "sourceSequence.sequenceMapLocation")
	private WebElement mapLocation;
	@FindBy(id = "sourceSequence.preferred")
	private WebElement preferred;

	// Genomic Sequence
	@FindBy(id = "sourceSequence.GDNA.source")
	private WebElement source;
	@FindBy(id = "sourceSequence.GDNA.accessionNumber")
	private WebElement accessionNo;

	// New Sequence
	@FindBy(id = "associatedSequence.orientation")
	private WebElement orientationNS;
	@FindBy(id = "associatedSequence.fromLocation")
	private WebElement fromNS;
	@FindBy(id = "associatedSequence.toLocation")
	private WebElement toNS;
	@FindBy(id = "associatedSequence.sequenceMapLocation")
	private WebElement mapLocationNS;
	@FindBy(id = "associatedSequence.preferred")
	private WebElement preferredNS;
	@FindBy(id = "associatedSequence.sequenceVariationTypeCode")
	private WebElement varificationType;

	// buttons:
	@FindBy(id = "saveNewGeneticFeature")
	private WebElement addGeneticFeature;
	@FindBy(id = "cancelNewGeneticFeature")
	private WebElement cancel;

	// validation error:
	@FindBy(className = "validationerror")
	private WebElement validationError;

	private String errorMsgForMandatoryField = "This field is required.";
	private String errorMSgForNumericValue = "Please enter a numeric value.";
	private String errorMsgForAcsnNoAlreadyInSylk = "The following accessions are already in SyLK:";

	public PopUpWarningUnsavedDataPage clickCancel() {
		this.cancel.click();
		this.waitForPageToLoad();
		this.waitForAjax();
		PopUpWarningUnsavedDataPage page = new PopUpWarningUnsavedDataPage(
				this.driver);
		PageFactory.initElements(this.driver, page);
		return page;

	}

	// Identification:

	public void enterNameId(String data) {
		this.name.sendKeys(data);
	}

	public void enterSymbolId(String data) {
		this.symbol.sendKeys(data);
	}

	public void enterSynonymsId(String data) {
		this.synonyms.sendKeys(data);
	}

	public void enterDescriptionId(String data) {
		this.description.sendKeys(data);
	}

	// Taxonomy:

	public void enterTaxonIDTaxonomy(String data) {
		// TaxonID field type has to be checked, need to check if its a auto
		// generated field.
	}

	public void enterSyngentaTaxonIDTaxonomy(String data) {
		this.syngentaTasonId.sendKeys(data);
	}

	public void enterSourceSpeciesTaxonomy(String data) {
		this.sourceSpeciesFind.click();
		this.waitForPageToLoad();
		this.selectInformationPopUpEnterData.sendKeys(data);
		WebElement ok = this.driver
				.findElement(By
						.cssSelector("button.ui-button.ui-widget.ui-state-default.ui-corner-all.ui-button-text-only"));
		ok.click();

	}

	public String getTextFromSourceSpeciesTaxonomy() {
		WebElement sequenceInput = this.driver.findElement(By
				.id("findOrganismDiv_field"));
		String text = sequenceInput.getAttribute("value");
		return text;
	}

	public void enterVarietyTaxonomy(String data) {
		this.variety.sendKeys(data);
	}

	public void enterLineTaxonomy(String data) {
		this.line.sendKeys(data);
	}

	public void enterEcotypeTaxonomy(String data) {
		this.ecotpye.sendKeys(data);
	}

	public void enterChromosomeTaxonomy(String data) {
		this.chromosome.sendKeys(data);
	}

	// Genomic Detail:

	public void selectOrientationGD(String selection) {
		List<WebElement> elements = this.orientation.findElements(By
				.tagName("option"));
		for (WebElement e : elements) {
			if (e.getText().equalsIgnoreCase(selection)) {
				e.click();
			}
		}

	}

	public void enterFromGD(String data) {
		this.fromGD.sendKeys(data);

	}

	public void enterToGD(String data) {
		this.toGD.sendKeys(data);
	}

	public void enterMapLocationGD(String data) {
		this.mapLocationNS.sendKeys(data);
	}

	public void checkPreferredGD() {
		this.preferred.click();
	}

	public String checkTheValueInPreferredCheckBox() {
		WebElement value = this.driver.findElement(By
				.id("sourceSequence.preferred"));
		String text = value.getAttribute("value");
		return text;
	}

	// Genomic Sequence:

	public void enterSourceGS(String data) {

		this.source.sendKeys(data);
	}

	public void enterAccessionNoGS(String data) {
		this.accessionNo.sendKeys(data);

	}

	// Associated Sequences:
	// New Sequence:

	public void selectOrientationNS(String slection) {
		List<WebElement> elements = this.orientationNS.findElements(By
				.tagName("option"));
		for (WebElement e : elements) {
			if (e.getText().equalsIgnoreCase(slection)) {
				e.click();
			}

		}
	}

	public void enterFromNS(String data) {
		this.fromNS.sendKeys(data);

	}

	public void enterToNS(String data) {
		this.toNS.sendKeys(data);
	}

	public void enterMapLocationNS(String data) {
		this.mapLocationNS.sendKeys(data);
	}

	public void checkPreferredNS() {
		this.preferredNS.click();
	}

	public boolean isPreferredNSCheckboxEnabled() {
		return this.preferredNS.isEnabled();
	}

	public void selectVariationTypeNS(String selection) {
		List<WebElement> elements = this.varificationType.findElements(By
				.tagName("option"));
		for (WebElement e : elements) {
			if (e.getText().equalsIgnoreCase(selection)) {
				e.click();
			}
		}

	}

	// Nucleotide gDNA:

	public void enterSourceNgDNA(String data) {
		this.ngDNASource.sendKeys(data);

	}

	public void enterAccessionNoNgDNA(String data) {
		this.ngDNAAccessionNo.sendKeys(data);

	}

	public void enterGINoNgDNA(String data) {
		this.ngDNAGiNo.sendKeys(data);
	}

	public void enterSequencenNgDNA(String data) {
		this.ngDNASequence.sendKeys(data);
	}

	// Nucleotide cDNA :

	public void enterSourceNcDNA(String data) {
		this.ncDNASource.sendKeys(data);

	}

	public void enterAccessionNoNcDNA(String data) {
		this.ncDNAaccessionNo.sendKeys(data);
	}

	public void enterGINoNcDNA(String data) {
		this.ncDNAgiNo.sendKeys(data);
	}

	public void enterSequenceNcDNA(String data) {
		this.ncDNASequence.sendKeys(data);
	}
	public void clearSequenceNcDNA() {
		this.ncDNASequence.clear();
	}

	// Protein:

	public void enterSourceProtein(String data) {
		this.ProteinSource.sendKeys(data);
	}

	public void enterAccessionNoProtein(String data) {
		this.ProteinAccessionNo.clear();
		this.ProteinAccessionNo.sendKeys(data);
	}

	public void enterGINoProtein(String data) {
		this.ProteinGiNo.sendKeys(data);
	}

	public void enterSequenceProtein(String data) {
		this.ProteinSequence.sendKeys(data);
	}

	// Nucleotide CDS :

	public void enterSourceNoNcDS(String data) {
		this.nCDSAsource.sendKeys(data);
	}

	public void enterAccessionNoNcDS(String data) {
		this.nCDSAccessionNo.sendKeys(data);
	}

	public void enterGINoNcDS(String data) {
		this.nCDSsequence.sendKeys(data);
	}

	public void enterSequenceNcDS(String data) {
		this.nCDSsequence.sendKeys(data);
	}

	// error msg validations:

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

	public String getTextFromTextBoxSymbol() {
		String text = null;
		try {
			this.symbol = this.driver.findElement(By
					.id("locus.identification.symbol"));
			text = this.symbol.getText();
		} catch (Exception e) {

		}

		return text;
	}

	public GeneticFeaturePage clickAddGeneticFeature() {
		this.addGeneticFeature.click();
		this.waitForPageToLoad();
		GeneticFeaturePage page = new GeneticFeaturePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public PopUpWarningUnsavedDataPage clickCancelAndGoToPopUpWarningUnsavedDataPage() {
		this.cancel.click();
		this.waitForPageToLoad();
		PopUpWarningUnsavedDataPage page = new PopUpWarningUnsavedDataPage(
				this.driver);
		PageFactory.initElements(this.driver, page);
		return page;

	}

	public List<String> returnMandatoryFieldsWithoutAsterisksProtein() {
		List<WebElement> formLabels = this.driver.findElements(By
				.className("formsItemLabel"));
		List<String> failureFields = new ArrayList<String>();
		List<String> mandatoryFields = new ArrayList<String>();
		mandatoryFields.add("symbol");
		mandatoryFields.add("sourcespecies");
		// mandatoryFields.add("accession#");
		mandatoryFields.add("sequence");
		for (WebElement label : formLabels) {
			String text = StringUtils.deleteWhitespace(StringUtils
					.lowerCase(label.getText()));
			if (mandatoryFields.contains(text)) {
				WebElement em = null;
				try {
					em = label.findElement(By.tagName("em"));
				} catch (Exception e) {
				}
				if (em == null) {
					failureFields.add(label.getText());
				}
			}

		}
		return failureFields;
	}

	public String getSequenceText_protein() {
		return this.getSequenceText("protein");
	}

	public boolean isSequenceText_protein_editable() {
		return this.isSequenceTextEditable("protein");
	}

	private String getSequenceText(String type) {

		String id = "associatedSequence." + type + ".sequenceCLob";
		WebElement sequenceInput = this.driver.findElement(By.id(id));
		String text = sequenceInput.getAttribute("value");

		return text;
	}

	private boolean isSequenceTextEditable(String type) {
		String id = "associatedSequence." + type + ".sequenceCLob";
		WebElement sequenceInput = this.driver.findElement(By.id(id));
		String text = null;
		try {
			text = sequenceInput.getAttribute("readonly");
		} catch (Exception e) {
		}

		if (StringUtils.equalsIgnoreCase(text, "readonly")) {
			return false;
		} else {
			return true;
		}
	}
	public String getSequenceText_cdna_nucleotide() {
		return this.getSequenceText("CDNA");
	}
}
