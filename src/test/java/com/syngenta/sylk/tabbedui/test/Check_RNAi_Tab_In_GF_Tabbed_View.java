package com.syngenta.sylk.tabbedui.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.AfterClass;
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
import com.syngenta.sylk.main.pages.SyngentaReporter;
import com.syngenta.sylk.menu_add.pages.AddRNAiTriggerPage;
import com.syngenta.sylk.menu_add.pages.BLASTnRnaiResultPage;
import com.syngenta.sylk.menu_add.pages.GeneticFeaturePage;
import com.syngenta.sylk.menu_add.pages.PopUpFlagForCurationPage;
import com.syngenta.sylk.menu_add.pages.RNAiTriggerDetailsPage;
import com.syngenta.sylk.menu_add.pages.SearchTargetGenePage;
import com.syngenta.sylk.menu_find.pages.RNAiPage;
import com.syngenta.sylk.menu_find.pages.SearchSylkPage;

public class Check_RNAi_Tab_In_GF_Tabbed_View {

	private LandingPage lp;
	private HomePage homepage;
	private GeneticFeaturePage gfPage;
	private SearchSylkPage searchSylkpage;
	private RNAiPage rnai;

	private List<Object[]> testData = new ArrayList<Object[]>();

	@BeforeClass(alwaysRun = true)
	public void loadData() {
		this.testData = new CommonLibrary()
				.getTestDataAsObjectArray("CheckRNAiTabInGF.xlsx");
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
			String gfName = "selenium_GF1";
			// step 6
			// check has to be made to see if its navigated to the
			// "Add New Genetic Feature page"
			gfName = common.isThereASeleniumGF(this.homepage, "Pillai, Nisha");
			if (StringUtils.isBlank(gfName)) {
				gfName = "selenium_GF1";
				this.homepage = common.addANewGeneticFeatureProtein(
						this.homepage, columns, gfName);
			}

			reporter.reportPass("Create a genetic feature");
			/*
			 * Add RNAi
			 * 
			 * Attach the genetic feature that was added above to this RNAi
			 */
			String rnaiTriggerName = "selenium_rnai";
			this.rnai.deleteThisRNAi(this.homepage, rnaiTriggerName,
					"Pillai, Nisha");

			BasePage page = null;
			AddRNAiTriggerPage addRnaiPage = this.homepage
					.goToRNAiTriggerPage();
			reporter.verifyEqual(addRnaiPage.getPageTitle(),
					PageTitles.add_rnai_trigger_page_title,
					"Select menu item 'RNAi Trigger' and open RNAi Trigger page.");

			addRnaiPage.enterSequence(columns.get("sequence"));
			BLASTnRnaiResultPage blastPage = addRnaiPage.clickDuplicateCheck();

			reporter.verifyEqual(
					blastPage.getPageTitle(),
					PageTitles.rnai_blast_search_result_page_title,
					"Click on duplicate check button after entering sequence number on RNAi page and Blast RNAi Search Result page opens up.");

			page = blastPage.clickAddAsNewRNAITriggerButton();
			RNAiTriggerDetailsPage rnaiTriggerDetailsPage = null;
			if (page instanceof PopUpFlagForCurationPage) {
				PopUpFlagForCurationPage popup = (PopUpFlagForCurationPage) page;
				popup.enterRationaleRNAi("test");
				rnaiTriggerDetailsPage = (RNAiTriggerDetailsPage) popup
						.clickContinueAndGoToNewGFPage();
				reporter.reportPass("Add test to rational PopUp for Flag For Curation Page, if it appears");
			} else {
				rnaiTriggerDetailsPage = (RNAiTriggerDetailsPage) page;

				reporter.verifyEqual(rnaiTriggerDetailsPage.getPageTitle(),
						PageTitles.rnai_trigger_details_page_title_page,
						"Click on Add new RNAi trigger button and RNAI Trigger details page opens up.");

			}
			SearchTargetGenePage search = rnaiTriggerDetailsPage
					.clickOnSelectTargetGeneButton();
			reporter.reportPass("Click on select target gene button and search page opens up.");
			search.selectUser("Pillai, Nisha");
			search = search.clickSearch();
			rnaiTriggerDetailsPage = search.clickAddGeneForRNAi();
			reporter.reportPass("Add a GF to this RNAi as a target gene");
			rnaiTriggerDetailsPage.selectRNAiTriggerType("Antisense RNA");

			rnaiTriggerDetailsPage.enterName(rnaiTriggerName);

			RNAiPage rnaiPage = rnaiTriggerDetailsPage.clickAddRNAiTrigger();

			reporter.verifyEqual(rnaiPage.getPageTitle(),
					PageTitles.rnai_page_title_page,
					"RNAi trigger created successfully.");

			// step 5

			this.searchSylkpage = this.homepage.goToGFRNAiTriggerROIpromoter();

			reporter.verifyEqual(this.searchSylkpage.getPageTitle(),
					PageTitles.search_sylk_page_title,
					"Select menu item 'GF/RNAi Triggers/ROI/Promoter' and open Search page.");

			// step 6
			this.searchSylkpage.selectAddedBy("Pillai, Nisha");

			this.searchSylkpage.selectType("Genetic Feature");

			this.searchSylkpage = this.searchSylkpage.clickSearch();
			this.gfPage = (GeneticFeaturePage) this.searchSylkpage
					.clickAndOpenThisGF(gfName);

			this.gfPage = this.gfPage.clickOnRnaiTab();

			reporter.verifyEqual(
					this.gfPage.getPageTitle(),
					PageTitles.genetic_feature_page_title,
					"Open the added genetic feature and click on RNAI tab and RNAI tab becomes active");

			HashMap<String, String> headers = this.gfPage
					.getAllColumnHeadersInRNAITab();
			System.out.println("column headers:" + headers);
			reporter.verifyEqual("" + headers.size(), "7",
					"RNAI tab has seven column headers");
			reporter.reportPass("The following headers are displayes : "
					+ headers.toString());
			this.rnai.deleteThisRNAi(this.homepage, rnaiTriggerName,
					"Pillai, Nisha");

		} catch (Exception e) {
			e.printStackTrace();
			throw new SyngentaException("Test Failed:"
					+ new CommonLibrary().getStackTrace(e));
		} finally {
			reporter.assertAll();
		}
	}
}
