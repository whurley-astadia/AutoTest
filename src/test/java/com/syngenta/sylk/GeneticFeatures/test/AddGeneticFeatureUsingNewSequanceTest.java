package com.syngenta.sylk.GeneticFeatures.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.syngenta.sylk.libraries.CommonLibrary;
import com.syngenta.sylk.libraries.PageTitles;
import com.syngenta.sylk.main.pages.BasePage;
import com.syngenta.sylk.main.pages.HomePage;
import com.syngenta.sylk.main.pages.LandingPage;
import com.syngenta.sylk.main.pages.MenuPage;
import com.syngenta.sylk.main.pages.SyngentaReporter;
import com.syngenta.sylk.menu_add.pages.AddNewGeneticFeaturePage;
import com.syngenta.sylk.menu_add.pages.BLASTSearchResultPage;
import com.syngenta.sylk.menu_add.pages.GeneticFeatureManualEntryPage;
import com.syngenta.sylk.menu_add.pages.GeneticFeaturePage;
import com.syngenta.sylk.menu_add.pages.PopUpFlagForCurationPage;

public class AddGeneticFeatureUsingNewSequanceTest {

	private AddNewGeneticFeaturePage addNewGFPage;
	private LandingPage lp;
	private HomePage homepage;
	private GeneticFeaturePage gfPage;
	private String newGFlink;

	private List<Object[]> testData = new ArrayList<Object[]>();

	// private String newProteinSequenceData =
	// "MQLSAPPYEERLTAPRTWWLMCFLVGVSMALILLPFGTLPLLGGLVGGTAVAAVVASSYGSIRIRVVGDSLI"
	// +
	// "AGEAKIPVAALGEAEIMDQEEARAWRTYKANPRAFMLLRSYIPTALRVEVTDPQDPTPYLYLSTREPERLAAALKAARTEA";
	//
	// private String[] blastdata = {"cDNA", "Nucleotide"};
	//
	// private String gFname = "";
	//
	// private String accessionNowithspaceATend = "SYLK002129-PROT ";
	//
	// private String sourceSpecies = "Ananas comosus";
	//
	// private String symbol = "SELENIUM_NEWSEQUENCE";

