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

public class Check_DeleteButtonDisabledInGF_WithEvidence {

	private LandingPage lp;
	private HomePage homepage;
	private SearchSylkPage searchSylkpage;

	private List<Object[]> testData = new ArrayList<Object[]>();

	@BeforeClass(alwaysRun = true)
	public void loadData() {
		this.testData = new CommonLibrary()
				.getTestDataAsObjectArray("Check_DeleteButtonDisabledInGF_WithEvidence.xlsx");
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
	@Test(enabled = true, description = "Check Delete Button disabled in tabbed view GF (GF with Evidence cannot be deleted) ", dataProvider = "testData", groups = {
			"Check_DeleteButtonDisabledInGF_WithEvidence", "GF", "regression"})
	public void rNAiConstructTabInTabbedView(String testDescription,
			String row_num, HashMap<String, String> columns) {

		SyngentaReporter reporter = new SyngentaReporter();
		CommonLibrary common = new CommonLibrary();
		try {

			reporter.reportPass("Login to SyLK");
			String gfName = "selenium_GF1";
			String user = "Pillai, Nisha";
			// step 6
			// check has to be made to see if its navigated to the
			// "Add New Genetic Feature page"
			GeneticFeaturePage gfPage = common.searchAndSelectThisGF(

			this.homepage, user, gfName);
			if (gfPage == null) {
				gfName = "selenium_GF1";
				this.homepage = common.addANewGeneticFeatureProtein(
						this.homepage, columns, gfName);
				gfPage = this.homepage.clickNewGeneticFeatureLink(gfName);
			}

			reporter.reportPass("Open a genetic feature");
			int evdCount = gfPage.getEvidenceSequenceCountOnTab();
			if (evdCount == 0) {
				gfPage = gfPage.addEvidenceInSequenceSection(gfPage, columns);
			}
			reporter.reportPass("Check that the GF has one evidence associated to it, if not add at least one evidence to GF");

			boolean enabled = gfPage.isDeleteThisGFButtonEnabled();
			reporter.verifyTrue(
					!enabled,
					"With an evidence present In GF tabbed view, delete button at the bottom of the page under the sequence section is disabled");
			String toolTip = gfPage.getDeleteButtonToolTip();

			reporter.verifyEqual(toolTip,
					"Some sequences have associated evidences.",
					"a pop-up with \"some sequences have associated evidences\" message appears");
			gfPage = gfPage.clickOnEvidenceSequenceTab();
			ViewLiteratureEvidenceDetailsPageSequence viewLit = gfPage
					.clickviewLiteratureEvidenceSequence();
			gfPage = viewLit.clickOnDelete();

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
