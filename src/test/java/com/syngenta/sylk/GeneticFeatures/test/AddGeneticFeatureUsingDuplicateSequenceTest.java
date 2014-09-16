package com.syngenta.sylk.GeneticFeatures.test;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.syngenta.sylk.libraries.CommonLibrary;
import com.syngenta.sylk.libraries.SyngentaException;
import com.syngenta.sylk.main.pages.BasePage;
import com.syngenta.sylk.main.pages.HomePage;
import com.syngenta.sylk.main.pages.LandingPage;
import com.syngenta.sylk.main.pages.SyngentaReporter;
import com.syngenta.sylk.menu_add.pages.AddNewGeneticFeaturePage;
import com.syngenta.sylk.menu_add.pages.BLASTSearchResultPage;
import com.syngenta.sylk.menu_add.pages.GeneticFeatureManualEntryPage;
import com.syngenta.sylk.menu_add.pages.GeneticFeaturePage;
import com.syngenta.sylk.menu_add.pages.NewGeneticFeaturePage;
import com.syngenta.sylk.menu_add.pages.PopUpFlagForCurationPage;
import com.syngenta.sylk.menu_add.pages.PopUpgDNASequenceDataPage;

public class AddGeneticFeatureUsingDuplicateSequenceTest {
	private AddNewGeneticFeaturePage addNewGFPage;
	private GeneticFeatureManualEntryPage GFmanualEntryPage;

	private String gDNASeqData = "CTTTGGGTGGTTTACTTTCTCTCCCGGCGACGGCGAGGCAGGCGCCCGCCAGCGTCACAGGTGGTGACGAGGCATTCCGGTGCCGAGGAGGATCCAAAGGACAGTCGGTTCGTCCTGGCGCG"
			+ "GTCGAGACGGGCCGGGCCCTCCTCCCTCCTGTGCGTGGGAGCCAGCCAGCCAGCCAGGAGCGGCGGGCCCCGCTTGGGCGAGCGACGAATTTTCGGGCGCTTTGACTCGGCTCGGCTCACGG"
			+ "CTCCTGGATATTGGACGACAAAGCGGTGGAAGCTTCTTATTTGGACCGGCCGCGGGCCGGCTGCAAGGAAGAGCGGCTGAAAGGGGTGGGCGAGCTGACTGCTGAGCATACGTACCCGCGCG"
			+ "AAGAAGCAGACGGAGGTCATCACGCTACCCGCGCGTGGCCAGTACCAGACAGACTCCTACCTACACTCAGAAAGCAAGAAGCCCAACGCCGAAAGCAACCACCGCGCTGGTCTCTCGCCTGTG"
			+ "CCGCCCTCGATCGCGCGTGAAGAGAAGCCCCTCACTTCCGTCCTCCTCCTGTCCTGTCCAGCTACCCCGGCCCCGACCCCGATAAAGCCCGCCCTTTAAATCGGCGGATCGAGGCGATGAGCGCC"
			+ "TCTCGCGACGCCAGGGCGGAGAGCTAGCCGGCCGGGAGCCCACACGCAGCTGGAAGCACCAGACCGATCGTGCCGGCCGAGCGGCGGCGCAGGCGCAGGCGCTTACATGGGAGTAGAGGC"
			+ "GGGCGGGTGCGGGCGGAGGGCGGTCGTCACCGGGTTCTACGTCTGGGGCTGGGAGTTCCTCACCGCCCTCCTTCTCTTCTCGGCCGCCGTCGCCGCCGCAGACTCCTACTAGCAAGCTACCAA"
			+ "CCTTCTTTCTTTCATTCCCTTAGGTAGCTCAGCCGTACACACAACAACACACAAGTCATCAGTTACTAGCTAGTTAGTAGCCTATACAACACATACATACATACAAAGGTGAGTGAGGTTCGCGTG";

	private String gDNASeqDataAfterdeleteRows = "CTTTGGGTGGTTTACTTTCTCTCCCGGCGACGGCGAGGCAGGCGCCCGCCAGCGTCACAGGTGGTGACGAGGCATTCCGGTGCCGAGGAGGATCCAAAGGACAGTCGGTTCGTCCTGGCGCGGTCGAGACGGGCCGGGCCCTCCTCCCTCCTG"
			+ "TGCGTGGGAGCCAGCCAGCCAGCCAGGAGCGGCGGGCCCCGCTTGGGCGAGCGACGAATTTTCGGGCGCTTTGACTCGGCTCGGCTCACGGCTCCTGGATATTGGACGACAAAGCGGTGGAAGCTTCTTATTTGGACCGGCCGCGGGCCGGCTG"
			+ "CTGTCCAGCTACCCCGGCCCCGACCCCGATAAAGCCCGCCCTTTAAATCGGCGGATCGAGGCGATGAGCGCCTCTCGCACGTCTGGGGCTGGGAGTTCCTCACCGCCCTCCTTCTCTTCTCGGCCGCCGTCGCCGCCGCAGACTCCTACTAGCAA"
			+ "GCTACCAACCTTCTTTCTTTCATTCCCTTAGGTAGCTCAGCCGTACACACAACAACACACAAGTCATCAGTTACTAGCTAGTTAGTAGCCTATACAACACATACATACATACAAAGGTGAGTGAGGTTCGCGTG";

