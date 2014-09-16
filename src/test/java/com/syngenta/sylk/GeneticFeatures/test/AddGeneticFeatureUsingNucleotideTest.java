package com.syngenta.sylk.GeneticFeatures.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.syngenta.sylk.libraries.CommonLibrary;
import com.syngenta.sylk.libraries.PageTitles;
import com.syngenta.sylk.libraries.SyngentaException;
import com.syngenta.sylk.main.pages.BasePage;
import com.syngenta.sylk.main.pages.HomePage;
import com.syngenta.sylk.main.pages.LandingPage;
import com.syngenta.sylk.main.pages.MenuPage;
import com.syngenta.sylk.main.pages.SyngentaReporter;
import com.syngenta.sylk.menu_add.pages.AddNewGeneticFeaturePage;
import com.syngenta.sylk.menu_add.pages.BLASTSearchResultPage;
import com.syngenta.sylk.menu_add.pages.GeneticFeatureManualEntryPage;
import com.syngenta.sylk.menu_add.pages.GeneticFeaturePage;
import com.syngenta.sylk.menu_add.pages.NewGeneticFeaturePage;
import com.syngenta.sylk.menu_add.pages.PopUpFlagForCurationPage;
import com.syngenta.sylk.menu_add.pages.PopUpWarningUnsavedDataPage;
import com.syngenta.sylk.menu_add.pages.PopUpcDNASequenceDataPage;

public class AddGeneticFeatureUsingNucleotideTest {

	private AddNewGeneticFeaturePage addNewGFPage;
	private LandingPage lp;
	private HomePage homepage;

	private String nucleotideSymbol;

	private List<Object[]> testData = new ArrayList<Object[]>();

	@BeforeClass(alwaysRun = true)
	public void loadData() {
		this.testData = new CommonLibrary()
				.getTestDataAsObjectArray("Add_GF_Nucleotide.xlsx");
	}

	@AfterClass(alwaysRun = true)
	public void quitDriverIfLeftOpen() {

		if (this.lp != null) {
			this.lp.driverQuit();
		}
	}

	@BeforeMethod(alwaysRun = true)
	public void testSetUp() {
		this.lp = LandingPage.getLandingPage();
		this.homepage = this.lp.goToHomePage();
	}

	@DataProvider(name = "addGFNucleotide", parallel = false)
	public Iterator<Object[]> loadTestData() {
		return this.testData.iterator();
	}

	@AfterMethod(alwaysRun = true)
	public void cleanUp() {

		try {
			MenuPage menu = this.addNewGFPage.getMenuPage();
			HomePage home = menu.gotoHomePage();
			GeneticFeaturePage gfPage = home
					.clickNewGeneticFeatureLink(this.nucleotideSymbol);
			gfPage.clickDeleleThisGeneticFeature();
		} catch (Exception e) {
			System.out.println("Could not delete GF..");

		}

		if (this.lp != null) {
			this.lp.driverQuit();
		}
	}

