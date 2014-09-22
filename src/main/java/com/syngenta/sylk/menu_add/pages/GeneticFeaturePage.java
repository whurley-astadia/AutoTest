package com.syngenta.sylk.menu_add.pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.syngenta.sylk.libraries.CommonLibrary;
import com.syngenta.sylk.main.pages.HomePage;
import com.syngenta.sylk.main.pages.MenuPage;

public class GeneticFeaturePage extends MenuPage {

	private static final List<WebElement> WebElment = null;

	public GeneticFeaturePage(WebDriver driver) {
		super(driver);
		this.waitForConstructSequenceToLoad();
		this.waitForLeadInfoSequenceToLoad();
	}

	@FindBy(css = "a@href")
	private WebElement gDNA;

	@FindBy(id = "deleteGeneticFeatureButton")
	private WebElement deleteThisGeneticFeature;

	@FindBy(id = "exportButton")
	private WebElement export;

	@FindBy(id = "geneticFeature_geneId")
	private WebElement nCBIid;

	@FindBy(className = "ui-state-default ui-corner-top")
	private WebElement evidenceTabTop;

	@FindBy(id = "geneticFeature_tabPanel")
	private WebElement tabDiv;

	@FindBy(css = "div#ui-tabs-2 span.view")
	private WebElement viewEvidence;

	@FindBy(css = "div[id='ui-tabs-7 span.view']")
	private WebElement viewEvidenceSequence;

	@FindBy(css = "select[onchange='addGeneticFeatureEvidence(this.value)']")
	private WebElement evidencedropdown;

	@FindBy(css = "a[target='_blank']")
	private WebElement titleEvidenceStatementLink;

	@FindBy(css = "a[href='javascript:addSequence6('cds');']")
	private WebElement cDNA;

	@FindBy(css = "span[class='field']")
	private WebElement protein;

	@FindBy(css = "ul#geneticFeature_tabs li:nth-child(2)")
	private WebElement clickEvidence;

	@FindBy(css = "ul#sequence_0_tabs li:nth-child(2)")
	private WebElement clickEvidenceSequence;

	@FindBy(css = "ul#sequence_0_tabs li:nth-child(3)")
	private WebElement clickLeadInfo;

	@FindBy(css = "ul#sequence_0_tabs li:nth-child(5)")
	private WebElement clickProjectLeads;

	@FindBy(css = "ul#sequence_0_tabs li:nth-child(6)")
	private WebElement clickOnConstructTab;

	@FindBy(css = "ul#sequence_0_tabs li:nth-child(1)")
	private WebElement clickDetail;

	@FindBy(css = "div#ui-tabs-2 div select")
	private WebElement selectEvidence;

	@FindBy(css = "ul#sequence_0_tabs li:nth-child(3)")
	private WebElement leadInfo;

	@FindBy(css = "div#ui-tabs-6 table table tr:nth-child(1) td:nth-child(3)")
	private WebElement addNewXref;

	@FindBy(css = "div#ui-tabs-11")
	private WebElement constructDiv;

	private void waitForConstructSequenceToLoad() {
		try {
			CommonLibrary common = new CommonLibrary();
			WebElement construct = this.driver.findElement(By
					.cssSelector("ul#sequence_0_tabs li:nth-child(6)"));
			if (StringUtils.containsIgnoreCase(construct.getAttribute("class"),
					"ui-state-active")) {
				int count = this.getConstructCountOnTab();
				if (count == 0) {
					while (true) {
						try {
							this.driver.findElement(By
									.cssSelector("div#ui-tabs-11 div.info"));
							break;
						} catch (Exception e) {
							common.slowDown();
						}
					}
				} else {
					this.waitForWebElement(By
							.cssSelector("div#ui-tabs-11 table.spaced"));
				}
			}
		} catch (Exception e) {

		}
	}