	private String gDNASeqwithSpecialChar = "CTTTGGGTGGTTTACTTTCTCTCCCGGCGACGGCGAGGCAGGCGCCCGCCAGCGTCACAGGTGGTGACGAGGCATTCCGGTGCCGAGGAGGATCCAAAGGACAGTCGGTTCGTCCTGGCGCGGTCGAGACGGGCCGGGCCCTCCTCCCTCCTG"
			+ "TGCGTGGGAGCCAGCCAGCCAGCCAGGAGCGGCGGGCCCCGCTTGGGCGAGCGACGAATTTTCGGGCGCTTTGACTCGGCTCGGCTCACGGCTCCTGGATATTGGACGACAAAGCGGTGGAAGCTTCTTATTTGGACCGGCCGCGGGCCGGCTG"
			+ "CTGTCCAGCTACCCCGGCCCCGACCCCGATAAAGCCCGCCCTTTAAATCGGCGGATCGAGGCGATGAGCGCCTCTCGCACGTCTGGGGCTGGGAGTTCCTCACCGCCCTCCTTCTCTTCTCGGCCGCCGTCGCCGCCGCAGACTCCTACTAGCAA"
			+ "GCTACCAACCTTCTTTCTTTCATTCCCTTAGGTAGCTCAGCCGTACACACAACAACACACAAGTCATCAGTTACTAGCTAGTTAGTAGCCTATACAACACATACATACATACAAAGGTGAGTGAGGTTCGCGTG*";

	private String rationaleForCuration = "test";

	private String accessionNoNgDNAmodified = "";

	private String sourceSpeciesTaxonomy = "Acanthus ebracteatus";

	private String orientationNS = "test orietations";

	private String fromNS = "100";

	private String toNS = "110";

	private String mapLocationNS = "Boston";

	private String variationTypeNS = "xyz";

	private String accessionNoNgDNAunique = "";

	private String GFSearchBox = "test GF";

	private String NewGFLinkName = "Selenium_GF_Duplicate";

	private LandingPage lp;

	@AfterClass(alwaysRun = true)
	public void quitDriverIfLeftOpen() {
		if (this.lp != null) {
			this.lp.driverQuit();
		}
	}
	@BeforeTest(alwaysRun = true)
	public void testSetUp() {
		this.lp = LandingPage.getLandingPage();
		HomePage homepage = this.lp.goToHomePage();
		this.addNewGFPage = homepage.goToAddGeneticFeaturePage();
	}

	@AfterTest(alwaysRun = true)
	public void cleanUp() {
		if (this.addNewGFPage != null) {
			this.addNewGFPage.driverQuit();
		}
	}

