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
import com.syngenta.sylk.libraries.SyngentaException;
import com.syngenta.sylk.main.pages.HomePage;
import com.syngenta.sylk.main.pages.LandingPage;
import com.syngenta.sylk.main.pages.SyngentaReporter;
import com.syngenta.sylk.menu_add.pages.GeneticFeaturePage;
import com.syngenta.sylk.menu_add.pages.ViewLiteratureEvidenceDetailsPageSequence;
import com.syngenta.sylk.menu_find.pages.SearchSylkPage;

public class Check_Delete_Button_Enabled_In_Tabbed_View_GF {

	private LandingPage lp;
	private HomePage homepage;
	private SearchSylkPage searchSylkpage;

	private List<Object[]> testData = new ArrayList<Object[]>();

	@BeforeClass(alwaysRun = true)
	public void loadData() {
		this.testData = new CommonLibrary()
				.getTestDataAsObjectArray("Check_Delete_Button_Enabled_In_Tabbed_View_GF.xlsx");
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
	@Test(enabled = true, description = "Check Delete Button enabled in tabbed view GF. Make sure the GF name appears striked out in search result after we delete the GF ", dataProvider = "testData", groups = {
			"Check_Delete_Button_Enabled_In_Tabbed_View_GF", "GF", "regression"})
	public void rNAiConstructTabInTabbedView(String testDescription,
			String row_num, HashMap<String, String> columns) {

		SyngentaReporter reporter = new SyngentaReporter();
		CommonLibrary common = new CommonLibrary();
		try {

			reporter.reportPass("Login to SyLK");
			String gfName = "gf_selenium_to_delete";
			String user = "Pillai, Nisha";
			// step 6
			// check has to be made to see if its navigated to the
			// "Add New Genetic Feature page"
			GeneticFeaturePage gfPage = common.searchAndSelectThisGF(

			this.homepage, user, gfName);
			if (gfPage == null) {
				gfName = "gf_selenium_to_delete";
				this.homepage = common.addANewGeneticFeatureProtein(
						this.homepage, columns, gfName);
				gfPage = this.homepage.clickNewGeneticFeatureLink(gfName);
			}

			reporter.reportPass("Open a genetic feature");
			int evdCount = gfPage.getEvidenceSequenceCountOnTab();
			if (evdCount != 0) {
				gfPage = gfPage.clickOnEvidenceSequenceTab();
				ViewLiteratureEvidenceDetailsPageSequence viewLit = gfPage
						.clickviewLiteratureEvidenceSequence();
				gfPage = viewLit.clickOnDelete();
			}
			reporter.reportPass("Check that the GF has no evidence associated to it, if there is any evidence, delete all associated evidence");

			boolean enabled = gfPage.isDeleteThisGFButtonEnabled();
			reporter.verifyTrue(
					enabled,
					"In GF tabbed view, check the existence of the delete button at the bottom of the page under the sequence section and that it is enabled");

			gfPage.clickDeleleThisGeneticFeature();

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
