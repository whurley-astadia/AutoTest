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
import com.syngenta.sylk.menu_add.pages.PopUpCDSSequenceDataPage;
import com.syngenta.sylk.menu_add.pages.PopUpFlagForCurationPage;
import com.syngenta.sylk.menu_add.pages.PopUpProteinSequenceDataPage;
import com.syngenta.sylk.menu_add.pages.PopUpWarningUnsavedDataPage;
import com.syngenta.sylk.menu_add.pages.PopUpcDNASequenceDataPage;
import com.syngenta.sylk.menu_add.pages.PopUpgDNASequenceDataPage;

public class AddGeneticFeatureUsingProteinTest {

	private AddNewGeneticFeaturePage addNewGFPage;
	private LandingPage lp;
	private HomePage homepage;

	private String proteinSymbol;

	private List<Object[]> testData = new ArrayList<Object[]>();

	@BeforeClass(alwaysRun = true)
	public void loadData() {
		this.testData = new CommonLibrary()
				.getTestDataAsObjectArray("Add_GF_Protein.xlsx");
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

	@DataProvider(name = "addGFprotein", parallel = false)
	public Iterator<Object[]> loadTestData() {
		return this.testData.iterator();
	}

	@AfterMethod(alwaysRun = true)
	public void cleanUp() {

		try {
			MenuPage menu = this.addNewGFPage.getMenuPage();
			HomePage home = menu.gotoHomePage();
			GeneticFeaturePage gfPage = home
					.clickNewGeneticFeatureLink(this.proteinSymbol);
			gfPage.clickDeleleThisGeneticFeature();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Could not delete GF..");
			// System.out.println(new CommonLibrary().getStackTrace(e));
		}

		if (this.lp != null) {
			this.lp.driverQuit();
		}
	}

	@Test(enabled = true, description = "This test will verify the functionality of SyLK application while creating Genetic Feature 	"
			+ "using Protein", dataProvider = "addGFprotein", groups = {
			"addNewGeneticFeatureProtein", "geneticfeature", "regression"})
	public void addNewGeneticFeatureProtein(String testDescription,
			String row_num, HashMap<String, String> columns) {

		SyngentaReporter reporter = new SyngentaReporter();
		try {
			reporter.reportPass("Login to SyLK");

			// step 6
			// check has to be made to see if its navigated to the
			// "Add New Genetic Feature page"
			this.addNewGFPage = this.homepage.goToAddGeneticFeaturePage();

			reporter.verifyEqual(this.addNewGFPage.getPageTitle(),
					PageTitles.add_new_genetic_feature_page_title,
					"Open 'Add New Genetic Feature Page'");

			this.proteinSymbol = columns.get("proteinSymbol");

			// step 7
			this.addNewGFPage.selectGeneType(columns.get("gene_type"));

			// step 8
			this.addNewGFPage.enterTextInSequence(columns.get("proteindata"));
			// check if the right sequence type is auto selected, that is the
			// protein is
			// selected.
			boolean isSequenceTypeEnabled = this.addNewGFPage
					.isSequenceTypeEnabled();

			reporter.verifyTrue(
					isSequenceTypeEnabled,
					"Sequence Type radio buttons were not enabled after entering a valid protein data.",
					"Enter a valid protein number enables sequence type radio buttons");
			String selected = this.addNewGFPage.getSelectedSequanceType();
			reporter.verifyEqual(selected, "protein",
					"Enter a valid protein number and protein radio button is auto selected");

			// step 9
			this.addNewGFPage.clickSequanceType("ACCESSION_NUMBER");
			// check accession number radio button is selected.
			selected = null;
			selected = this.addNewGFPage.getSelectedSequanceType();
			reporter.verifyEqual(selected, "accession_number",
					"Click on accession number radio button and the radio button is selected");
			// step 10
			this.addNewGFPage.clickFindMatches();
			// check for the validation error msg for the Accession number
			// should
			// not be more
			// 32 chars .
			String errorMsg = "The accession number can not exceed 32 characters.";
			String actualError = this.addNewGFPage.getValidationerror();
			reporter.verifyEqual(
					actualError,
					errorMsg,
					"With protein data entered and accession number selected click on find match gives and error message.");
			// step 11
			this.addNewGFPage.clickClear();
			// check the user is able to clear the text boxes.
			String sequenceText = this.addNewGFPage.getTextInSequence();
			reporter.verifyEqual(sequenceText, null,
					"Click on clear button to clear text from sequence text area");

			this.addNewGFPage.enterTextInSequence(columns.get("proteindata"));

			// step12
			// No step for step 12

			// step 13
			GeneticFeatureManualEntryPage GFmanualEntryPage = null;

			BasePage page = this.addNewGFPage.clickFindMatches();

			if (!(page instanceof GeneticFeatureManualEntryPage)) {
				reporter.assertThisAsFail("Click on Find Matches with Protein Number ="
						+ columns.get("proteindata")
						+ " did not open up Duplicate Search Results page");
			} else {
				GFmanualEntryPage = (GeneticFeatureManualEntryPage) page;
			}
			// check that the " GeneticFeatureManualEntryPage" appears with the
			// Protein data populated the protein seq text box.
			reporter.verifyEqual(
					GFmanualEntryPage.getPageTitle(),
					PageTitles.genetic_Feature_Manual_Entry_page_title,
					"Click on Find Match with valid protein data Genetic feature manual entry page opens up");
			String proteinDataInManual = GFmanualEntryPage
					.getTextInProteinSequence();
			reporter.verifyEqual(proteinDataInManual,
					columns.get("proteindata"),
					"Protein data entered in New GF page is auto populated in GF Manual entry page");
			// step 14
			PopUpWarningUnsavedDataPage popUpWarningUnsavedDataPage = GFmanualEntryPage
					.clickCancel();
			if (popUpWarningUnsavedDataPage == null) {
				reporter.assertThisAsFail("Click on cancel on GF manual entry page shows a warning popup");
			} else {
				reporter.reportPass("Click on cancel on GF manual entry page shows a warning popup");
			}
			this.addNewGFPage = popUpWarningUnsavedDataPage
					.clickContinueWithoutSavingAndGoToAddNewGFPage();

			// step 15
			this.addNewGFPage.clickClear();

			// step 16
			this.addNewGFPage.enterTextInSequence(columns.get("proteindata"));

			// step 17
			page = null;
			page = this.addNewGFPage.clickFindMatches();

			if (!(page instanceof GeneticFeatureManualEntryPage)) {
				reporter.assertThisAsFail("Click on Find Matches with Protein Number ="
						+ columns.get("proteindata")
						+ " did not open up GF Manual entry page");
			} else {
				GFmanualEntryPage = (GeneticFeatureManualEntryPage) page;
			}

			// step 18
			BLASTSearchResultPage blastSearchResultPage = GFmanualEntryPage
					.clickDuplicateCheck();
			// check for the Blast search result page appears
			// check for the generated accession no is provided.

			reporter.verifyEqual(blastSearchResultPage.getPageTitle(),
					PageTitles.blast_search_results_page_title,
					"Click on Duplicate Check button opens up blast search results page");
			String accession_number = blastSearchResultPage
					.getAccessionNumber();
			if (StringUtils.isBlank(accession_number)) {
				reporter.verifyThisAsFail("Check for the generated accession number is provided on blast search result screen. Accesstion number is blank.");
			} else {
				reporter.reportPass("Check for the generated accession number is provided on blast search result screen. Actual accession_number="
						+ accession_number);
			}

			// step 19
			Map<String, String> matches = blastSearchResultPage
					.checkAllThreeMatches_protein_protein();

			reporter.reportPass("Get value for Exact Matches in search result page. Exact Matches = "
					+ "Exact Matches=" + matches.get("Exact Matches"));
			reporter.reportPass("Get value for Close Matches in search result page. Close Matches = "
					+ "Close Matches=" + matches.get("Close Matches"));
			reporter.reportPass("Get value for Distant Matches in search result page. Distant Matches = "
					+ "Distant Matches=" + matches.get("Distant Matches"));

			blastSearchResultPage.clickAndExpandBLASTp();

			boolean gfExists = blastSearchResultPage
					.checkIfThisGFExistsInBlastProtein(columns
							.get("accessionNo"));
			System.out.println("GF Exists:::" + gfExists);
			/*
			 * If above boolean is false it means that this accessionNo was not
			 * found in the search result. We will fail this test immediately as
			 * it cannot go any further without selecting this accessionNo
			 */

			if (!gfExists) {
				reporter.assertThisAsFail(columns.get("accessionNo")
						+ " was not listed in Blast search result page.");
			} else {
				reporter.reportPass(columns.get("accessionNo")
						+ " is listed in Blast search result page.");
			}
			// step 20
			blastSearchResultPage.expandOrColapseThisGFInBlastProtein(columns
					.get("accessionNo"));

			// step 21
			GeneticFeaturePage gFPage = blastSearchResultPage
					.clickViewGeneticFeatureInBlast(columns.get("accessionNo"));
			// check for the gene summary appears on the genetic feature page.
			reporter.verifyEqual(gFPage.getPageTitle(),
					PageTitles.genetic_feature_page_title,
					"Click on view GF button and genetic feature page opens up");
			// step 22
			blastSearchResultPage = (BLASTSearchResultPage) gFPage
					.browserBack();
			// check for the blast search result page appears.
			reporter.verifyEqual(blastSearchResultPage.getPageTitle(),
					PageTitles.blast_search_results_page_title,
					"Click on back button takes the user back to blast search result page");
			// step 23
			blastSearchResultPage.clickAndExpandBLASTp();

			// step 24
			GeneticFeaturePage GFPage;
			GFPage = blastSearchResultPage
					.clickThisAccessionNoInBlastProteinandGoToGFPage(columns
							.get("accessionNo"));
			// check for the gene summary appears on genetic feature page.
			reporter.verifyEqual(GFPage.getPageTitle(),
					PageTitles.genetic_feature_page_title,
					"Click on accession number in blast and genetic feature page opens up");

			// step 25
			blastSearchResultPage = (BLASTSearchResultPage) gFPage
					.browserBack();
			// According to the test case it will click back button and navigate
			// to
			// Add new genetic feature screen.
			// But i am using browser back and the immediate page is the blast
			// search result page.

			// step 26
			NewGeneticFeaturePage newGFPage = null;
			page = blastSearchResultPage.clickAddAsNewGFAndGoToNewGFPage();
			if (page instanceof PopUpFlagForCurationPage) {
				PopUpFlagForCurationPage popup = (PopUpFlagForCurationPage) page;
				popup.enterRationale("test");
				newGFPage = (NewGeneticFeaturePage) popup
						.clickContinueAndGoToNewGFPage();
			} else {
				newGFPage = (NewGeneticFeaturePage) page;
			}
			// check for the new genetic feature page appears.
			// check for the red asterisk for the mandatory fields.
			reporter.verifyEqual(
					newGFPage.getPageTitle(),
					PageTitles.new_genetic_feature_page_title,
					"Click on add new genetic feature button on blast and new genetic feature page opens up");
			List<String> fields = newGFPage
					.returnMandatoryFieldsWithoutAsterisksProtein();
			/*
			 * if (fields.size() != 0) { String failures = ""; for (String s :
			 * fields) { failures = failures + "," + s; }
			 * reporter.verifyThisAsFail
			 * ("Following mandatory fields are not marked with Asterisks. " +
			 * failures); } else {
			 * reporter.reportPass("All mandatory fields marked with asterisks"
			 * ); }
			 */
			// step 27
			PopUpWarningUnsavedDataPage popUpWarningPage = newGFPage
					.clickCancel();
			// check for the dialog appears.
			if (popUpWarningPage == null) {
				reporter.assertThisAsFail("Click on cancel on new GF manual entry page shows a warning popup");
			} else {
				reporter.reportPass("Click on cancel on new GF manual entry page shows a warning popup");
			}

			blastSearchResultPage = popUpWarningPage
					.clickContinueWithoutSavingAndGoToBLASTsearchPage();

			// check for the blast search result appears
			reporter.verifyEqual(blastSearchResultPage.getPageTitle(),
					PageTitles.blast_search_results_page_title,
					"Click on Duplicate Check button opens up blast search results page");

			// step 28
			newGFPage = null;
			page = blastSearchResultPage.clickAddAsNewGFAndGoToNewGFPage();
			if (page instanceof PopUpFlagForCurationPage) {
				PopUpFlagForCurationPage popup = (PopUpFlagForCurationPage) page;
				popup.enterRationale("test");
				newGFPage = (NewGeneticFeaturePage) popup
						.clickContinueAndGoToNewGFPage();
			} else {
				newGFPage = (NewGeneticFeaturePage) page;
			}

			// step 29
			boolean enabled = newGFPage.isPreferredNSCheckboxEnabled();
			// check for the checkbox is enabled.
			if (!enabled) {
				reporter.verifyThisAsFail("Preferred checkbox within new genetic feature form is enabled");
			} else {
				reporter.reportPass("Preferred checkbox within new genetic feature form is enabled");
				newGFPage.checkPreferredNS();
			}

			// step 30
			newGFPage.enterSourceProtein(columns.get("proteinSource"));

			newGFPage.enterAccessionNoProtein("angaj23");

			newGFPage.enterGINoProtein(columns.get("ProteinGiNo"));
			// check for text is entered as typed
			// Sequence is automatically copied in the corresponding and is not
			// editable.
			// check the auto generated accession number is editable.
			// step 31

			String text = newGFPage.getSequenceText_protein();
			if (StringUtils.isBlank(text)) {
				reporter.verifyThisAsFail("Sequence get automatically copied under sequence textarea for Protein");
			} else {
				reporter.reportPass("Sequence get automatically copied under sequence textarea for Protein");
			}
			// reporter.verifyTrue(!newGFPage.isSequenceText_protein_editable(),
			// "Sequence text area should be non-editable",
			// "Sequence text area should be non-editable");

			newGFPage.clickAddGeneticFeature();
			errorMsg = null;
			errorMsg = newGFPage.getValidationerror();
			reporter.verifyEqual(
					errorMsg,
					"This field is required.",
					"Actual validation error message does not match expected. Expected= 'This field is required.', Actual= '"
							+ errorMsg + "'",
					"Validation error message matches expected validation error.");
			// check for the right validation error message.

			// step 32
			newGFPage.enterSymbolId(columns.get("proteinSymbol"));
			newGFPage.enterNameId(columns.get("proteinName"));
			newGFPage.enterSynonymsId(columns.get("proteinSynonymsId"));
			newGFPage.enterDescriptionId(columns.get("proteinDescription"));
			// check text is entered as typed.

			// step 33
			newGFPage.enterSourceSpeciesTaxonomy(columns.get("sourceSpecies"));
			// check text is entered as typed

			// step 34
			newGFPage.selectOrientationGD(columns.get("orientationGD"));
			// check the seletion is made.

			// step 35
			newGFPage.enterSourceGS(columns.get("sourceGS"));
			newGFPage.enterAccessionNoGS(columns.get("accessionNoGS"));
			// check for the text is entered as typed or pasted.

			// step 36
			GFPage = null;
			GFPage = newGFPage.clickAddGeneticFeature();
			// check for genetic feature page appears displaying the gene
			// summary.

			reporter.verifyEqual(GFPage.getPageTitle(),
					PageTitles.genetic_feature_page_title,
					"Click on add genetic feature button opens up genetic feature details page");

			// step 37
			// To my understanding the step is to expand and collapse the detail
			// on
			// the summary page.
			// needs to put a method for this.

			// step 38
			// this.homepage = GFPage.gotoHomePage();
			// GFPage = this.homepage
			// .clickNewGeneticFeatureLink(this.proteinSymbol);

			GFPage = GFPage.clickDetailSequenceTab();
			PopUpProteinSequenceDataPage popUpProtein = GFPage.clickOnProtein();
			// check for the data appears in the pop up.

			if (popUpProtein == null) {
				reporter.verifyThisAsFail("Click on protein on genetic feature details page a popup with protein details opens up");
			} else {
				reporter.reportPass("Click on protein on genetic feature details page a popup with protein details opens up");
			}

			// step 39
			GFPage = popUpProtein.clickOnCloseProtein();
			// check for the dialog box closes.

			// step 40
			// a nucleotide sequence should be paired with any (CDNA,gDNA orCDS)
			PopUpcDNASequenceDataPage popUpCDNA = GFPage.clickOncDNA();
			// check for the data appears in the dialog with data and the
			// sequance
			// data is not editable.
			if (popUpCDNA == null) {
				reporter.verifyThisAsFail("Click on cDNA (ADD) in the  'genetic feature details' page did not open up the CDNA Pop up.");
			} else {
				reporter.reportPass("Click on cDNA in the 'genetic feature details' page opened up the  cDNA Pop up.");
			}
			// step 40 and 41
			GFPage = popUpCDNA.clickOnCancel();

			PopUpgDNASequenceDataPage popUpgDNA = GFPage.clickOngDNA();

			GFPage = popUpgDNA.clickOnCancel();

			PopUpCDSSequenceDataPage popUpCDS = GFPage.clickOnCDS();
			if (popUpCDS == null) {
				reporter.verifyThisAsFail("Click on CDS on genetic feature details page a popup with CDS details opens up");
			} else {
				reporter.reportPass("Click on CDS on genetic feature details page a popup with CDS details opens up");
			}
			GFPage = popUpCDS.clickOnCancel();
			// If no nucleotide was paired , then
			// Check for the add cDNA, add gDNA and add CDS will be displayed.

			// step 42
			// No back button to click on this page, needs to check with the
			// team.
		} catch (Exception e) {
			e.printStackTrace();
			throw new SyngentaException("Test Failed:"
					+ new CommonLibrary().getStackTrace(e));
		} finally {
			reporter.assertAll();
		}

		// ******************************End of the test*********************//
	}
}