	@Test(description = "This test will verify the functionality of SyLK application while creating Genetic Feature using duplicate sequence", groups = {
			"addNewGeneticFeatureusingSequence", "geneticfeature", "regression"})
	public void addNewGeneticFeatureusingSequence() {
		CommonLibrary common = new CommonLibrary();
		SyngentaReporter reporter = new SyngentaReporter();
		try {
			// step 3
			this.addNewGFPage.selectGeneType("Gene");

			// step 4
			this.addNewGFPage.enterTextInSequence(this.gDNASeqData);

			// step 5
			this.addNewGFPage.clickClear();
			this.addNewGFPage
					.enterTextInSequence(this.gDNASeqDataAfterdeleteRows);
			// Check the right gene type remain selected even after some rows
			// are
			// deleted
			// from between.

			// step 6
			this.addNewGFPage.clickClear();
			this.addNewGFPage.enterTextInSequence(this.gDNASeqwithSpecialChar);
			// Check the right gene type remain selected even after special
			// characters are added at the end.

			// step 7
			this.addNewGFPage.clickClear();
			this.addNewGFPage
					.enterTextInSequence(this.gDNASeqDataAfterdeleteRows);

			// step 8
			BasePage page = this.addNewGFPage.clickFindMatches();
			if (!(page instanceof GeneticFeatureManualEntryPage)) {
				reporter.verifyThisAsFail("Click on Find Matches with Nucleotide sequence ="
						+ this.gDNASeqDataAfterdeleteRows
						+ " did not open up Duplicate Search Results page");
			} else {
				this.GFmanualEntryPage = (GeneticFeatureManualEntryPage) page;
			}

			// step 9
			// The data will be in the wrong textbox so it has to be moved to
			// the
			// right
			// one, there is no clear button to do this.
			String cdnaSequence = this.GFmanualEntryPage.getcDNASequence();
			this.GFmanualEntryPage.EntergDNASequence(cdnaSequence);
			this.GFmanualEntryPage.EntercDNASequence("");

			BLASTSearchResultPage BlastSearchResultPage = this.GFmanualEntryPage
					.clickDuplicateCheck();

			// step 10
			BlastSearchResultPage.clickAndExpandBLAST_gdna_nucleotide();

			// step 11
			// expand the blast that has highest percent of match.
			// new method has to be written for sorting the highest percent of
			// blast.
			GeneticFeaturePage gfPage = BlastSearchResultPage
					.clickAndOpenAssessionFromExpandedBlast_gdna_nucleotide();

			// step 12
			// There is no back button on this page, needs to check with the
			// team.
			// for now i will navigate by browser back
			BlastSearchResultPage = (BLASTSearchResultPage) gfPage
					.browserBack();

			// step 13
			BlastSearchResultPage.checkAllThreeMatches_gdna_nucleotide();

			// It should check the matches and it should get an 100% identity
			// and
			// coverage match .

			// step 14
			PopUpFlagForCurationPage popupFlagForCurationPage = (PopUpFlagForCurationPage) BlastSearchResultPage
					.clickAddAsNewGFAndGoToFlagForCuration();

			// // step 15a

			// step15b

			popupFlagForCurationPage.enterRationale(this.rationaleForCuration);
			// // Not able to figure out the data for rationale . 'test' is not
			// // working
			// // as given by them.
			//
			// // IMPORTANT the method in the popupFolagForcuration
			NewGeneticFeaturePage newGFPage = (NewGeneticFeaturePage) popupFlagForCurationPage
					.clickContinueAndGoToNewGFPage();
			// page = blastPage.clickAddAsNewRNAITriggerButton();

			// BasePage base = BlastSearchResultPage
			// .clickAddAsNewGFAndGoToNewGFPage();
			//
			// newGFPage = null;
			// if (page instanceof PopUpFlagForCurationPage) {
			// PopUpFlagForCurationPage popup = (PopUpFlagForCurationPage) page;
			// popup.clickAndDragThisWindow();
			// popup.enterRationaleRNAi("test");
			// newGFPage = (NewGeneticFeaturePage) popup
			// .clickContinueAndGoToNewGFPage();
			// reporter.reportPass("Add test to rational PopUp for Flag For Curation Page, if it appears");
			// } else {
			// newGFPage = (NewGeneticFeaturePage) base;
			//
			// reporter.verifyEqual(
			// newGFPage.getPageTitle(),
			// PageTitles.new_genetic_feature_page_title,
			// "Click on 'Add new Genetic Feature' button and 'New Genetic Feature page'  opens up.");
			//
			// }

			// step 16
			// copy and enter the AccessionNo from the one which has this
			// sequence.
			newGFPage.enterAccessionNoNgDNA(this.accessionNoNgDNAmodified);

			// step 17
			newGFPage.enterSymbolId("selenium_duplicate");

			newGFPage.enterSourceSpeciesTaxonomy(this.sourceSpeciesTaxonomy);

			newGFPage.selectOrientationNS(this.orientationNS);

			newGFPage.enterFromNS(this.fromNS);

			newGFPage.enterToNS(this.toNS);

			newGFPage.enterMapLocationNS(this.mapLocationNS);

			newGFPage.checkPreferredNS();

			newGFPage.selectVariationTypeNS(this.variationTypeNS);

			// step 18

			newGFPage.clickAddGeneticFeature();
			// check for the following error message
			// "This accession number already exists in sylk, please enter a unique accession no"

			// step 19
			// copy and enter the unique accession no originally created by the
			// sylk.
			newGFPage.enterAccessionNoNgDNA(this.accessionNoNgDNAunique);

			// step 20
			gfPage = newGFPage.clickAddGeneticFeature();
			// make a check to see if the accession number is updated.

			// step 21
			PopUpgDNASequenceDataPage popUpgDNA = gfPage.clickOngDNA();
			// make sure the sequence entered is a gDNA and if its not this
			// method
			// will fail.

			// step 22
			gfPage = popUpgDNA.clickOnClose();

			// step 23

			BlastSearchResultPage = (BLASTSearchResultPage) gfPage
					.browserBack();

			// step 24
			HomePage homepage = BlastSearchResultPage.gotoHomePage();

			// step 25
			homepage.enterInSearchBox(this.GFSearchBox);

			homepage.clickSearchButton();

			// step 26
			gfPage = homepage.clickNewGeneticFeatureLink(this.NewGFLinkName);

			// step 27
			// gfPage.clickOnExport();
			// This method has to handle a browser dialog and then a excel will
			// open
			// up.

			// step 28
			// click open in the dialog and it will open up a excel.

			// step 29
			// close excel without saving the exported data.

			// step 30
			// No back button to click on, in the Genetic Feature Page.
			// hence will do a browser back.

			// step 31
			// no back button again, so browser back to homepage and then enter
			// todayds date to and from and click search.
			// The current date data should be auto generated by the method in
			// the
			// test.
			// GF that was created today should be listed in the search result.

			// *************************end of the test*******************//
		} catch (Exception e) {
			throw new SyngentaException("Test Failed:"
					+ new CommonLibrary().getStackTrace(e));
		} finally {
			reporter.assertAll();
		}
	}
}
