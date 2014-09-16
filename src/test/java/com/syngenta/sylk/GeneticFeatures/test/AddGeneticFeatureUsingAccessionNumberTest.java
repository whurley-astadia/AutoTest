package com.syngenta.sylk.GeneticFeatures.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
import com.syngenta.sylk.main.pages.BasePage;
import com.syngenta.sylk.main.pages.HomePage;
import com.syngenta.sylk.main.pages.LandingPage;
import com.syngenta.sylk.main.pages.MenuPage;
import com.syngenta.sylk.main.pages.SyngentaReporter;
import com.syngenta.sylk.menu_add.pages.AddNewGeneticFeaturePage;
import com.syngenta.sylk.menu_add.pages.CreateLiteratureEvidenceDetailsForGeneticFeaturePage;
import com.syngenta.sylk.menu_add.pages.DuplicateSearchResultPage;
import com.syngenta.sylk.menu_add.pages.GeneticFeaturePage;
import com.syngenta.sylk.menu_add.pages.GeneticFeatureSearchResultsPage;
import com.syngenta.sylk.menu_add.pages.ImportGeneticFeatureDetailPage;
import com.syngenta.sylk.menu_add.pages.ImportGeneticFeaturePage;
import com.syngenta.sylk.menu_add.pages.LiteratureDetailPage;
import com.syngenta.sylk.menu_add.pages.NCBIPage;
import com.syngenta.sylk.menu_add.pages.PopUpAddGeneSymbol;
import com.syngenta.sylk.menu_add.pages.PopUpAddTraitComponent;
import com.syngenta.sylk.menu_add.pages.PopUpProteinSequenceDataPage;
import com.syngenta.sylk.menu_add.pages.RelatedLiteraturePage;
import com.syngenta.sylk.menu_add.pages.ViewLiteratureEvidenceDetailsGF;

public class AddGeneticFeatureUsingAccessionNumberTest {

	private AddNewGeneticFeaturePage addNewGFPage;
	private LandingPage lp;
	private HomePage homepage;
	private GeneticFeaturePage gfPage;
	private String newGFlink;

	private List<Object[]> testData = new ArrayList<Object[]>();