	private void waitForLeadInfoSequenceToLoad() {
		try {
			CommonLibrary common = new CommonLibrary();
			WebElement leadInfo = this.driver.findElement(By
					.cssSelector("ul#sequence_0_tabs li:nth-child(3)"));
			if (StringUtils.containsIgnoreCase(leadInfo.getAttribute("class"),
					"ui-state-active")) {
				int count = this.getLeadInfoCountOnTab();
				if (count == 0) {
					while (true) {
						try {
							this.driver.findElement(By
									.cssSelector("div#ui-tabs-8 div.info"));
							break;
						} catch (Exception e) {
							common.slowDown();
						}
					}
				}
			}
		} catch (Exception e) {

		}
	}

	private void waitForEvidenceSequenceToLoad() {
		try {
			if (this.getEvidenceSequenceCountOnTab() > 0) {
				this.waitForWebElement(By.cssSelector("div#ui-tabs-7 div.clip"));
			} else {
				new CommonLibrary().slowDown();
			}
		} catch (Exception e) {

		}
	}
	public String getGDNALabel() {
		JavascriptExecutor js = (JavascriptExecutor) this.driver;
		WebElement element = (WebElement) js
				.executeScript("return jQuery.find(\"[href='javascript:addSequence6('gdna');']\")[0]");
		return element.getText();
	}

	// this method should accept the parameter and return the pop for the
	// associated seq.
	public PopUpgDNASequenceDataPage clickOngDNA() {
		JavascriptExecutor js = (JavascriptExecutor) this.driver;
		WebElement element = (WebElement) js
				.executeScript("return jQuery.find(\"[href='javascript:addSequence6('gdna');']\")[0]");
		if (element != null) {
			element.click();
		} else {
			element = (WebElement) js
					.executeScript("return jQuery.find(\"[href='javascript:viewSequence6('gdna');']\")[0]");
		}
		this.waitForPopUpToLoad();
		PopUpgDNASequenceDataPage page = new PopUpgDNASequenceDataPage(
				this.driver);
		PageFactory.initElements(this.driver, page);

		return page;
	}

	public PopUpcDNASequenceDataPage clickOncDNA() {
		String linkText = this.driver.findElement(
				By.cssSelector("div#sequence_0_container span")).getText();
		WebElement element = null;
		JavascriptExecutor js = (JavascriptExecutor) this.driver;
		if (StringUtils.containsIgnoreCase(linkText, "-cdna")) {
			WebElement div = this.driver.findElement(By.id("ui-tabs-6"));
			List<WebElement> trs = div.findElements(By.tagName("tr"));
			for (WebElement tr : trs) {
				WebElement td1;
				try {
					td1 = tr.findElement(By.cssSelector("td.label"));
				} catch (Exception e) {
					continue;
				}
				if (StringUtils.equalsIgnoreCase(
						StringUtils.deleteWhitespace(td1.getText()), "cDNA:")) {
					element = tr.findElement(By.tagName("span"));
					break;
				}
			}
			if (element != null) {
				element.click();
			}
		} else {
			element = (WebElement) js
					.executeScript("return jQuery.find(\"[href='javascript:addSequence6('cdna');']\")[0]");
			if (element != null) {
				element.click();
			} else {
				element = (WebElement) js
						.executeScript("return jQuery.find(\"[href='javascript:viewSequence6('cdna');']\")[0]");
			}
		}
		element.click();
		this.waitForPopUpToLoad();
		PopUpcDNASequenceDataPage page = new PopUpcDNASequenceDataPage(
				this.driver);
		PageFactory.initElements(this.driver, page);

		return page;
	}
	public PopUpCDSSequenceDataPage clickOnCDS() {
		JavascriptExecutor js = (JavascriptExecutor) this.driver;
		WebElement element = (WebElement) js
				.executeScript("return jQuery.find(\"[href='javascript:addSequence6('cds');']\")[0]");
		element.click();

		this.waitForPopUpToLoad();
		PopUpCDSSequenceDataPage page = new PopUpCDSSequenceDataPage(
				this.driver);
		PageFactory.initElements(this.driver, page);

		return page;
	}

