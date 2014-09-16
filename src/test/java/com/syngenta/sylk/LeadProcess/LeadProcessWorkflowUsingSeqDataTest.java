package com.syngenta.sylk.LeadProcess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.testng.SkipException;
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
import com.syngenta.sylk.menu_add.pages.DuplicateSearchResultPage;
import com.syngenta.sylk.menu_add.pages.GeneticFeaturePage;
import com.syngenta.sylk.menu_add.pages.GeneticFeatureSearchResultsPage;

public class LeadProcessWorkflowUsingSeqDataTest {

	private AddNewGeneticFeaturePage addNewGFPage;
	private LandingPage lp;
	private HomePage homepage;

	private List<Object[]> testData = new ArrayList<Object[]>();

	@BeforeClass(alwaysRun = true)
	public void loadData() {
		this.testData = new CommonLibrary()
				.getTestDataAsObjectArray("LeadProcessWF_UsingSequence.xlsx");
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
			HomePage home = menu.gotoHomePage();

		} catch (Exception e) {
			System.out.println("Could not delete GF..");
			System.out.println(new CommonLibrary().getStackTrace(e));
		}

		if (this.lp != null) {
			this.lp.driverQuit();
		}
	}

	@DataProvider(name = "testdata", parallel = false)
	public Iterator<Object[]> loadTestData() {

		return this.testData.iterator();
	}

	@Test(enabled = true, description = "This test will verify the functionality of SyLK application while creating Lead Process workflow using  "
			+ "Sequence Data", dataProvider = "testdata", groups = {
			"leadprocessworkflow_using_accessionnumber", "leadprocess",
			"regression"})
	public void leadProcessWorkflowUsingAccessionNo(String testDescription,
			String row_num, HashMap<String, String> columns) {

		// step 1:
		// Login to Sylk

		SyngentaReporter reporter = new SyngentaReporter();

		reporter.reportPass("Login to SyLK");

		// step 6
		// check has to be made to see if its navigated to the
		// "Add New Genetic Feature page"
		this.addNewGFPage = this.homepage.goToAddGeneticFeaturePage();
		reporter.verifyEqual(this.addNewGFPage.getPageTitle(),
				PageTitles.add_new_genetic_feature_page_title,
				"Open Add Genetic Feature Page");

		this.addNewGFPage.selectGeneType(columns.get("gene_type"));

		this.addNewGFPage.enterTextInSequence(columns.get("nucleotide_seq"));

		boolean isSequenceTypeEnabled = this.addNewGFPage
				.isSequenceTypeEnabled();
		reporter.assertTrue(isSequenceTypeEnabled,
				"Entering a valid accession_number should auto select accesstion radio button.");

		BasePage page = this.addNewGFPage.clickFindMatches();

		DuplicateSearchResultPage duplicatesearchResultPage = null;;
		if (page instanceof GeneticFeaturePage) {
			throw new SkipException(
					"The data being used in this test already exists in the system. This test will delete this data now for future runs and mark this test as SKIPPED.");
		}
		if (page instanceof DuplicateSearchResultPage) {
			GeneticFeatureSearchResultsPage gfSearchResultsPage = null;
			duplicatesearchResultPage = (DuplicateSearchResultPage) page;

		}

	}

}
