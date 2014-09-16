package com.syngenta.sylk.gf.extendtabbedui;

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
import com.syngenta.sylk.main.pages.BasePage;
import com.syngenta.sylk.main.pages.HomePage;
import com.syngenta.sylk.main.pages.LandingPage;
import com.syngenta.sylk.main.pages.SyngentaReporter;
import com.syngenta.sylk.menu_add.pages.AddNewGeneticFeaturePage;
import com.syngenta.sylk.menu_add.pages.AddNewROIPage;
import com.syngenta.sylk.menu_add.pages.GeneticFeaturePage;
import com.syngenta.sylk.menu_add.pages.ROIDetailPage;
import com.syngenta.sylk.menu_add.pages.SearchTargetGenePage;
import com.syngenta.sylk.menu_find.pages.SearchSylkPage;

public class CheckROITabInGFTest {

	private AddNewGeneticFeaturePage addNewGFPage;
	private LandingPage lp;
	private HomePage homepage;
	private GeneticFeaturePage gfPage;
	private String proteinSymbol;
	private SearchSylkPage searchSylkpage;

	private List<Object[]> testData = new ArrayList<Object[]>();

	@BeforeClass(alwaysRun = true)
	public void loadData() {
		this.testData = new CommonLibrary()
				.getTestDataAsObjectArray("CheckROITabInGF.xlsx");
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
			"addNewGeneticFeatureProtein", "geneticfeature", "regression"})
	public void addNewGeneticFeatureProtein(String testDescription,
			String row_num, HashMap<String, String> columns) {

		SyngentaReporter reporter = new SyngentaReporter();
		CommonLibrary common = new CommonLibrary();

		reporter.reportPass("Login to SyLK");
		String gfName = "selenium_GF1";
		// step 6
		// check has to be made to see if its navigated to the
		// "Add New Genetic Feature page"
		gfName = common.isThereASeleniumGF(this.homepage, "Pillai, Nisha");
		if (StringUtils.isBlank(gfName)) {
			gfName = "selenium_GF1";
			this.homepage = common.addANewGeneticFeatureProtein(this.homepage,
					columns, gfName);
		}

		reporter.reportPass("Create a genetic feature");

		// String rnaiTriggerName = "selenium_rnai";
		// this.homepage.deleteThisRNAi(this.homepage, rnaiTriggerName,
		// "Pillai, Nisha");

		BasePage page = null;
		AddNewROIPage addnewROIPage = this.homepage.goToAddNewROIPage();
		reporter.verifyEqual(addnewROIPage.getPageTitle(),
				PageTitles.add_New_ROI_Page_title,
				"Select menu item 'Region Of Interest' and open page.");

		// Add ROI
		SearchTargetGenePage searchTargetgenePage = addnewROIPage
				.clickAddGeneToROI();

		searchTargetgenePage.selectAddedBy(columns.get("addedBy"));

		searchTargetgenePage = searchTargetgenePage.clickSearch();

		addnewROIPage = searchTargetgenePage.clickAddGeneForROI();

		addnewROIPage.enterSourceSpecies(columns.get("source_species"));
		addnewROIPage.enterLine(columns.get("line"));
		addnewROIPage.selectReferenceGenome(columns.get("reference_genome"));
		addnewROIPage.enterChromosome(columns.get("Chromosome"));
		addnewROIPage.enterFrom(columns.get("Chromosome_from"));
		addnewROIPage.enterTo(columns.get("Chromosome_to"));

		addnewROIPage.selectRegionOfInterestType(columns
				.get("Region_Of_Interest_Type"));
		addnewROIPage.enterSNPs(columns.get("snps"));

		ROIDetailPage roiDetailspage = addnewROIPage.ClickAddRegionOfInterest();
		SearchSylkPage searchSylkPage = roiDetailspage
				.goToGFRNAiTriggerROIpromoter();
		searchSylkPage.selectAddedBy(columns.get("addedBy"));
		searchSylkPage.selectType(columns.get("type"));
		searchSylkPage.clickSearch();

	}
}