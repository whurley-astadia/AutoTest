package com.syngenta.sylk.rnai.extendtabbedui;

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

public class CheckEditButtonEnabledInDetailTabTest {

	private List<Object[]> testData = new ArrayList<Object[]>();

	private LandingPage lp;
	private HomePage homepage;
	private SearchSylkPage searchSylkpage;

	@BeforeClass(alwaysRun = true)
	public void loadData() {
		this.testData = new CommonLibrary()
				.getTestDataAsObjectArray("Check_EditButton_Enabled_DetailTab.xlsx");
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

	@DataProvider(name = "checkEditButtonEnabled", parallel = false)
	public Iterator<Object[]> loadTestData() {

		return this.testData.iterator();
	}

	@AfterMethod(alwaysRun = true)
	public void cleanUp() {

		if (this.lp != null) {
			this.lp.driverQuit();
		}
	}
	@Test(enabled = true, description = "Check Edit Button enabled in detail Tab in tabbed view RNAi ", dataProvider = "checkEditButtonEnabled", groups = {
			"rNaiConstructTabInTabbedView", "RNAI", "regression"})
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
			reporter.verifyEqual(
					rnaiPage.getPageTitle(),
					PageTitles.rnai_page_title_page,
					"From the search result click on RNAi link and open a RNAi which has at least one evidence.");

			// step 5
			rnaiPage = rnaiPage.clickOnDetailTab();

			reporter.reportPass("Click on detail tab open up the detail tab.");

			// step 6
			rnaiPage.clickEdit();

			rnaiPage.selectRNAiTriggerType(columns.get("RNAiTriggerType"));

			rnaiPage.clearText();

			rnaiPage.enterDescription(columns.get("Description"));

			rnaiPage.enterGenieID(columns.get("GenieID"));

			rnaiPage.enterSynonyms(columns.get("Synonyms"));

			rnaiPage.clickSave();

			String errorMsg = "Field is required";
			String actualError = rnaiPage.getValidationErrorName();
			reporter.verifyEqual(actualError, errorMsg,
					"Warning  error message is displayed for mandatory field when left blank");

			rnaiPage.enterName(columns.get("Name"));

			rnaiPage = rnaiPage.clickSave();

			String expectedText = (columns.get("RNAiTriggerType"));
			String actualText = rnaiPage.getRNAiTriggerType();
			reporter.verifyEqual(
					actualText,
					expectedText,
					"The edited  changes have been reflected in detail tab for 'RNAi Trigger Type' field");

			System.out.println(actualText);
			System.out.println(expectedText);

			String expectedText1 = (columns.get("Name"));
			String actualText1 = rnaiPage.getName();
			reporter.verifyEqual(actualText1, expectedText1,
					"The edited  changes have been reflected in detail tab for 'Name field'");

			System.out.println(actualText1);
			System.out.println(expectedText1);

			String expectedText2 = (columns.get("Description"));
			String actualText2 = rnaiPage.getDescription();
			reporter.verifyEqual(actualText2, expectedText2,
					"The edited  changes have been reflected in detail tab for 'Description field'");

			String expectedText3 = (columns.get("GenieID"));
			String actualText3 = rnaiPage.getGenieID();
			reporter.verifyEqual(actualText3, expectedText3,
					"The edited  changes have been reflected in detail tab for 'Genie ID field'");

			String actualText4 = rnaiPage.getSynonyms();
			String expectedText4 = (columns.get("Synonyms"));
			reporter.verifyEqual(actualText4, expectedText4,
					"The edited  changes have been reflected in detail tab for 'Synonyms field'");

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