	@Test(description = "This test will verify the functionality of SyLK application while creating Genetic Feature "
			+ "using Nucleotide", dataProvider = "addGFNucleotide", groups = {
			"addNewGeneticFeatureNucleotide", "geneticfeature", "regression"})
	public void addNewGeneticFeatureNucleotide(String testDescription,
			String row_num, HashMap<String, String> columns) {

		SyngentaReporter reporter = new SyngentaReporter();
		try {

			reporter.reportPass("Login to SyLK");

			this.addNewGFPage = this.homepage.goToAddGeneticFeaturePage();

			reporter.verifyEqual(this.addNewGFPage.getPageTitle(),
					PageTitles.add_new_genetic_feature_page_title,
					"Open 'Add New Genetic Feature Page'");

			this.nucleotideSymbol = (columns.get("nucleotideSymbol"));

			// step 7
			this.addNewGFPage.selectGeneType(columns.get("gene_type"));

			// step 8
			this.addNewGFPage
					.enterTextInSequence(columns.get("nucleotidedata"));
			// check if the nucleotide radio butten is selected..
			String selected = this.addNewGFPage.getSelectedSequanceType();
			reporter.verifyEqual(
					selected,
					"nucleotide",
					"Enter nucleotide data into sequence text area and nucleotide radio gets auto selected.");

			// step 9
			BasePage page = this.addNewGFPage.clickFindMatches();
			GeneticFeatureManualEntryPage GFmanualEntryPage = null;
			// check if the desired page did not open up we will fail this test
			if (!(page instanceof GeneticFeatureManualEntryPage)) {
				reporter.verifyThisAsFail(("Click on Find Matches with Nucleotide data open up genetic feature manual entry page"));
			} else {
				GFmanualEntryPage = (GeneticFeatureManualEntryPage) page;
				reporter.verifyEqual(
						GFmanualEntryPage.getPageTitle(),
						PageTitles.genetic_Feature_Manual_Entry_page_title,
						"Click on find match with valid nucleotide data opens up genetic feature manual entry page.");
			}

			// step 10
			PopUpWarningUnsavedDataPage popUpWarningUnsavedDataPage = GFmanualEntryPage
					.clickCancel();
			if (popUpWarningUnsavedDataPage == null) {
				reporter.assertThisAsFail("PopUP warning indicating unsaved page data did not showup after clicking cancel on GF manual entry page.");
			} else {
				reporter.reportPass("PopUP warning indicating unsaved page data is visible after clicking cancel on GF manual entry page.");

			}
			this.addNewGFPage = popUpWarningUnsavedDataPage
					.clickContinueWithoutSavingAndGoToAddNewGFPage();

			// step 11
			this.addNewGFPage.clickSequanceType(columns.get("seq_type"));

			page = null;
			page = this.addNewGFPage.clickFindMatches();
			if (!(page instanceof GeneticFeatureManualEntryPage)) {
				reporter.verifyThisAsFail("Click on Find Matches with valid Nucleotide Number did not open up GeneticFeatureManualEntryPage Search Results page");
			} else {
				GFmanualEntryPage = (GeneticFeatureManualEntryPage) page;
			}
			// check Genetic Feature manual entry page appears and

			reporter.verifyEqual(GFmanualEntryPage.getPageTitle(),
					PageTitles.genetic_Feature_Manual_Entry_page_title,
					"Genetic Feature manual entry Page is visible after find match is clicked.");

			// check the test is shown in the protein sequence text box

			// step 12
			GFmanualEntryPage.clickDuplicateCheck();
			// check for the error message "Invalid protein sequence data."
			String error = GFmanualEntryPage.getValidationError();
			reporter.verifyEqual(
					error,
					"Invalid protein sequence data.",
					"Click on duplicate check on GF manual enter page throws and error message 'Invalid protein sequence data.' ");

			// check if the right error msg is displayed
			// The QC test case says The Blast page appears after clicking the
			// "Duplicate check " button, instead it
			// gives a error msg that its a invalid sequence, which i think is
			// the
			// right msg.

			// step 13
			PopUpWarningUnsavedDataPage popUpWarningUnsavedPage = GFmanualEntryPage
					.clickCancel();
			// check the pop for unsaved data should appear.
			if (popUpWarningUnsavedPage == null) {
				reporter.assertThisAsFail("Click on cancel on GF manual entry page shows a warning popup");
			} else {
				reporter.reportPass("Click on cancel on GF manual entry page shows a warning popup");
			}

			// step 14
			this.addNewGFPage = popUpWarningUnsavedPage
					.clickContinueWithoutSavingAndGoToAddNewGFPage();

			// check that the sequence is still in the text box
			String actualSeq = this.addNewGFPage.getTextInSequence();

			reporter.verifyEqual(actualSeq, columns.get("nucleotidedata"),
					"The nucleotide sequence is still in the text box.");

			// check that the protein radio butten is still selected.
			String selectedRadiotype = this.addNewGFPage
					.getSelectedSequanceType();
			reporter.verifyEqual(selectedRadiotype, "protein",
					"Protein radio button is still selected.");

			// step 15
			// check that after clicking clear all the field is cleared.
			this.addNewGFPage.clickClear();
			String actualSequence = this.addNewGFPage.getTextInSequence();
			String actualRadioType = this.addNewGFPage
					.getSelectedSequanceType();

			reporter.verifyEqual(
					actualSequence,
					null,
					"Clicking clear button on the 'Add new Genetic Feature Page' clears the sequence text box.");
			// reporter.verifyEqual(
			// actualRadioType,
			// null,
			// "Clicking clear button on the 'Add new Genetic Feature Page' clears the sequence type, radio button field. ");
			// step 16
			this.addNewGFPage
					.enterTextInSequence(columns.get("nucleotidedata"));

			page = null;
			page = this.addNewGFPage.clickFindMatches();
			if (!(page instanceof GeneticFeatureManualEntryPage)) {
				reporter.verifyThisAsFail("Click on Find Matches with Nucleotide Number ="
						+ "nucleotideData"
						+ " did not open up Duplicate Search Results page");
			} else {
				GFmanualEntryPage = (GeneticFeatureManualEntryPage) page;
			}
			// check that the nucleotide radio button is selected
			// check Genetic Feature manual entry page appears with the data in
			// the
			// respective nucleotide text box

			// step 17
			BLASTSearchResultPage blastSearchResultPage = GFmanualEntryPage
					.clickDuplicateCheck();
			// check the blast page appears.
			reporter.verifyEqual(blastSearchResultPage.getPageTitle(),
					PageTitles.blast_search_results_page_title,
					"Click on duplicate check opens up genetic blast search result page.");

			blastSearchResultPage.clickAndExpandBLAST_cdna_nucleotide();

			// check for the close and exact matches in the blast
			Map<String, String> matches = blastSearchResultPage
					.checkAllThreeMatches_cdna_nucleotide();

			reporter.reportPass("Get value for Exact Matches in search result page. Exact Matches = "
					+ "Exact Matches=" + matches.get("Exact Matches"));
			reporter.reportPass("Get value for Close Matches in search result page. Close Matches = "
					+ "Close Matches=" + matches.get("Close Matches"));
			reporter.reportPass("Get value for Distant Matches in search result page. Distant Matches = "
					+ "Distant Matches=" + matches.get("Distant Matches"));

			// maximum percent identity should be shown .
			// Needs to write a method for this.

			// step 18
			GeneticFeaturePage gfPage = blastSearchResultPage
					.clickAndOpenAssessionFromExpandedBlast_cdna_nucleotide();

			reporter.verifyEqual(
					gfPage.getPageTitle(),
					PageTitles.genetic_feature_page_title,
					"Click on the 'Accession number link' on the expanded GF view from the Blast Result page navigates to' Genetic feature Page'.");

			page = null;
			page = gfPage.browserBack();
			blastSearchResultPage = (BLASTSearchResultPage) page;

			// step 19
			BasePage base = blastSearchResultPage
					.clickAddAsNewGFAndGoToNewGFPage();
			// .clickAddAsNewGFAndGoToFlagForCuration();
			NewGeneticFeaturePage newGFPage = null;
			if (base instanceof PopUpFlagForCurationPage) {
				PopUpFlagForCurationPage popUpFlagForCuration = (PopUpFlagForCurationPage) base;
				popUpFlagForCuration.enterRationale(columns.get("rationale"));
				newGFPage = (NewGeneticFeaturePage) popUpFlagForCuration
						.clickContinueAndGoToNewGFPage();
				reporter.assertThisAsFail("Rationale window appears and test case aborted due of this.");
			} else {
				newGFPage = (NewGeneticFeaturePage) base;

			}

			String text = newGFPage.getSequenceText_cdna_nucleotide();
			if (StringUtils.isBlank(text)) {
				reporter.verifyThisAsFail("Sequence not copied into cdna nucleotide sequence.");
			}
			// check for the auto generated accession number for the nucleotide.

			// check for "new Genetic Feature page "appears
			reporter.verifyEqual(
					newGFPage.getPageTitle(),
					PageTitles.new_genetic_feature_page_title,
					"After browser back from 'GF page' to 'Blast search result page,'Click on 'Add as new GF'  opens up 'New genetic feature page' appears.");

			// check for the nucleotide text is copied in the respective text
			// box

			String nucleotext = newGFPage.getSequenceText_cdna_nucleotide();
			System.out.println("Actual:" + nucleotext);
			System.out.println("Expected:" + columns.get("nucleotidedata"));
			reporter.verifyEqual(
					nucleotext,
					columns.get("nucleotidedata"),
					"Copied Nucleotide text in "
							+ PageTitles.new_genetic_feature_page_title
							+ " matches expected nucleotide from testdata sheet.");

			// step 20
			newGFPage.enterAccessionNoNcDNA(columns.get("accessionNoNcDNA"));

			newGFPage.enterGINoNcDNA(columns.get("giNoNcDNA"));

			newGFPage.enterSourceNcDNA(columns.get("sourceNcDNA"));

			newGFPage.checkPreferredNS();

			// Check for the enterSequenceNcDNA field is not editable.test
			// should fail if it gets edited.

			String acualNcDNAseq = newGFPage.getSequenceText_cdna_nucleotide();
			newGFPage.enterSequenceNcDNA(columns.get("sequenceNcDNA"));
			String acualNcDNAseq1 = newGFPage.getSequenceText_cdna_nucleotide();
			reporter.verifyEqual(
					acualNcDNAseq1,
					acualNcDNAseq,
					" This test is failed as 'Nucleotide cDNA sequence text box is editable' in New GF Page",
					"The 'Nucleotide cDNA sequence text box in New GF Page is not editable.");

			// step 21
			newGFPage.clickAddGeneticFeature();

			// check for empty madatory field error message.
			String errorMsg = null;
			errorMsg = newGFPage.getValidationerror();
			reporter.verifyEqual(
					errorMsg,
					"This field is required.",
					"Actual validation error message does not match expected. Expected= 'This field is required.', Actual= '"
							+ errorMsg + "'",
					"Validation error message matches expected validation error.");

			// check for validation error message for all mandatory fields.

			// step 22
			newGFPage.enterSymbolId(columns.get("nucleotideSymbol"));
			newGFPage.enterNameId(columns.get("nameId"));
			newGFPage.enterSynonymsId(columns.get("synonymsId"));
			newGFPage.enterDescriptionId(columns.get("descriptionId"));

			newGFPage.clickAddGeneticFeature();

			String errorMessage = null;
			errorMessage = newGFPage.getValidationerror();
			reporter.verifyEqual(
					errorMessage,
					"This field is required.",
					"Actual validation error message does not match expected. Expected= 'This field is required.', Actual= '"
							+ errorMsg + "'",
					"Validation error message for mandatory field matches with the expected validation error.");

			// step 23

			newGFPage.enterSourceSpeciesTaxonomy(columns
					.get("sourceSpeciesTaxonomy"));

			// step 24
			newGFPage.selectOrientationGD(columns.get("orientationGD"));
			newGFPage.checkPreferredGD();

			// check text is entered as typed sourcespecies
			String textSourceSpecies = newGFPage
					.getTextFromSourceSpeciesTaxonomy();
			reporter.verifyEqual(textSourceSpecies,
					columns.get("sourceSpeciesTaxonomy"),
					"The text entered in sourcespecies is accepted as pasted.");

			// Check to see the selection is made.
			// check to see the checkbox is enabled.
			String expectedValue = "1";
			String actualValueInTheCheckbox = newGFPage
					.checkTheValueInPreferredCheckBox();
			reporter.verifyEqual(
					actualValueInTheCheckbox,
					expectedValue,
					"The Check Box is not enabled ine the 'New GF Page'",
					"The Check box is enabled in the preferred field of 'Genomic Detail'section of new GF page'.");

			// step 25
			newGFPage.enterSourceGS(columns.get("sourceGS"));
			newGFPage.enterAccessionNoGS(columns.get("accessionNoGS"));
			// check for the text is entered as typed or pasted.

			// step 26
			GeneticFeaturePage GFpage = newGFPage.clickAddGeneticFeature();
			// check for the Genetic Feature page appears, displaying the gene
			// summary

			reporter.verifyEqual(
					GFpage.getPageTitle(),
					PageTitles.genetic_feature_page_title,
					"Genetic Feature Page opens up after clicking 'Add Genetic Feature' button on new Genetic Feature Page.");

			// step 27
			// method has to be put for expanding the sequence and check if the
			// buttons there is disabled.

			// step 28

			PopUpcDNASequenceDataPage popUpcDNAseqdataPage = GFpage
					.clickOncDNA();

			// check that this dialog appears with the nucleotide seq in the
			// textbox. .

			// step 29
			GFpage = popUpcDNAseqdataPage.closePopUpcDNASeq();
			// close the dialog box

			// step 30
			HomePage homepage = (HomePage) GFpage.browserBack();
			// navigate to the homepage
			// check for the newly created Genetic Feature in the
			// "new in Sylk list"
			// .

			// step says to click on the back button at the bottom of the page,
			// there is no back button , hence navigating
			// with the browser back.

			// ********************end of the
			// test******************************//
		} catch (Exception e) {
			e.printStackTrace();
			throw new SyngentaException("Test Failed:"
					+ new CommonLibrary().getStackTrace(e));
		} finally {
			reporter.assertAll();
		}
	}
}
