package com.syngenta.sylk.LeadProcess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.testng.SkipException;
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
import com.syngenta.sylk.menu_add.pages.ConstructNominationPage;
import com.syngenta.sylk.menu_add.pages.CreateLiteratureEvidenceDetailsForGeneticFeaturePage;
import com.syngenta.sylk.menu_add.pages.DuplicateSearchResultPage;
import com.syngenta.sylk.menu_add.pages.GeneticFeaturePage;
import com.syngenta.sylk.menu_add.pages.GeneticFeatureSearchResultsPage;
import com.syngenta.sylk.menu_add.pages.ImportGeneticFeatureDetailPage;
import com.syngenta.sylk.menu_add.pages.ImportGeneticFeaturePage;
import com.syngenta.sylk.menu_add.pages.LeadNominationPage;
import com.syngenta.sylk.menu_add.pages.LiteratureSearchPage;
import com.syngenta.sylk.menu_add.pages.PopUpAddSequenceAccession;
import com.syngenta.sylk.menu_add.pages.PopUpAddSourceOfLeadFunctionInfo;
import com.syngenta.sylk.menu_add.pages.PopUpAddSuggestedProjectNamePage;
import com.syngenta.sylk.menu_add.pages.PopUpAddTraitComponent;
import com.syngenta.sylk.menu_add.pages.PopUpTargetSpecies;
import com.syngenta.sylk.menu_add.pages.PopUpXrefPage;
import com.syngenta.sylk.menu_add.pages.ProjectLeadPage;
import com.syngenta.sylk.menu_add.pages.ViewLiteratureEvidenceDetailsPageSequence;

public class LeadProcessWorkflowUsingAccessionNoTest {

	private List<Object[]> testData = new ArrayList<Object[]>();

	private AddNewGeneticFeaturePage addNewGFPage;
	private ConstructNominationPage consPage;
	private LandingPage lp;
	private HomePage homepage;
	private GeneticFeaturePage gfPage;
	private String newGFlink;

	@BeforeClass(alwaysRun = true)
	public void loadData() {
		this.testData = new CommonLibrary()
				.getTestDataAsObjectArray("LeadProcessWF_UsingAccessionNum.xlsx");
	}

	@BeforeMethod(alwaysRun = true)
	public void testSetUp() {
		this.lp = LandingPage.getLandingPage();
		this.homepage = this.lp.goToHomePage();
	}

	@AfterMethod(alwaysRun = true)
	public void cleanUp() {

		MenuPage menu = null;
		try {
			if (this.consPage != null) {
				menu = this.consPage.getMenuPage();
			} else {
				menu = this.addNewGFPage.getMenuPage();
			}
			HomePage home = menu.gotoHomePage();
			home = home.deleteThisGeneticFeature(home, this.newGFlink);
		} catch (Exception e) {
			System.out.println("Could not delete GF..");
			System.out.println(new CommonLibrary().getStackTrace(e));
		}

		if (this.lp != null) {
			this.lp.driverQuit();
		}
	}

	@DataProvider(name = "leadProcessAccessionNo", parallel = false)
	public Iterator<Object[]> loadTestData() {

		return this.testData.iterator();
	}

