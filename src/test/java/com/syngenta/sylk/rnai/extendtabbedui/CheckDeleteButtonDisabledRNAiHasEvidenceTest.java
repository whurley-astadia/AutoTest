package com.syngenta.sylk.rnai.extendtabbedui;

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
import com.syngenta.sylk.main.pages.HomePage;
import com.syngenta.sylk.main.pages.LandingPage;
import com.syngenta.sylk.main.pages.SyngentaReporter;
import com.syngenta.sylk.menu_add.pages.CreateLiteratureEvidenceDetailsForRNAiPage;
import com.syngenta.sylk.menu_add.pages.LiteratureSearchPage;
import com.syngenta.sylk.menu_add.pages.PopUpAddRNAiTrigger;
import com.syngenta.sylk.menu_find.pages.RNAiPage;
import com.syngenta.sylk.menu_find.pages.SearchSylkPage;

public class CheckDeleteButtonDisabledRNAiHasEvidenceTest {

	private LandingPage lp;
	private HomePage homepage;
	private SearchSylkPage searchSylkpage;

	private List<Object[]> testData = new ArrayList<Object[]>();

	@BeforeClass(alwaysRun = true)
	public void loadData() {
		this.testData = new CommonLibrary()
				.getTestDataAsObjectArray("Check_DeleteButton_Disabled_RNAiNomEvidence.xlsx");
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

	@Test(enabled = true, description = "Check Delete Button disabled  tabbed view RNAi (RNAi has Evidence attached) ", dataProvider = "testData", groups = {
			"CheckDeleteButtonDisabledRNAiHasEvidenceTest", "RNAI",
			"regression"})
	public void rNAiConstructTabInTabbedView(String testDescription,
			String row_num, HashMap<String, String> columns) {

		SyngentaReporter reporter = new SyngentaReporter();

		// step1

		reporter.reportPass("Login to SyLK");

		// Step 2
		this.searchSylkpage = this.homepage.goToGFRNAiTriggerROIpromoter();

		reporter.verifyEqual(this.searchSylkpage.getPageTitle(),
				PageTitles.search_sylk_page_title,
				"Select menu item 'GF/RNAi Triggers/ROI/Promoter' and open Search page.");
		// step 3
		this.searchSylkpage.selectAddedBy(columns.get("user"));

		this.searchSylkpage.selectType("RNAi");

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
				.clickAndOpenRNAiWithNoConstruct();
		reporter.reportPass("From search result page click on a RNAi trigger with no construct.");

		rnaiPage.clickOnEvidenceTab();
		int evidenceCount = rnaiPage.getEvidenceCountOnTab();
		if (evidenceCount == 0) {
			rnaiPage.clickOnEvidenceTab();
			LiteratureSearchPage litPage = rnaiPage
					.selectAddEvidence("literature");
			CreateLiteratureEvidenceDetailsForRNAiPage litEvidCreatePage = (CreateLiteratureEvidenceDetailsForRNAiPage) litPage
					.searchThis(columns.get("search"));
			litEvidCreatePage.enterRationale("test");
			litEvidCreatePage.enterObservation("test");
			PopUpAddRNAiTrigger popUpRNAi = litEvidCreatePage.clickAddRNAi();
			popUpRNAi.enterText(columns.get("trait"));
			litEvidCreatePage = popUpRNAi.clickAdd();
			rnaiPage = litEvidCreatePage.clickSave();
		}

		evidenceCount = rnaiPage.getEvidenceCountOnTab();
		reporter.reportPass("if RNAi does not have evidences add one or more Evidence. Number of evidence with this RNAi trigger = "
				+ evidenceCount);
		boolean flag = rnaiPage.isDeleteRNAiButtonEnabled();
		reporter.verifyTrue(
				!flag,
				"With no construct and evience tab having "
						+ evidenceCount
						+ " evidence(s) 'Delete This RNAi Trigger' button should be disabled");
		String expectedToolTip = "Cannot delete an RNAi With Evidence attached";
		String tooTip = rnaiPage.getTooTipOnDeleteRNAiButton();

		reporter.verifyEqual(tooTip, expectedToolTip,
				"Delete RNAi button expected tool tip='" + expectedToolTip
						+ "' and Actual=" + tooTip);
	}
}