	public PopUpProteinSequenceDataPage clickOnProtein() {
		this.protein.click();
		this.waitForPageToLoad();
		this.waitForAjax();
		PopUpProteinSequenceDataPage page = new PopUpProteinSequenceDataPage(
				this.driver);
		PageFactory.initElements(this.driver, page);

		return page;
	}

	public HomePage clickDeleleThisGeneticFeature() {
		this.deleteThisGeneticFeature.click();

		WebDriverWait wait = new WebDriverWait(this.driver, 10);
		wait.until(ExpectedConditions.alertIsPresent());
		this.driver.switchTo().alert().accept();
		this.waitForPageToLoad();
		this.waitForAjax();
		HomePage page = new HomePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public ConstructNominationPage clickConstructNominationIdLink(int a) {
		if (a == 0) {
			a = 1;
		}
		WebElement link = this.constructDiv.findElement(By
				.cssSelector("tr:nth-child(" + (a + 1) + ") td:nth-child(1)"));
		WebElement atag = link.findElement(By.tagName("a"));
		atag.click();
		this.waitForPageToLoad();
		this.waitForAjax();
		ConstructNominationPage page = new ConstructNominationPage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public PopUpFileDownLoad clickOnExport() {
		this.export.click();
		// write the implementation for the browser alert for
		// file download.
		this.waitForPopUpToLoad();
		PopUpFileDownLoad page = new PopUpFileDownLoad(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;

	}

	public NCBIPage expandTheSeqInPreferredSequenceSection() {
		this.nCBIid.click();
		this.waitForPopUpToLoad();
		this.waitForAjax();
		NCBIPage page = new NCBIPage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;

	}

	public String getTextFromCDNA() {
		String linkText = this.driver.findElement(
				By.cssSelector("div#sequence_0_container span")).getText();
		WebElement element = null;
		JavascriptExecutor js = (JavascriptExecutor) this.driver;
		if (StringUtils.containsIgnoreCase(linkText, "-cdna")) {
			WebElement div = this.driver.findElement(By.id("ui-tabs-6"));
			List<WebElement> trs = div.findElements(By.tagName("tr"));
			for (WebElement tr : trs) {
				WebElement td1;
				try {
					td1 = tr.findElement(By.cssSelector("td.label"));
				} catch (Exception e) {
					continue;
				}
				if (StringUtils.equalsIgnoreCase(
						StringUtils.deleteWhitespace(td1.getText()), "cDNA:")) {
					element = tr.findElement(By.tagName("span"));
					break;
				}
			}

		} else {
			element = (WebElement) js
					.executeScript("return jQuery.find(\"[href='javascript:addSequence6('cdna');']\")[0]");
			if (element == null) {
				element = (WebElement) js
						.executeScript("return jQuery.find(\"[href='javascript:viewSequence6('cdna');']\")[0]");
			}
		}
		if (element != null) {
			return element.getText();
		}

		return null;
	}

	public void clickEvidenceTab() {

		WebElement evi = this.clickEvidence.findElement(By.tagName("a"));
		evi.click();
		this.waitForPageToLoad();

	}

	public int getEvidenceCountOnTab() {

		WebElement evi = this.clickEvidence.findElement(By.tagName("a"));
		String text = evi.getText();
		text = StringUtils.substringBetween(text, "(", ")");
		if (StringUtils.isBlank(text)) {
			return 0;
		} else {
			return Integer.parseInt(text);
		}

	}
	public int getConstructCountOnTab() {

		WebElement evi = this.driver.findElement(By
				.partialLinkText("Constructs"));
		String text = evi.getText();
		text = StringUtils.substringBetween(text, "(", ")");
		if (StringUtils.isBlank(text)) {
			return 0;
		} else {
			return Integer.parseInt(text);
		}

	}

	public int getProjectLeadsCountOnTab() {

		WebElement evi = this.clickProjectLeads.findElement(By.tagName("a"));
		String text = evi.getText();
		text = StringUtils.substringBetween(text, "(", ")");
		if (StringUtils.isBlank(text)) {
			return 0;
		} else {
			return Integer.parseInt(text);
		}

	}

	public int getLeadInfoCountOnTab() {

		WebElement infoLi = this.driver.findElement(By
				.cssSelector("ul#sequence_0_tabs li:nth-child(3)"));
		WebElement evi = infoLi.findElement(By.tagName("a"));
		String text = evi.getText();
		text = StringUtils.substringBetween(text, "(", ")");
		if (StringUtils.isBlank(text)) {
			return 0;
		} else {
			return Integer.parseInt(text);
		}

	}

	public GeneticFeaturePage clickOnEvidenceSequenceTab() {
		WebElement evdTab = this.driver.findElement(By
				.cssSelector("ul#sequence_0_tabs li:nth-child(2)"));
		if (!StringUtils.containsIgnoreCase(evdTab.getAttribute("class"),
				"ui-state-active")) {
			WebElement evi = evdTab.findElement(By.tagName("a"));
			evi.click();
			this.waitForPageToLoad();
			this.waitForAjax();
			this.waitForEvidenceSequenceToLoad();
		}
		GeneticFeaturePage page = new GeneticFeaturePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;

	}

	public GeneticFeaturePage clickDetailTab() {
		if (!StringUtils.containsIgnoreCase(
				this.clickDetail.getAttribute("class"), "ui-state-active")) {
			WebElement evi = this.clickDetail.findElement(By.tagName("a"));
			evi.click();
			this.waitForPageToLoad();
			this.waitForAjax();
			this.waitForWebElement(By
					.cssSelector("div#ui-tabs-6 table table tr:nth-child(1) td:nth-child(3)"));
		}
		GeneticFeaturePage page = new GeneticFeaturePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;

	}
	public GeneticFeaturePage clickOnLeadInfoTab() {
		if (!StringUtils.containsIgnoreCase(
				this.clickLeadInfo.getAttribute("class"), "ui-state-active")) {
			WebElement evi = this.clickLeadInfo.findElement(By.tagName("a"));
			evi.click();
			this.waitForPageToLoad();
			this.waitForAjax();
			this.waitForLeadInfoSequenceToLoad();
		}
		GeneticFeaturePage page = new GeneticFeaturePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public GeneticFeaturePage clickOnProjectLeadTab() {
		if (!StringUtils
				.containsIgnoreCase(
						this.clickProjectLeads.getAttribute("class"),
						"ui-state-active")) {
			WebElement evi = this.clickProjectLeads
					.findElement(By.tagName("a"));
			evi.click();
			this.waitForPageToLoad();
			this.waitForAjax();
		}
		GeneticFeaturePage page = new GeneticFeaturePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public GeneticFeaturePage clickOnConstructTab() {
		if (!StringUtils.containsIgnoreCase(
				this.clickOnConstructTab.getAttribute("class"),
				"ui-state-active")) {
			WebElement element = this.clickOnConstructTab.findElement(By
					.tagName("a"));
			element.click();
			this.waitForPageToLoad();
			this.waitForAjax();
			this.waitForConstructSequenceToLoad();
		}

		GeneticFeaturePage page = new GeneticFeaturePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;

	}

	public boolean promoteNewProjectButtonExists() {
		boolean flag = false;
		try {
			WebElement button = this.driver.findElement(By
					.cssSelector("div#ui-tabs-10 input.formBtn"));
			if (StringUtils.equalsIgnoreCase(button.getAttribute("value"),
					"Promote To New Project")) {
				flag = true;
			}
		} catch (Exception e) {
		}

		return flag;

	}
	public ProjectLeadPage clickOnPromoteToNewProjectButton() {
		WebElement button = this.driver.findElement(By
				.cssSelector("div#ui-tabs-10 input.formBtn"));
		button.click();
		this.waitForPageToLoad();
		this.waitForAjax();
		ProjectLeadPage page = new ProjectLeadPage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public LeadNominationPage clickOnNominateButton() {
		WebElement form = this.driver.findElement(By
				.cssSelector("div#ui-tabs-8 form#submitForm"));
		List<WebElement> buttons = form.findElements(By
				.cssSelector("input.btn"));
		for (WebElement nominate : buttons) {
			if (StringUtils.equalsIgnoreCase(nominate.getAttribute("value"),
					"Nominate")) {
				nominate.click();
				break;
			}
		}
		this.waitForPageToLoad();
		this.waitForAjax();
		LeadNominationPage page = new LeadNominationPage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}
	public int getEvidenceSequenceCountOnTab() {

		WebElement evi = this.clickEvidenceSequence
				.findElement(By.tagName("a"));
		String text = evi.getText();
		text = StringUtils.substringBetween(text, "(", ")");
		if (StringUtils.isBlank(text)) {
			return 0;
		} else {
			return Integer.parseInt(text);
		}
	}

	public int getRegulatoryCheckCountOnTab() {
		WebElement reg = this.driver.findElement(By
				.cssSelector("ul#sequence_0_tabs li:nth-child(4)"));
		WebElement regATag = reg.findElement(By.tagName("a"));
		String text = regATag.getText();
		text = StringUtils.substringBetween(text, "(", ")");
		if (StringUtils.isBlank(text)) {
			return 0;
		} else {
			return Integer.parseInt(text);
		}
	}
	public RelatedLiteraturePage selectEvidence(String selection) {
		// this.selectEvidence.click();
		List<WebElement> elements = this.selectEvidence.findElements(By
				.tagName("option"));
		for (WebElement element : elements) {
			if (element.getText().equalsIgnoreCase(selection)) {
				element.click();
			}

		}
		this.waitForPageToLoad();
		this.waitForAjax();
		RelatedLiteraturePage page = new RelatedLiteraturePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;

	}

	public RelatedLiteraturePage selectAddEvidence(String selection) {

		if (StringUtils.containsIgnoreCase(selection, "literature")) {
			selection = "literature";
		} else if (StringUtils.containsIgnoreCase(selection, "other")) {
			selection = "other";
		}
		WebElement div = this.driver.findElement(By.id("ui-tabs-2"));
		List<WebElement> elements = div.findElements(By.tagName("option"));
		for (WebElement element : elements) {
			String value = StringUtils.lowerCase(element.getAttribute("value"));
			if (StringUtils.equals(StringUtils.deleteWhitespace(value),
					selection)) {
				element.click();
				break;
			}
		}
		this.waitForPageToLoad();
		this.waitForAjax();
		RelatedLiteraturePage page = new RelatedLiteraturePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;

	}

	public LiteratureSearchPage selectAddEvidenceSequence(String selection) {

		if (StringUtils.containsIgnoreCase(selection, "literature")) {
			selection = "literature";
		} else if (StringUtils.containsIgnoreCase(selection, "other")) {
			selection = "other";
		}
		WebElement div = this.driver.findElement(By.id("ui-tabs-7"));
		List<WebElement> elements = div.findElements(By.tagName("option"));
		for (WebElement element : elements) {
			String value = StringUtils.lowerCase(element.getAttribute("value"));
			if (StringUtils.equals(StringUtils.deleteWhitespace(value),
					selection)) {
				element.click();
				break;
			}
		}
		this.waitForPageToLoad();
		this.waitForAjax();
		LiteratureSearchPage page = new LiteratureSearchPage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;

	}
	public RelatedLiteraturePage clickEvidenceAndMakeAselection(String selection) {
		WebElement evi = this.evidenceTabTop.findElement(By.tagName("a"));
		evi.click();
		this.waitForPageToLoad();
		List<WebElement> elements = this.evidencedropdown.findElements(By
				.tagName("option"));
		for (WebElement element : elements) {
			if (element.getText().equalsIgnoreCase(selection)) {
				element.click();
			}
			this.waitForPageToLoad();
			this.waitForAjax();

		}
		RelatedLiteraturePage page = new RelatedLiteraturePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;

	}

	// click open and view the literature evidence.
	public ViewLiteratureEvidenceDetailsGF clickviewLiteratureEvidence() {
		this.viewEvidence.click();
		this.waitForPageToLoad();
		this.waitForAjax();
		ViewLiteratureEvidenceDetailsGF page = new ViewLiteratureEvidenceDetailsGF(
				this.driver);
		PageFactory.initElements(this.driver, page);
		return page;

	}

	public ViewLiteratureEvidenceDetailsPageSequence clickviewLiteratureEvidenceSequence() {
		this.viewEvidenceSequence = this.driver.findElement(By
				.cssSelector("div#ui-tabs-7 span.view"));
		this.viewEvidenceSequence.click();
		this.waitForPageToLoad();
		this.waitForAjax();
		ViewLiteratureEvidenceDetailsPageSequence page = new ViewLiteratureEvidenceDetailsPageSequence(
				this.driver);
		PageFactory.initElements(this.driver, page);
		return page;

	}

	public NCBIPage clickOnEvidenceStatementLink() {
		this.titleEvidenceStatementLink.click();
		String currentHandle = this.driver.getWindowHandle();

		this.waitForPopUpToLoad();
		Set<String> handles = this.driver.getWindowHandles();
		Iterator<String> i = handles.iterator();
		while (i.hasNext()) {
			String handle = i.next();
			if (!StringUtils.equalsIgnoreCase(currentHandle, handle)) {
				this.driver.switchTo().window(handle);
				System.out.println(":::::::::::::::::::::::::"
						+ this.driver.getTitle());
				break;
			}
		}
		this.waitForPopUpToLoad();
		NCBIPage page = new NCBIPage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;

	}

	// Add new Xref
	public PopUpXrefPage clickAddNewXref() {
		WebElement element = this.addNewXref.findElement(By.tagName("a"));
		element.click();
		this.waitForPopUpToLoad();
		PopUpXrefPage page = new PopUpXrefPage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public GeneticFeaturePage clickOnTheProjectLinkInProjectLeadTab(int a) {
		// when the test send 1 it means the first link needs to be clicked
		a = a - 1;
		WebElement projectLink = this.driver.findElement(By.id("summary_10_"
				+ a + ""));
		projectLink.click();
		GeneticFeaturePage page = new GeneticFeaturePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public ConstructNominationPage clickOnNominateConstructButton() {

		List<WebElement> buttons = this.driver.findElements(By
				.cssSelector("#submitForm > input.floatleft.btn"));
		for (WebElement button : buttons) {
			if (StringUtils.equalsIgnoreCase(button.getAttribute("value"),
					"Nominate as New Construct")) {
				button.click();
				break;
			}
		}

		ConstructNominationPage page = new ConstructNominationPage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public GeneticFeaturePage clickOnProjectLeadDeleteButton() {

		List<WebElement> buttons = this.driver.findElements(By
				.cssSelector("#submitForm > input.floatleft.btn"));
		for (WebElement button : buttons) {
			if (StringUtils.equalsIgnoreCase(button.getAttribute("value"),
					"Delete")) {
				button.click();
				this.driver.switchTo().alert().accept();
				break;
			}
		}

		GeneticFeaturePage page = new GeneticFeaturePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public GeneticFeaturePage clickOnLeadInfoDeleteButton() {
		WebElement button = this.driver.findElement(By
				.id("deleteLeadNominationDetailsButton8"));

		button.click();
		this.driver.switchTo().alert().accept();
		GeneticFeaturePage page = new GeneticFeaturePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public List<String> getConstructColumns() {
		List<String> columns = new ArrayList<String>();
		List<WebElement> allHeaders = this.driver.findElements(By
				.cssSelector("div#ui-tabs-11 td.label"));
		for (WebElement header : allHeaders) {
			columns.add(header.getText());
		}
		return columns;
	}

	public GeneticFeaturePage clickOnRnaiTab() {
		WebElement evdTab = this.driver.findElement(By
				.cssSelector("ul#geneticFeature_tabs li:nth-child(4)"));
		if (!StringUtils.containsIgnoreCase(evdTab.getAttribute("class"),
				"ui-state-active")) {
			WebElement evi = evdTab.findElement(By.tagName("a"));
			evi.click();
			this.waitForPageToLoad();
			this.waitForAjax();
			this.waitForEvidenceSequenceToLoad();
		}
		GeneticFeaturePage page = new GeneticFeaturePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public GeneticFeaturePage clickOnROITab() {
		WebElement evdTab = this.driver.findElement(By
				.cssSelector("ul#geneticFeature_tabs li:nth-child(5)"));
		if (!StringUtils.containsIgnoreCase(evdTab.getAttribute("class"),
				"ui-state-active")) {
			WebElement evi = evdTab.findElement(By.tagName("a"));
			evi.click();
			this.waitForPageToLoad();
			this.waitForAjax();
			this.waitForEvidenceSequenceToLoad();
		}
		GeneticFeaturePage page = new GeneticFeaturePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public HashMap<String, String> getAllColumnHeadersInRNAITab() {
		HashMap<String, String> columns = new HashMap<String, String>();
		WebElement mainDiv = this.driver.findElement(By
				.cssSelector("div#ui-tabs-4"));
		List<WebElement> tds = mainDiv.findElements(By
				.cssSelector("tr:nth-child(1) td"));
		int a = 1;
		for (WebElement td : tds) {
			columns.put("" + a, td.getText());
			a++;
		}

		return columns;
	}

	public GeneticFeaturePage clickDetailSequenceTab() {
		WebElement evdTab = this.driver.findElement(By
				.cssSelector("ul#sequence_0_tabs li:nth-child(1)"));
		if (!StringUtils.containsIgnoreCase(evdTab.getAttribute("class"),
				"ui-state-active")) {
			WebElement evi = evdTab.findElement(By.tagName("a"));
			evi.click();
			this.waitForPageToLoad();
			this.waitForAjax();
			this.waitForEvidenceSequenceToLoad();
		}
		GeneticFeaturePage page = new GeneticFeaturePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public GeneticFeaturePage clickOnRegulatoryCheckTab() {
		WebElement regTab = this.driver.findElement(By
				.cssSelector("ul#sequence_0_tabs li:nth-child(4)"));
		if (!StringUtils.containsIgnoreCase(regTab.getAttribute("class"),
				"ui-state-active")) {
			WebElement evi = regTab.findElement(By.tagName("a"));
			evi.click();
			this.waitForPageToLoad();
			this.waitForAjax();
			this.waitForRegulatoryCheckToLoad();
		}

		GeneticFeaturePage page = new GeneticFeaturePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	private void waitForRegulatoryCheckToLoad() {
		try {
			if (this.getRegulatoryCheckCountOnTab() > 0) {
				this.waitForWebElement(By.cssSelector("form#submitForm"));
			} else {
				new CommonLibrary().slowDown();
			}
		} catch (Exception e) {
		}
	}

	public String getRegulatoryCheckTabMessage() {
		WebElement form = this.driver.findElement(By
				.cssSelector("div#ui-tabs-9 form#submitForm"));
		String message = null;
		try {
			WebElement msgDiv = form.findElement(By
					.cssSelector("table div.info"));
			message = msgDiv.getText();
		} catch (Exception e) {
		}

		return message;
	}

	public boolean isCheckBoxRecommendedRegulatoryChecked() {
		WebElement span = this.driver.findElement(By
				.cssSelector("span#ro_recommend_regulatory_check"));
		WebElement input = span.findElement(By.tagName("input"));
		String value = input.getAttribute("value");
		if (StringUtils.equalsIgnoreCase(value, "on")) {
			return true;
		} else {
			return false;
		}
	}

	public String getAllergenSimilarityInRegulatory() {
		WebElement span = this.driver.findElement(By
				.cssSelector("span#ro_allergen_similarity"));
		String value = span.getText();
		return value;
	}

	public String getConsensesSiteInRegulatory() {
		WebElement span = this.driver.findElement(By
				.cssSelector("span#ro_glycosylation_consensus_sites"));
		String value = span.getText();
		return value;
	}

	public String getAllergenSearchDateInRegulatory() {
		WebElement span = this.driver.findElement(By
				.cssSelector("span#ro_allergen_search_date"));
		String value = span.getText();
		return value;
	}
}