	@BeforeClass(alwaysRun = true)
	public void loadData() {
		this.testData = new CommonLibrary()
				.getTestDataAsObjectArray("Add_GF_AccessionNo.xlsx");
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

	@DataProvider(name = "gfAccessionNoadd", parallel = false)
	public Iterator<Object[]> loadTestData() {

		return this.testData.iterator();
	}

	@AfterMethod(alwaysRun = true)
	public void cleanUp() {

		try {
			MenuPage menu = this.addNewGFPage.getMenuPage();
			HomePage home = menu.gotoHomePage();
			GeneticFeaturePage gfPage = home
					.clickNewGeneticFeatureLink(this.newGFlink);
			gfPage.clickDeleleThisGeneticFeature();
		} catch (Exception e) {
			System.out.println("Could not delete GF..");

		}

		if (this.lp != null) {
			this.lp.driverQuit();
		}
	}

	@Test(enabled = true, description = "This test will verify the functionality of SyLK application while creating Genetic Feature using  "
			+ "Accession Numbers", dataProvider = "gfAccessionNoadd", groups = {
			"addNewGeneticFeatureProtein", "geneticfeature", "regression"})
	public void addNewGeneticFeatureAssessionNumber(String testDescription,
			String row_num, HashMap<String, String> columns) {

		/*
		 * Step 1 : Login and navigate to 'Add New Genetic Feature page'
		 */

		SyngentaReporter reporter = new SyngentaReporter();
		try {
			this.newGFlink = columns.get("newGFlink");
			reporter.reportPass("Login to SyLK");

			// step 6
			// check has to be made to see if its navigated to the
			// "Add New Genetic Feature page"
			this.addNewGFPage = this.homepage.goToAddGeneticFeaturePage();
			reporter.verifyEqual(this.addNewGFPage.getPageTitle(),
					PageTitles.add_new_genetic_feature_page_title,
					"Open'Add New Genetic Feature Page'");

			this.newGFlink = columns.get("newGFlink");

			// Step 2 : Select Gene from dropdown
			this.addNewGFPage.selectGeneType(columns.get("gene_type"));

			/*
			 * Step 3 & 4 : Paste access_number into the sequence textarea
			 */

			this.addNewGFPage.enterTextInSequence(columns.get("accessionNo"));

			boolean isSequenceTypeEnabled = this.addNewGFPage
					.isSequenceTypeEnabled();
			reporter.assertTrue(isSequenceTypeEnabled,
					"Entering a valid accession_number should auto select accesstion radio button.");

			// Step 5 : Select Protein radio button
			this.addNewGFPage.clickSequanceType(columns.get("protein"));

			/*
			 * Step 6: Click on clear
			 */
			/**** replace assert with Verify ****/
			this.addNewGFPage.clickClear();
			String sequenceText = this.addNewGFPage.getTextInSequence();

			reporter.verifyEqual(
					sequenceText,
					null,
					"Sequence text field not cleared after clicking clear button.",
					"Sequence text field cleared correctly after clicking clear button");

			boolean enabled = this.addNewGFPage.isSequenceTypeEnabled();

			reporter.verifyTrue(
					!enabled,
					"Sequence type radio button enabled even after clicking clear button.",
					"Click on clear and sequence type radio buttons get disabled.");

			/*
			 * Step 7 & 8: Enter accession number with invalid chars and space
			 * and click on FindMatch
			 */

			String temp_data = "   %^%^%^%^%^    "
					+ (columns.get("accessionNo")) + "  ";

			this.addNewGFPage.enterTextInSequence(temp_data);
			this.addNewGFPage.clickFindMatches();
			String actualErrorMessage = this.addNewGFPage.getValidationerror();
			String expectedErroMesssage = "The value entered is not a valid accession number";

			reporter.verifyEqual(actualErrorMessage, expectedErroMesssage,
					"Did not get the expected error message.",
					"Proper error message displayed when invalid accesstion number is entered.");

			/*
			 * Step 9 : Clear out the spaces from the accession number and add a
			 * parenthesis at the end of it. Click on the Find Matches button.
			 */

			this.addNewGFPage.clickClear();

			temp_data = (columns.get("accessionNo")) + ")";

			this.addNewGFPage.enterTextInSequence(temp_data);
			this.addNewGFPage.clickFindMatches();
			actualErrorMessage = null;
			actualErrorMessage = this.addNewGFPage.getValidationerror();
			expectedErroMesssage = "The value entered is not a valid accession number";

			reporter.verifyEqual(actualErrorMessage, expectedErroMesssage,
					"Did not get the expected error message.",
					"Proper error message displayed when invalid accesstion number is entered.");

			// clear again
			this.addNewGFPage.clickClear();

			/*
			 * I see this step in QTP
			 * 
			 * enter following value as access_number and click on FindMatch
			 * Mid(Parameter("Accession_Number"),1,1) & "   "
			 * &Right(Parameter("Accession_Number"),
			 * Len(Parameter("Accession_Number")) - 1) basically substring of
			 * access_number with three spaces in between them
			 * 
			 * Checkpoint : The value entered is not a valid accession number
			 * Make sure error message with the message 'The value entered is
			 * not a valid accession number' is displayed
			 */
			/********* replace assert with Verify *********/

			temp_data = null;
			temp_data = StringUtils.substring(columns.get("accessionNo"), 1, 2)
					+ "   " + StringUtils.right(columns.get("accessionNo"), 2);

			this.addNewGFPage.enterTextInSequence(temp_data);
			this.addNewGFPage.clickFindMatches();
			actualErrorMessage = null;
			actualErrorMessage = this.addNewGFPage.getValidationerror();
			expectedErroMesssage = null;
			expectedErroMesssage = "The value entered is not a valid accession number";

			reporter.verifyEqual(actualErrorMessage, expectedErroMesssage,
					"Did not get the expected error message.",
					"Proper error message displayed when invalid accesstion number is entered.");

			// Clear again
			this.addNewGFPage.clickClear();

			/*
			 * Step 10 : Remove the invalid characters and click on the Find
			 * Matches button.
			 */
			// temp_data = this.accessionNo;

			this.addNewGFPage.enterTextInSequence(columns.get("accessionNo"));

			BasePage page = this.addNewGFPage.clickFindMatches();
			if (page instanceof GeneticFeaturePage) {
				throw new SkipException(
						"The data being used in this test already exists in the system. This test will delete this data now for future runs and mark this test as SKIPPED.");
			}
			DuplicateSearchResultPage duplicatesearchResultPage = (DuplicateSearchResultPage) page;
			/*
			 * Step 11 : Click on the Modify Search button.
			 */
			this.addNewGFPage = duplicatesearchResultPage.clickModify();

			/*
			 * Step 12 : Without making any changes, click on the Find Matches
			 * button again.
			 */
			// get the returned basepage
			page = this.addNewGFPage.clickFindMatches();
			// if the user is currently on DuplicateSearchResultPage i.e if
			// DuplicateSearchResultPage is returned
			if (page instanceof DuplicateSearchResultPage) {
				GeneticFeatureSearchResultsPage gfSearchResultsPage = null;
				duplicatesearchResultPage = (DuplicateSearchResultPage) page;

				/*
				 * Step 13 : Click on the Search External Databases button.
				 */

				page = null;
				page = duplicatesearchResultPage.clickExternalDatabase();

				// if the user is currently on DuplicateSearchResultPage i.e if
				// DuplicateSearchResultPage is return
				if (page instanceof DuplicateSearchResultPage) {
					duplicatesearchResultPage = (DuplicateSearchResultPage) page;
					/*
					 * control still on duplicate search results page
					 */
					reporter.assertThisAsFail("Click on External Databases button with Accession Number ="
							+ (columns.get("accessionNo"))
							+ " did not show any matches");

				} else if (page instanceof GeneticFeatureSearchResultsPage) {

					/*
					 * Step 14: If related accession numbers displayed, Click on
					 * the Duplicate Check button.
					 */
					gfSearchResultsPage = (GeneticFeatureSearchResultsPage) page;

					if (gfSearchResultsPage.doesTheHeaderReadNoMatches()) {
						reporter.assertThisAsFail("The test data provided does not show any matches in external database. This test case will be aborted.");
					}

					ImportGeneticFeaturePage importGFPage = gfSearchResultsPage
							.clickOnAnyGeneIdToOpenImportGF();

					List<String> relatedAccessionNumbers = importGFPage
							.getRelatedAccessionNumbers();
					String accessionNumber = importGFPage.getAccessionNumber();

					System.out.println("relatedAccessionNumbers:::"
							+ relatedAccessionNumbers.get(0));
					System.out.println("accessionNumber::::" + accessionNumber);
					reporter.reportPass("Search result matching assertion nnumber should be displayed. Assertion number = "
							+ accessionNumber
							+ ", RelatedAccessionNumbers="
							+ relatedAccessionNumbers.get(0));
					ImportGeneticFeatureDetailPage importGFDetailPage = importGFPage
							.clickDuplicate();

					HashMap<String, String> details = importGFDetailPage
							.getGenticFeatureDetails();
					reporter.reportPass("Details : Accession Number="
							+ details.get("accession") + ", Symbol="
							+ details.get("symbol"));
					// expand blast

					// Step 15

					// check for the Main blast results open up on click.

					importGFDetailPage.expandColapseResultForBlast();

					boolean elementPresent = importGFDetailPage
							.isMainBlastExpanded();

					reporter.verifyTrue(elementPresent,
							"Click on Blast failed,  blast did not open up",
							"Click on Blast open up the blast");

					importGFDetailPage.expandBlastp();

					importGFDetailPage.expandtBlastn();

					// check for the All three matches counts.
					// importGFDetailPage.checkAllThreeMatches();

					// step 17

					importGFDetailPage.expandColapseResultForBlast();

					importGFDetailPage.clickExpandtheNCBIRelatedSeq();
					NCBIPage ncbiPage = importGFDetailPage
							.clickOnProteinLinkUnderRelatedSeq();

					reporter.verifyEqual(ncbiPage.getPageTitle(),
							PageTitles.NCBI_Page_title,
							"NCBI page opens up on clicking the protein link");

					importGFDetailPage = ncbiPage
							.closeNCBIWindowAndGoToImportGFDetailpage();

					this.homepage = importGFDetailPage
							.clickAddSequenceAsANewGF();

					// Check for the Home page appears even after the GF is
					// importeed into Sylk.

					reporter.verifyEqual(this.homepage.getPageTitle(),
							PageTitles.home_page_title,
							"Sylk home page appears after Genetic Feature is imported into Sylk");

					this.gfPage = this.homepage
							.clickNewGeneticFeatureLink(columns
									.get("newGFlink"));

					reporter.verifyEqual(this.gfPage.getPageTitle(),
							PageTitles.genetic_feature_page_title,
							"Genetic Feature Screen appears showing the Gene summary");

					// step 18

					String textAdd = this.gfPage.getTextFromCDNA();

					reporter.verifyEqual(
							textAdd,
							"[Add]",
							" 'Add' link is shown for CDNA when there is no sequence added. The text shown is : "
									+ textAdd);

					// step 20

					PopUpProteinSequenceDataPage popUpProteinSeqDataPage = this.gfPage
							.clickOnProtein();

					String text = popUpProteinSeqDataPage.getTextFromSeqData();

					// step 21
					this.gfPage = popUpProteinSeqDataPage.clickOnCloseProtein();

					// step 22
					// RelatedLiteraturePage relatedLiteraturepage
					// =gfPage.selectEvidenceTab("Literature Evidence");

					int initialEvidenceCount = this.gfPage
							.getEvidenceCountOnTab();
					this.gfPage.clickEvidenceTab();

					RelatedLiteraturePage relatedLiteraturepage = this.gfPage
							.selectAddEvidence((columns.get("Literature")));
					// click on evidence tab
					// check screen appears showing public literature summarey.

					reporter.assertEqual(
							this.gfPage.getPageTitle(),
							PageTitles.related_literature_page_title,
							"Related Literature Screen appears when 'Literature Evidence' is selected in Add Evidence dropdown ");

					// step23
					LiteratureDetailPage literatureDetailpage = relatedLiteraturepage
							.clickViewDetail();
					// check for literature detail screen appears.
					reporter.verifyEqual(
							literatureDetailpage.getPageTitle(),
							PageTitles.literature_detail_page_title,

							"Literature Detail Screen appears when 'view Details' is clicked from 'Related Literature page'");

					// step 24

					CreateLiteratureEvidenceDetailsForGeneticFeaturePage createLiteratureEviDetailpage = literatureDetailpage
							.clickAssociate();

					// check for
					// CreatLiteratureEvidenceDetailsForGeneticFeaturePage
					// screen appears.
					reporter.verifyEqual(
							this.gfPage.getPageTitle(),
							PageTitles.Create_Literature_Evidence_Details_for_GF_page_title,
							"'Create Literature Evidence Details for GF'  Screen appears when 'Associate' is clicked from 'Related Literature page'");

					// step 25
					createLiteratureEviDetailpage.enterRationale("rationale");
					// check for text is accepted as typed

					createLiteratureEviDetailpage
							.enterObservation("observation");
					// check for text is accepted as typed.

					// step 26
					PopUpAddGeneSymbol popUpGFsymbol = createLiteratureEviDetailpage
							.clickAddGeneticFeatures();
					if (popUpGFsymbol != null) {
						reporter.reportPass("Click on add grnrtic feature on create literature detail page. A Gene symbol popup appears.");
					} else {
						reporter.assertThisAsFail("Click on add genetic feature on create literature detail page. A Gene symbol popup appears.");
					}

					popUpGFsymbol.enterText(columns.get("GFDetails"));
					createLiteratureEviDetailpage = popUpGFsymbol.clickAdd();

					// step 27
					PopUpAddTraitComponent popUpAddtraitComponent = createLiteratureEviDetailpage
							.clickAddTraitComponent();
					if (popUpAddtraitComponent != null) {
						reporter.reportPass("Click on add trait component on create literature detail page. Add trait popup appears.");
					} else {
					}

					createLiteratureEviDetailpage = (CreateLiteratureEvidenceDetailsForGeneticFeaturePage) popUpAddtraitComponent
							.addTrait(columns.get("trait"));

					reporter.verifyEqual(
							createLiteratureEviDetailpage.getPageTitle(),
							PageTitles.Create_Literature_Evidence_Details_for_GF_page_title,
							"Click Add button on Add trait component popup, Create lirerature details page appears.");
					// step 28
					createLiteratureEviDetailpage.selectChangeDirection(columns
							.get("changeDirection"));

					// step 29

					this.gfPage = createLiteratureEviDetailpage.clickSave();
					reporter.verifyEqual(
							this.addNewGFPage.getPageTitle(),
							PageTitles.genetic_feature_page_title,
							"Click on save button on Create literaterature detail page, genetic feature page appears.");

					// step 30
					int currentCountOnEvidenceTab = this.gfPage
							.getEvidenceCountOnTab();
					if (initialEvidenceCount < currentCountOnEvidenceTab) {
						reporter.reportPass("Adding an evidence increased the evidence count on tab from "
								+ initialEvidenceCount
								+ " to "
								+ currentCountOnEvidenceTab);
					} else {
						reporter.verifyThisAsFail("Adding an evidence DID NOT increased the evidence count. Initial count = "
								+ initialEvidenceCount
								+ ", Count after adding evidence = "
								+ currentCountOnEvidenceTab);

					}

					// check for the evidence count increased by 1 in the
					// Genetic Feature page

					// step 31
					NCBIPage ncbipage = this.gfPage
							.clickOnEvidenceStatementLink();

					this.gfPage = ncbipage.closeNCBIWindowAndGoToGFpage();

					// Evidence Detail For Sequence page opens. Literature
					// evidence is listed in the Trait(NCBI page)
					// check for the this page appears when clicked on the
					// 'Evidence Statement link' from Genetic feature page.

					// step 32
					ViewLiteratureEvidenceDetailsGF viewLiteratureEviDetailgf = this.gfPage
							.clickviewLiteratureEvidence();
					this.gfPage = viewLiteratureEviDetailgf.clickBack();
					// Click on the View button next to the literature
					// reference.
					// check for View Literature Reference Evidence page
					// appears. All details are accurately displayed.

					// delete literature evidence.
					viewLiteratureEviDetailgf = this.gfPage
							.clickviewLiteratureEvidence();
					this.gfPage = viewLiteratureEviDetailgf
							.clickDeleteToDeleteThisLiteratureEvidence();

				}

			} else {
				reporter.assertThisAsFail("Click on Find Matches with Accession Number ="
						+ (columns.get("accessionNo"))
						+ " did not open up Duplicate Search Results page or Genetic feature Search Result page.");
			}

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
