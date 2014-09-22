package com.syngenta.sylk.tabbedui.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.syngenta.sylk.libraries.CommonLibrary;
import com.syngenta.sylk.libraries.PageTitles;
import com.syngenta.sylk.libraries.SyngentaException;
import com.syngenta.sylk.main.pages.HomePage;
import com.syngenta.sylk.main.pages.LandingPage;
import com.syngenta.sylk.main.pages.SyngentaReporter;
import com.syngenta.sylk.menu_add.pages.GeneticFeaturePage;
import com.syngenta.sylk.menu_find.pages.SearchSylkPage;

public class CheckRegulatoryInfoMessageInTabTest {

	private LandingPage lp;
	private HomePage homepage;
	private GeneticFeaturePage gfPage;
	private SearchSylkPage searchSylkpage;

	private List<Object[]> testData = new ArrayList<Object[]>();

	@BeforeClass(alwaysRun = true)
	public void loadData() {
		this.testData = new CommonLibrary()
				.getTestDataAsObjectArray("CheckRegulatoryInfoMessageInTab.xlsx");
	}

	@BeforeMethod(alwaysRun = true)
	public void testSetUp() {
		this.lp = LandingPage.getLandingPage();
		this.homepage = this.lp.goToHomePage();
	}

	@DataProvider(name = "checkrnaiingf", parallel = false)
	public Iterator<Object[]> loadTestData() {
		return this.testData.iterator();
	}

	@AfterClass(alwaysRun = true)
	public void cleanUp() {

		if (this.lp != null) {
			this.lp.driverQuit();
		}
	}

	@Test(enabled = true, description = "Check RNAi tab in Genetic Feature", dataProvider = "checkrnaiingf", groups = {
			"CheckRNAiTabInGFTest", "RNAI", "regression"})
	public void addNewGeneticFeatureProtein(String testDescription,
			String row_num, HashMap<String, String> columns) {

		SyngentaReporter reporter = new SyngentaReporter();
		CommonLibrary common = new CommonLibrary();
		try {
			reporter.reportPass("Login to SyLK");

			this.searchSylkpage = this.homepage.goToGFRNAiTriggerROIpromoter();

			reporter.verifyEqual(this.searchSylkpage.getPageTitle(),
					PageTitles.search_sylk_page_title,
					"Select menu item 'GF/RNAi Triggers/ROI/Promoter' and open Search page.");
			// step 3
			this.searchSylkpage.selectAddedBy(columns.get("user"));

			this.searchSylkpage.selectType("Genetic Feature");

			this.searchSylkpage = this.searchSylkpage.clickSearch();

			int searchResults = this.searchSylkpage.getTotalResultCount();

			if (searchResults == 0) {
				reporter.assertThisAsFail("When searched for genetic feature for this user="
						+ columns.get("user") + " resulted in zero results");
			} else {
				reporter.reportPass("When searched for genetic feature for this user="
						+ columns.get("user")
						+ " displays "
						+ searchResults
						+ " results");
			}
			// Step 4
			this.gfPage = (GeneticFeaturePage) this.searchSylkpage
					.clickAndOpenAGFWithoutLeadInfo();
			reporter.verifyEqual(
					this.gfPage.getPageTitle(),
					PageTitles.genetic_feature_page_title,
					"From the search result click on genetic feature link and open a genetic feature which has no Project Lead that been nominated.");

			this.gfPage = this.gfPage.clickOnRegulatoryCheckTab();
			String regulatoryTabMessage = this.gfPage
					.getRegulatoryCheckTabMessage();

			reporter.assertEqual(regulatoryTabMessage, "No Regulatory Info",
					"Verify that There is 'No Regulatory Info' Message appearing");

		} catch (SkipException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new SyngentaException("Test Failed:"
					+ new CommonLibrary().getStackTrace(e));
		} finally {
			reporter.assertAll();
		}
	}
}
