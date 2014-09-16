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

public class CheckDeleteButtonDisabledRNAiNominatedToConstructTest {

	private LandingPage lp;
	private HomePage homepage;
	private SearchSylkPage searchSylkpage;

	private List<Object[]> testData = new ArrayList<Object[]>();

	@BeforeClass(alwaysRun = true)
	public void loadData() {
		this.testData = new CommonLibrary()
				.getTestDataAsObjectArray("Check_DeleteButton_Disabled_RNAiNomConstruct.xlsx");
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

	@DataProvider(name = "testData", parallel = false)
	public Iterator<Object[]> loadTestData() {

		return this.testData.iterator();
	}

	@AfterMethod(alwaysRun = true)
	public void cleanUp() {

		if (this.lp != null) {
			this.lp.driverQuit();
		}
	}
	@Test(enabled = true, description = "Check Edit Button disabled in details Tab in tabbed view RNAi ", dataProvider = "testData", groups = {
			"checkEditButtondisabledindetailTab", "RNAI", "regression"})
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
						+ (columns.get("user") + " resulted in zero results"));
			} else {
				reporter.reportPass("When searched for RNAi for this user="
						+ (columns.get("user") + " displays " + count + " results"));
			}

			RNAiPage rnaiPage = (RNAiPage) this.searchSylkpage
					.clickAndOpenRNAiWithNominatedConstruct();
			int constructCount = rnaiPage.getConstructCountOnTab();
			reporter.verifyEqual(
					rnaiPage.getPageTitle(),
					PageTitles.rnai_page_title_page,
					"Open RNAi Trigger Tabbed Page with at least one construct from the search results. Total constructs on this RNAi = "
							+ constructCount);

			rnaiPage.clickOnEvidenceTab();
			int evidenceCount = rnaiPage.getEvidenceCountOnTab();

			rnaiPage = rnaiPage.deleteAllEvdence();

			if (evidenceCount == 0) {
				reporter.reportPass("Check evidence tab and there should be no evidence attached to this RNAi.");
			} else {
				reporter.reportPass("Delete all existing evidence on this RNAi. Found "
						+ evidenceCount + " evidence and deleted them.");
			}

			boolean flag = rnaiPage.isDeleteRNAiButtonEnabled();

			reporter.verifyTrue(
					!flag,
					"Delete all evidence and with contruct tab having "
							+ constructCount
							+ " constructs 'Delete This RNAi Trigger' button should be disabled");
			String expectedToolTip = "This Trigger cannot be deleted as it is nominated as a Construct";
			String tooTip = rnaiPage.getTooTipOnDeleteRNAiButton();

			reporter.verifyEqual(tooTip, expectedToolTip,
					"Delete RNAi button expected tool tip='" + expectedToolTip
							+ "' and Actual=" + tooTip);

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