	@Test(enabled = true, description = "This test will verify the functionality of SyLK application while creating Lead Process workflow using  "
			+ "Accession Numbers", dataProvider = "leadProcessAccessionNo", groups = {
			"leadprocessworkflow_using_accessionnumber", "leadprocess",
			"regression"})
	public void leadProcessWorkflowUsingAccessionNo(String testDescription,
			String row_num, HashMap<String, String> columns) {

		// step 1:

		SyngentaReporter reporter = new SyngentaReporter();

		try {
			reporter.reportPass("Login to SyLK");
			this.newGFlink = columns.get("newGFlink");

			this.addNewGFPage = this.homepage.goToAddGeneticFeaturePage();
			reporter.verifyEqual(this.addNewGFPage.getPageTitle(),
					PageTitles.add_new_genetic_feature_page_title,
					"Open Add Genetic Feature Page");

			this.addNewGFPage.selectGeneType(columns.get("gene_type"));

			this.addNewGFPage.enterTextInSequence(columns.get("accessionNo"));

			boolean isSequenceTypeEnabled = this.addNewGFPage
					.isSequenceTypeEnabled();
			reporter.assertTrue(isSequenceTypeEnabled,
					"Entering a valid accession_number should auto select accesstion radio button.");

			BasePage page = this.addNewGFPage.clickFindMatches();
			DuplicateSearchResultPage duplicatesearchResultPage = null;;
			if (page instanceof GeneticFeaturePage) {
				throw new SkipException(
						"The data being used in this test already exists in the system. This test will delete this data now for future runs and mark this test as SKIPPED.");
			}

			if (page instanceof DuplicateSearchResultPage) {
				GeneticFeatureSearchResultsPage gfSearchResultsPage = null;
				duplicatesearchResultPage = (DuplicateSearchResultPage) page;

				/*
				 * Step 13 : Click on the Search External Databases button.
				 */

				page = null;
				page = duplicatesearchResultPage.clickExternalDatabase();
				ImportGeneticFeaturePage importGFPage = null;
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

					importGFPage = gfSearchResultsPage
							.clickOnAnyGeneIdToOpenImportGF();

				} else if (page instanceof ImportGeneticFeaturePage) {
					importGFPage = (ImportGeneticFeaturePage) page;

				}

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
				this.homepage = importGFDetailPage.clickAddSequenceAsANewGF();
				reporter.verifyEqual(this.homepage.getPageTitle(),
						PageTitles.home_page_title,
						"Sylk home page appears after Genetic Feature is imported into Sylk");

				this.gfPage = this.homepage.clickNewGeneticFeatureLink(columns
						.get("newGFlink"));

				reporter.assertEqual(this.gfPage.getPageTitle(),
						PageTitles.genetic_feature_page_title,
						"Genetic Feature Screen appears showing the Gene summary");

				int initialEvidenceCount = this.gfPage
						.getEvidenceSequenceCountOnTab();
				this.gfPage.clickOnEvidenceSequenceTab();

				LiteratureSearchPage literatureSearchPage = this.gfPage
						.selectAddEvidenceSequence("literature");
				CreateLiteratureEvidenceDetailsForGeneticFeaturePage createLiterature = (CreateLiteratureEvidenceDetailsForGeneticFeaturePage) literatureSearchPage
						.searchThis(columns.get("search"));

				reporter.assertEqual(
						createLiterature.getPageTitle(),
						PageTitles.Create_Literature_Evidence_Details_for_Sequence_page_title,
						"Create literature page opens up when searching for '"
								+ columns.get("search ") + "'");

				createLiterature.enterRationale(columns.get("rationale"));

				createLiterature.enterObservation(columns.get("observation"));

				PopUpAddSequenceAccession popUpAddseqAccession = createLiterature
						.clickOnAddSequence();

				if (popUpAddseqAccession == null) {
					reporter.assertThisAsFail("Click on add sequence button on create literature page did not open up the popup.");
				} else {
					reporter.reportPass("Click on add sequence button on create literature page opens up a popup.");
				}

				createLiterature = popUpAddseqAccession.addSequenceName(columns
						.get("addSeq"));

				PopUpAddTraitComponent popUptrait = createLiterature
						.clickAddTraitComponent();

				if (popUptrait == null) {
					reporter.assertThisAsFail("Click on add trait component button on create literature page opens up a popup.");
				} else {
					reporter.reportPass("Click on add trait component button on create literature page opens up a popup.");
				}

				createLiterature = (CreateLiteratureEvidenceDetailsForGeneticFeaturePage) popUptrait
						.addTrait(columns.get("trait"));

				this.gfPage = createLiterature.clickSave();

				int currentCountOnEvidenceTab = this.gfPage
						.getEvidenceSequenceCountOnTab();
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

				this.gfPage = this.gfPage.clickOnEvidenceSequenceTab();

				ViewLiteratureEvidenceDetailsPageSequence viewLiteraturePage = this.gfPage
						.clickviewLiteratureEvidenceSequence();

				reporter.assertEqual(
						viewLiteraturePage.getPageTitle(),
						PageTitles.view_literature_evidence_for_sequence_page_title,
						"Click on image icon under sequence evidence opens up view literature page.");

				reporter.reportPass("View literature page Title = "
						+ viewLiteraturePage.getTitle() + ", Author = "
						+ viewLiteraturePage.getAuthor() + ", PMID = "
						+ viewLiteraturePage.getPMID());
				this.gfPage = viewLiteraturePage.clickOnBack();

				this.gfPage = this.gfPage.clickDetailTab();
				PopUpXrefPage popUpxrefpage = this.gfPage.clickAddNewXref();
				popUpxrefpage.enterAccessionNo(columns.get("xrefAccessionNo"));
				popUpxrefpage.selectSource(columns.get("xrefSource"));
				this.gfPage = popUpxrefpage.clickSave();

				this.gfPage = this.gfPage.clickOnLeadInfoTab();
				LeadNominationPage leadNominationpage = this.gfPage
						.clickOnNominateButton();
				reporter.assertEqual(
						viewLiteraturePage.getPageTitle(),
						PageTitles.lead_nomination_page_title,
						"Click on nomination button under lead info on Genetic feature page opens up Load Nomination page.");

				PopUpAddSuggestedProjectNamePage popUpAddSuggProNamepage = leadNominationpage
						.clickOnAddSuggestedProjectName();

				popUpAddSuggProNamepage.enterSuggestedProjectName(columns
						.get("suggestedProjectName"));
				leadNominationpage = popUpAddSuggProNamepage.clickAdd();
				leadNominationpage.selectLeadSource(columns.get("lead_source"));
				leadNominationpage.selectLeadType(columns.get("lead_type"));
				leadNominationpage.enterLeadFunction(columns
						.get("lead_function"));
				PopUpAddSourceOfLeadFunctionInfo leadInfoPage = leadNominationpage
						.clickOnAddLeadFunctionInfo();
				leadNominationpage = leadInfoPage
						.addLeadFunctionInformationAndClickAdd(columns
								.get("source_lead_function_info"));
				leadNominationpage.enterRationale("test");
				this.gfPage = leadNominationpage.clickOnAddLeadNomination();

				reporter.assertEqual(
						this.gfPage.getPageTitle(),
						PageTitles.genetic_feature_page_title,
						"Click on save lead nomination with all valid data save nomination and opens up genetic feature page.");

				this.gfPage = this.gfPage.clickOnProjectLeadTab();
				ProjectLeadPage projectLeadPage = this.gfPage
						.clickOnPromoteToNewProjectButton();

				String leadName = projectLeadPage.getLeadName();
				String promoterName = projectLeadPage.getPromotingPersonName();

				reporter.assertEqual(this.gfPage.getPageTitle(),
						PageTitles.project_lead_page_title,
						"Click on promote to new project button opens up genetic feature page.");
				reporter.reportPass("Project Lead page shows Lead Name as '"
						+ leadName + "', Person Promoting Lead name as '"
						+ promoterName + "'.");
				projectLeadPage.enterNominationRationale("test");
				this.gfPage = projectLeadPage.clickOnSave();

				if (!this.gfPage.promoteNewProjectButtonExists()) {
					this.gfPage.clickOnProjectLeadTab();
				}

				this.gfPage = this.gfPage
						.clickOnTheProjectLinkInProjectLeadTab(1);

				this.consPage = this.gfPage.clickOnNominateConstructButton();
				reporter.assertEqual(
						this.consPage.getPageTitle(),
						PageTitles.Construct_Nominations,
						"Click on 'Nominate Construct button'on genetic feature page navigates to 'Construct Nomination Page' ");

				this.consPage.addRationale("test");
				this.consPage.addVectorFunctionDescription("test");
				this.consPage.checkSBC();
				PopUpTargetSpecies popup = this.consPage
						.clickOnAddTargetSpecies();

				popup.enterTargetSpeciesClickAdd("Acanthus ebracteatus");
				this.consPage.enterSuggestedGOIName("test");

				this.consPage.selectSequence("cDNA");
				String sequence = this.consPage.getSequenceText();

				this.consPage = this.consPage.clickOnSave();

				this.homepage = this.consPage.gotoHomePage();

				// this.lp = LandingPage.getLandingPage();
				// HomePage homepage1 = this.lp.goToHomePage();

				this.gfPage = this.homepage.clickNewGeneticFeatureLink(columns
						.get("newGFlink"));

				this.gfPage = this.gfPage.clickOnConstructTab();

				this.consPage = this.gfPage.clickConstructNominationIdLink(1);
				reporter.verifyEqual(
						this.consPage.getPageTitle(),
						PageTitles.Construct_Nominations,
						"Click on 'Construct nomination ID link' inside the 'construct tab' on Genetic feature Page navigates to the  'Construct Nomination Page', the construct is nominated successfully . ");

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
