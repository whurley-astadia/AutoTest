package com.syngenta.sylk.tabbedui.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
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
import com.syngenta.sylk.menu_find.pages.RNAiPage;
import com.syngenta.sylk.menu_find.pages.SearchSylkPage;

public class Check_CreatedBy_CreatedDate_In_Details_Tab_In_Tabbed_View_RNAi {

	private List<Object[]> testData = new ArrayList<Object[]>();
	private LandingPage lp;
	private HomePage homepage;
	private SearchSylkPage searchSylkpage;

	@BeforeClass(alwaysRun = true)
	public void loadData() {
		this.testData = new CommonLibrary()
				.getTestDataAsObjectArray("Check_CreatedByAndCreatedDate_DetailTab.xlsx");
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

	@DataProvider(name = "checkCreatedByAndDateField", parallel = false)
	public Iterator<Object[]> loadTestData() {

		return this.testData.iterator();
	}

	@AfterMethod(alwaysRun = true)
	public void cleanUp() {

		if (this.lp != null) {
			this.lp.driverQuit();
		}
	}

	@Test(enabled = true, description = "Check Created by and Created date fields in details Tab in tabbed view RNAi ", dataProvider = "checkCreatedByAndDateField", groups = {
			"checkCreatedByAndDateField", "RNAI", "regression"})
	public void rNAiConstructTabInTabbedView(String testDescription,
			String row_num, HashMap<String, String> columns) {

		SyngentaReporter reporter = new SyngentaReporter();

		try {

			// step1

			reporter.reportPass("Login to SyLK");

			// Step 2
			this.searchSylkpage = this.homepage.goToGFRNAiTriggerROIpromoter();

			reporter.verifyEqual(this.searchSylkpage.getPageTitle(),
					PageTitles.search_sylk_page_title,
					"Select menu item 'GF/RNAi Triggers/ROI/Promoter' and open Search page.");

			// step 3
			this.searchSylkpage.selectAddedBy(columns.get("user"));

			this.searchSylkpage.selectType(columns.get("selectType"));

			this.searchSylkpage = this.searchSylkpage.clickSearch();

			int count = this.searchSylkpage.getTotalResultCount();
			if (count == 0) {
				reporter.assertThisAsFail("When searched for RNAi for this user="
						+ columns.get("user") + " resulted in zero results");
			} else {
				reporter.reportPass("When searched for RNAi for this user="
						+ columns.get("user") + " displays " + count
						+ " results");
			}

			// step 4
			RNAiPage rnaiPage = (RNAiPage) this.searchSylkpage
					.clickAndOpenRNAiSearch();
			reporter.verifyEqual(rnaiPage.getPageTitle(),
					PageTitles.rnai_page_title_page,
					"From the search result click on one of the RNAi link and open the RNAi Page");

			// step 5
			rnaiPage = rnaiPage.clickOnDetailTab();

			reporter.reportPass("Click on detail tab open up the detail tab.");

			String Value = rnaiPage.getCreatedDate();

			boolean dateFormat = RNAiPage
					.checkValidFormat("dd-MMM-yyyy", Value);

			reporter.verifyTrue(dateFormat,
					"Date created Must have the following format DD-MON-YYYY");

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