	@BeforeClass(alwaysRun = true)
	public void loadData() {
		this.testData = new CommonLibrary()
				.getTestDataAsObjectArray("Add_GF_NewSequence.xlsx");
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

	@AfterMethod(alwaysRun = true)
	public void cleanUp() {

		try {
			MenuPage menu = this.addNewGFPage.getMenuPage();
			this.homepage = menu.gotoHomePage();
			GeneticFeaturePage gfPage = this.homepage
					.clickNewGeneticFeatureLink(this.newGFlink);
			gfPage.clickDeleleThisGeneticFeature();
		} catch (Exception e) {
			System.out.println("Could not delete GF..");
			// System.out.println(new CommonLibrary().getStackTrace(e));
		}

		if (this.lp != null) {
			this.lp.driverQuit();
		}
	}
	@DataProvider(name = "gfNewSequence", parallel = false)
	public Iterator<Object[]> loadTestData() {

		return this.testData.iterator();
	}

	@Test(enabled = true, description = "This test will verify the functionality of SyLK application while creating Genetic Feature using "
			+ "new sequence", dataProvider = "gfNewSequence", groups = {
			"addNewGeneticFeatureNewSequence", "geneticfeature", "regression"})
	public void addGeneticFeatureUsingNewSequence(String testDescription,
			String row_num, HashMap<String, String> columns) {

		SyngentaReporter reporter = new SyngentaReporter();

		reporter.reportPass("Login to SyLK");

		this.addNewGFPage = this.homepage.goToAddGeneticFeaturePage();
		reporter.verifyEqual(this.addNewGFPage.getPageTitle(),
				PageTitles.add_new_genetic_feature_page_title,
				"Open 'Add New Genetic Feature Page'");

		this.newGFlink = columns.get("newGFlink");

		// step 3
		this.addNewGFPage.selectGeneType(columns.get("gene_type"));

		// step 4
		this.addNewGFPage.enterTextInSequence(columns.get("newSequenceData"));
		// this.newSequenceData
		// Check : the correct sequence type should be auto selected.

		boolean isSequenceTypeEnabled = this.addNewGFPage
				.isSequenceTypeEnabled();

		reporter.verifyTrue(
				isSequenceTypeEnabled,
				"Sequence Type radio buttons were not enabled after entering a valid nucleotide data.",
				"Enter a valid nucleotide sequence enables right sequence type radio buttons");

		// // step 5
		// GeneticFeatureManualEntryPage GFmanualEntryPage =
		// (GeneticFeatureManualEntryPage) this.addNewGFPage
		// .clickFindMatches();
		//

		GeneticFeatureManualEntryPage GFmanualEntryPage = null;

		// Step 5
		BasePage page = this.addNewGFPage.clickFindMatches();

		if (!(page instanceof GeneticFeatureManualEntryPage)) {
			reporter.assertThisAsFail("Click on Find Matches with nucleotide sequence ="
					+ columns.get("newSequenceData")
					+ " did not open up Duplicate Search Results page");
		} else {
			GFmanualEntryPage = (GeneticFeatureManualEntryPage) page;
		}

		reporter.verifyEqual(
				GFmanualEntryPage.getPageTitle(),
				PageTitles.genetic_Feature_Manual_Entry_page_title,
				"Click on Find Match with valid nucleotide sequence opens up 'Genetic feature manual entry page' ");

		String nucleotideSeqData = GFmanualEntryPage.getcDNASequence();
		reporter.verifyEqual(
				nucleotideSeqData,
				columns.get("newSequenceData"),
				"Nucleotide data entered in New GF page is auto populated in GF Manual entry page");

		// step 6
		GFmanualEntryPage.EnterProteinSequence(columns.get("proteinseqdata"));

		String proteinData = GFmanualEntryPage.getTextInProteinSequence();
		reporter.verifyEqual(proteinData, columns.get("proteinseqdata"),
				"Additional protein sequence cannot be added",
				"Additional protein sequence can be added '.");

		String sequenceText = GFmanualEntryPage.getTextInProteinSequence();
		reporter.verifyEqual(
				sequenceText,
				columns.get("proteinseqdata"),
				"Protein Sequence text field does not matches with the text entered in the 'add genetic feature page'.",
				"Protein Sequence text field show the right  text sequence entered in the 'add gentic feature page'.");

		// step 7
		BLASTSearchResultPage blastSearchResultPage = GFmanualEntryPage
				.clickDuplicateCheck();

		reporter.verifyEqual(blastSearchResultPage.getPageTitle(),
				PageTitles.blast_search_results_page_title,
				"Click on Duplicate Check button opens up blast search results page");

		// step8
		blastSearchResultPage.clickAndExpandBLAST_cdna_nucleotide();

		blastSearchResultPage
				.clickAndExPandTheSequenceFromExpandedBlast_cdna_nucleotide();
		this.gfPage = blastSearchResultPage
				.clickViewButtonOnExpandedgeneticFeature();
		reporter.verifyEqual(
				blastSearchResultPage.getPageTitle(),
				PageTitles.genetic_feature_page_title,
				"Click on view genetic feature button in an expanded sequence under cdna - nucleotide opens up  genetic feature page");
		blastSearchResultPage = (BLASTSearchResultPage) this.gfPage
				.browserBack();
		reporter.verifyEqual(
				blastSearchResultPage.getPageTitle(),
				PageTitles.blast_search_results_page_title,
				"A browser back on genetic feature page opens up blast search result page correctly with all blast collapsed");

		blastSearchResultPage.clickAndExpandBLAST_cdna_nucleotide();

		blastSearchResultPage
				.clickAndExPandTheSequenceFromExpandedBlast_cdna_nucleotide();
		PopUpFlagForCurationPage curationPage = blastSearchResultPage
				.clickAddGFButtonOnExpandedgeneticFeature();

		if (curationPage == null) {
			reporter.assertThisAsFail("Click on Add GF button shows opens up Flag for curation popup page.");
		} else {
			reporter.reportPass("Click on Add GF button shows opens up Flag for curation popup page.");
		}

		curationPage.enterRationale("test");
		this.gfPage = (GeneticFeaturePage) curationPage
				.clickContinueAndGoToNewGFPage();

		reporter.verifyEqual(
				blastSearchResultPage.getPageTitle(),
				PageTitles.genetic_feature_page_title,
				"Click on continue on flag for curation page opens up genetic feature page correctly.");

		// step9

		// step 12
		// blastSearchResultPage = (BLASTSearchResultPage) this.gfPage
		// .browserBack();

		// step 13

		// String accession_number = blastSearchResultPage.getAccessionNumber();
		// if (StringUtils.isBlank(accession_number)) {
		// reporter.verifyThisAsFail("Check for the generated accession number is provided on blast search result screen. Accesstion number is blank.");
		// } else {
		// reporter.reportPass("Check for the generated accession number is provided on blast search result screen. Actual accession_number="
		// + accession_number);
		// }
		//
		// // step 8
		//
		// Map<String, String> matches = blastSearchResultPage
		// .checkAllThreeMatches_cdna_protein();
		//
		// reporter.reportPass("Get value for Exact Matches in search result page. Exact Matches = "
		// + "Exact Matches=" + matches.get("Exact Matches"));
		// reporter.reportPass("Get value for Close Matches in search result page. Close Matches = "
		// + "Close Matches=" + matches.get("Close Matches"));
		// reporter.reportPass("Get value for Distant Matches in search result page. Distant Matches = "
		// + "Distant Matches=" + matches.get("Distant Matches"));
		//
		// blastSearchResultPage.clickAndExpandBLAST_cdna_nucleotide();
		//
		// // step 9
		//
		// blastSearchResultPage.expandOrColapseThisGFInBlastProtein("gfName");
		//
		// // blastSearchResultPage.clickViewGeneticFeatureInBlast("gfName");
		// GeneticFeaturePage gFPage = blastSearchResultPage
		// .clickViewGeneticFeatureInBlast(columns.get("accessionNo"));
		// // check for the gene summary appears on the genetic feature page.
		// reporter.verifyEqual(gFPage.getPageTitle(),
		// PageTitles.genetic_feature_page_title,
		// "Click on view GF button and genetic feature page opens up");
		//

		// BlastSearchResultPage.checkAllThreeMatches_cdna_protein();
		// BlastSearchResultPage
		// .clickAndExPandTheSequenceWithTheHightestPercentage_cdna_nucleotide();
		//
		// // expand the blast for the sequence entered.
		// // GeneticFeaturePage GFpage = BlastSearchResultPage
		// // .clickViewGeneticFeatureInBlast(this.gfName);
		//
		// // need to click on something which reliable like the first one.
		// // as of now i am passing a gfname , which cannot be reliable, need
		// to
		// // put a method for this.
		//
		// // step 9
		// GeneticFeaturePage GFpage = BlastSearchResultPage
		// .clickViewGeneticFeatureInBlast(this.gFname);
		// // check that the GFpage appears showing gene summary for sequence
		// selected.
		//

		/*
		 * // step 10
		 * 
		 * PopUpFileDownLoad popUpfiledownload = GFpage.clickOnExport(); //
		 * check for file download dialog appears.
		 * 
		 * // step 11 PopUpExcelNewSeqPage popUpExcelNewSeqPage =
		 * popUpfiledownload.clickOk(); // check for the excel page opens up and
		 * close
		 * 
		 * // its a browser popup and needs to put implementation // Alert kind
		 * GFpage = popUpExcelNewSeqPage.closeExcel();
		 * 
		 * // step 12 BlastSearchResultPage = (BLASTSearchResultPage)
		 * GFpage.browserBack(); // check that the BlastSearchResult page opens
		 * up // Navigating through browser back.
		 * 
		 * // step 13
		 * 
		 * BlastSearchResultPage.clickAndExpandBLAST_cdna_protein(); //
		 * BlastSearchResultPage.clickAndExpandTheFirstSequenceInTheBlast(); //
		 * implementation has to be put for this method.
		 * 
		 * PopUpFlagForCurationPage popupFlagForCurationPage =
		 * BlastSearchResultPage .clickOnAddNewSequenceToThisGF();
		 * 
		 * //
		 * --------------------------------------------------------------------
		 * ------------------------------
		 * 
		 * BlastSearchResultPage = popupFlagForCurationPage.clickCancel();
		 * 
		 * // step 14 // NewGeneticFeaturePage //
		 * newGFPage=popupFlagForCurationPage.clickContinueAndGoToNewGFPage();
		 * 
		 * // IMP NOTE : Either the test case or the UI has chaged. Genetic
		 * Fature // Page is expected after // clicking on to Add new sequence
		 * to <name> GF, instead 'the flag for // rational' opens up , then it
		 * // ask to click on cancel on GF page, there is no cancel button on GF
		 * // page, and it says to do operation // with the field on new GF
		 * page, but doesn't checks that page. // Once i start working on IE and
		 * can handle it then i can know which // page it is actually navigating
		 * to. // meanwhile i am clicking on to ADD As New GF button on blast
		 * and // navigating there.
		 * 
		 * NewGeneticFeaturePage newGFPage = BlastSearchResultPage
		 * .clickAddAsNewGFAndGoToNewGFPage();
		 * 
		 * PopUpWarningUnsavedDataPage popUpWarningUnsavedDataPage = newGFPage
		 * .clickCancel(); // check unsaved dialog appears
		 * 
		 * BlastSearchResultPage = popUpWarningUnsavedDataPage
		 * .clickContinueWithoutSavingAndGoToBLASTsearchPage();
		 * 
		 * newGFPage = BlastSearchResultPage.clickAddAsNewGFAndGoToNewGFPage();
		 * // check that new GF page appears with the sequence and accession no
		 * // in the new sequence form. // seq is uneditable
		 * 
		 * // step 16
		 * 
		 * newGFPage.enterAccessionNoProtein(this.accessionNowithspaceATend); //
		 * check for validation error message "Invalid Accession Number". //
		 * check for the sequence is pre populated in the proper section of the
		 * // text box, protein or nucleotide newGFPage.getValidationerror();
		 * 
		 * // step 17
		 * 
		 * // step 18 newGFPage.enterSourceSpeciesTaxonomy(this.sourceSpecies);
		 * // Check for data is entered as typed
		 * newGFPage.enterSymbolId(this.symbol); // Check for data is entered
		 * typed.
		 * 
		 * // step 19
		 */
	}
}
