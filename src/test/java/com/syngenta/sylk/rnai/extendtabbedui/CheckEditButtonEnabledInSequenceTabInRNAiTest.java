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

public class CheckEditButtonEnabledInSequenceTabInRNAiTest {

	private LandingPage lp;
	private HomePage homepage;
	private SearchSylkPage searchSylkpage;

	private List<Object[]> testData = new ArrayList<Object[]>();

	@BeforeClass(alwaysRun = true)
	public void loadData() {
		this.testData = new CommonLibrary()
				.getTestDataAsObjectArray("check_EditButtton_Enabled_SequenceTab.xlsx");
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
	@Test(enabled = true, description = "Check Edit Button enabled in sequence Tab in tabbed view RNAi ", dataProvider = "testData", groups = {
			"CheckEditButtonEnabledInSequenceTabInRNAi", "RNAI", "regression"})
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

			// step 4
			RNAiPage rnaiPage = (RNAiPage) this.searchSylkpage
					.clickAndOpenRNAiSearch();
			reporter.verifyEqual(rnaiPage.getPageTitle(),
					PageTitles.rnai_page_title_page,
					"From the search result click on one of the RNAi link and open the RNAi Page");

			// step 5
			rnaiPage = rnaiPage.clickOnSequenceTab();

			boolean editenabled = rnaiPage.isEditButtonUnderDetailTabEnabled();

			reporter.verifyTrue(
					editenabled,
					"Edit Button is enabled in 'sequence Tab' in tabbed view RNAi created by same user.");

			// step 6

			rnaiPage.clickEdit();
			boolean saveEnabled = rnaiPage
					.isSaveButtonBeneathEditableFieldEnabled();

			reporter.verifyTrue(
					saveEnabled,
					"The different fields of the details tab are editable, and a save button is enabled.");

			// step 7
			boolean AntisenseSeqEditable = rnaiPage
					.isAntisenseSequenceEditable();
			reporter.verifyTrue(!AntisenseSeqEditable,
					"The field 'Anti-sense Sequence or Mature miRNA sequence'is not editable.");

			// step 7

			rnaiPage.enterEntireRNAiTriggerSequence(columns
					.get("shortsequence"));
			rnaiPage.clickSave();

			String errorMsg = "Invalid sequence: sequence is too short";
			String actualError = rnaiPage.getValidationError();
			reporter.verifyEqual(
					actualError,
					errorMsg,
					"Warning  error message : '"
							+ errorMsg
							+ "'  is displayed for 'Entire RNAi Trigger Sequence' when short sequence is entered ");

			// step 8
			String actualText = rnaiPage.getTextInEntireRNAiTriggerTextbox();
			reporter.verifyEqual(
					actualText,
					columns.get("shortsequence"),
					"The field 'Entire RNAi Trigger Sequence' in sequence tab on RNAi page is uneditable.",
					"The field 'Entire RNAi Trigger Sequence' in sequence tab on RNAi page is editable.");

			rnaiPage.enterEntireRNAiTriggerSequence(columns
					.get("expectedLongtext"));
			rnaiPage = rnaiPage.clickSave();
			String actuallongtext = rnaiPage
					.getTextInEntireRNAiTriggerReadOnlyText();

			reporter.verifyEqual(
					actuallongtext,
					columns.get("expectedLongtext"),
					"The field 'Entire RNAi Trigger Sequence' got edited and saved when long valid sequence is entered.");

			// step 9
			rnaiPage = rnaiPage.clickEdit();
			rnaiPage.enterAccessionNo();
			rnaiPage = rnaiPage.clickSave();

			String errorMessage = "Field is required";
			String actualErrorMsg = rnaiPage.getValidationErrorAccession();
			reporter.verifyEqual(
					actualErrorMsg,
					errorMessage,
					"Warning  error message : '"
							+ errorMessage
							+ "'  is displayed when 'Accession no' is left blank. ");

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
