package com.syngenta.sylk.menu_add.pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.syngenta.sylk.libraries.PageTitles;
import com.syngenta.sylk.libraries.SyngentaException;
import com.syngenta.sylk.main.pages.BasePage;
import com.syngenta.sylk.main.pages.MenuPage;

public class BLASTSearchResultPage extends MenuPage {

	public BLASTSearchResultPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	private List<WebElement> currentlySelectedAccession;
	@FindBy(id = "proteinStraightProteinId")
	private WebElement blastTable;

	@FindBy(className = "ui-icon ui-icon-expand ui-icon-plus")
	private WebElement BLASTpExpand;

	@FindBy(className = "ui-icon ui-icon-expand ui-icon-plus")
	private WebElement tBLASTpExpand;

	@FindBy(id = "proteinStraightProteinId")
	private WebElement BLASTp;

	@FindBy(id = "cDNAStraightNucleotideId")
	private WebElement BLASTn;

	@FindBy(id = "proteinCrossProteinId")
	private WebElement tBLASTp;

	@FindBy(id = "addAsNewGFButton")
	private WebElement addAsNewGF;

	@FindBy(id = "cancelButton")
	private WebElement cancel;

	@FindBy(id = "curationUserRationale")
	private WebElement RationaleFagForCurationPopUp;

	@FindBy(className = "popUpBtn ui-state-focus")
	private WebElement continueFagForCurationPopUp;

	@FindBy(id = "urlError")
	private WebElement ErrorMsgFagForCurationPopUp;

	@FindBy(css = "button[value='Cancel']")
	private WebElement cancelFagForCurationPopUp;

	@FindBy(id = "proteinStraightProteinHeaderId")
	private WebElement expandBLASTp;

	@FindBy(id = "proteinCrossProteinHeaderId")
	private WebElement expandtBLASTn;

	@FindBy(id = "protein-straight-protein")
	private WebElement clickLinkInsidetBLASTn;

	@FindBy(id = "cDNAStraightNucleotideHeaderId")
	private WebElement matches;

	@FindBy(css = "input[name='viewSequence'][value='View Glyma14g08320 Genetic Feature']")
	private WebElement clickviewGF;

	public void clickAndExpandBLASTp() {
		WebElement ExpandBLASTp = this.BLASTp.findElement(By.tagName("span"));
		ExpandBLASTp.click();
	}

	public String getAccessionNumber() {
		String accession_number = null;

		List<WebElement> divs = this.driver.findElements(By
				.cssSelector("div.formsFormBlock"));
		for (WebElement div : divs) {
			WebElement label = div.findElement(By.tagName("label"));
			if (StringUtils.equalsIgnoreCase(label.getText(),
					"Accession Number")) {
				WebElement myDiv = div.findElement(By
						.cssSelector("div.formsItemInputWrapper"));
				accession_number = myDiv.getText();
				break;

			}
		}
		return accession_number;

	}
	public void clickAndExpandBLAST_cdna_nucleotide() {
		this.clickAndExpandBLAST("cdna", "nucleotide");
	}

	public void clickAndExpandBLAST_protein_protein() {
		this.clickAndExpandBLAST("protein", "protein");
	}

	public void clickAndExpandBLAST_gdna_nucleotide() {
		this.clickAndExpandBLAST("gdna", "nucleotide");
	}

	public void clickAndExpandBLAST_cdna_protein() {
		this.clickAndExpandBLAST("cdna", "protein");
	}

	public void clickAndExpandBLAST_gdna_protein() {
		this.clickAndExpandBLAST("gdna", "protein");
	}

	public void clickAndExpandBLAST(String type, String gene_type) {

		if (StringUtils.equalsIgnoreCase(type, "cdna")) {
			type = "cDNA";
		} else if (StringUtils.equalsIgnoreCase(type, "gdna")) {
			type = "gDNA";
		} else if (StringUtils.equalsIgnoreCase(type, "protein")) {
			type = "protein";
		}

		if (StringUtils.equalsIgnoreCase(gene_type, "protein")) {
			gene_type = "Protein";
		} else if (StringUtils.equalsIgnoreCase(gene_type, "nucleotide")) {
			gene_type = "Nucleotide";
		}
		WebElement div = this.driver.findElement(By.id(type + "Straight"
				+ gene_type + "Id"));
		WebElement ExpandBLASTn = div.findElement(By.tagName("span"));

		ExpandBLASTn.click();
		WebElement label = this.driver.findElement(By.cssSelector("h2#" + type
				+ "Straight" + gene_type + "HeaderId label:nth-child(4)"));
		if (StringUtils.containsIgnoreCase(label.getText(),
				"No Matches to View")) {
			ExpandBLASTn.click();
			div = this.driver.findElement(By.id(type + "Cross" + gene_type
					+ "Id"));
			ExpandBLASTn = div.findElement(By.tagName("span"));

			ExpandBLASTn.click();
		}
	}
	public BasePage clickAddAsNewGFAndGoToFlagForCuration() {
		BasePage page = null;
		this.addAsNewGF.click();
		this.waitForPageToLoad();
		this.waitForAjax();
		String dialogId = "ui-dialog-title-flagForCurationDialog";
		WebElement popUp = null;
		try {
			popUp = this.driver.findElement(By.id(dialogId));
			if (!popUp.isEnabled()) {
				popUp = null;
			}
		} catch (Exception e) {
			// don't do anything. Intentionally left unhandled.
		}
		if (popUp != null) {
			page = new PopUpFlagForCurationPage(this.driver);
		} else {
			page = new NewGeneticFeaturePage(this.driver);
		}
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public BasePage clickAddAsNewGFAndGoToNewGFPage() {
		BasePage page = null;
		// NewGeneticFeaturePage newGFpage = null;
		this.addAsNewGF.click();
		this.waitForPageToLoad();
		this.waitForAjax();
		String dialogId = "ui-dialog-title-flagForCurationDialog";
		WebElement popUp = null;
		try {
			popUp = this.driver.findElement(By.id(dialogId));
			if (!popUp.isEnabled()) {
				popUp = null;
			}
		} catch (Exception e) {
			// don't do anything. Intentionally left unhandled.
		}

		if (popUp != null) {
			page = new PopUpFlagForCurationPage(this.driver);
		}

		// new CommonLibrary().slowDown();
		else if (StringUtils.equalsIgnoreCase(this.getPageTitle(),
				PageTitles.new_genetic_feature_page_title)) {
			page = new NewGeneticFeaturePage(this.driver);

		}

		else {
			throw new SyngentaException(
					"New Genetic Feature Page did not open up correctly.");
		}
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public GeneticFeatureManualEntryPage clickCancel() {
		this.cancel.click();
		this.waitForPageToLoad();
		GeneticFeatureManualEntryPage page = new GeneticFeatureManualEntryPage(
				this.driver);
		PageFactory.initElements(this.driver, page);
		return page;

	}

	public void checkBLASTpResultForMatches(List<String> expected) {
		List<WebElement> elements = this.expandBLASTp.findElements(By
				.tagName("labels"));
		for (WebElement e : elements) {
			if (e.getText().contains((CharSequence) expected)) {
				e.clear();
				// }else( String matches=e.getText();

				// return matches;
			}
			// / incomplete
		}
	}

	public List<String> checktBLASTnResultForMatches(List<String> expected) {

		List<WebElement> elements = this.expandtBLASTn.findElements(By
				.tagName("label"));
		for (WebElement e : elements) {
			String actual = e.getText();
			if (expected.contains(actual)) {
				expected.remove(actual);
			}
		}
		return expected;
	}

	public boolean checkIfThisGFExistsInBlastProtein(String gfName) {
		List<WebElement> divs = this.driver.findElements(By
				.id("protein-straight-protein"));
		for (WebElement div : divs) {
			WebElement h2 = div.findElement(By.tagName("h2"));
			List<WebElement> aTags = h2.findElements(By.tagName("a"));
			for (WebElement a : aTags) {
				if (StringUtils.containsIgnoreCase(a.getText(), gfName)) {
					return true;
				}
			}
		}
		return false;
	}

	public Map<String, String> checkAllThreeMatches_cdna_nucleotide() {
		return this.checkAllThreeMatches("cdna", "nucleotide");
	}

	public Map<String, String> checkAllThreeMatches_gdna_nucleotide() {
		return this.checkAllThreeMatches("gdna", "nucleotide");
	}

	public Map<String, String> checkAllThreeMatches_gdna_protein() {
		return this.checkAllThreeMatches("gdna", "protein");
	}

	public Map<String, String> checkAllThreeMatches_cdna_protein() {
		return this.checkAllThreeMatches("cdna", "protein");
	}

	public Map<String, String> checkAllThreeMatches_protein_protein() {
		return this.checkAllThreeMatches("protein", "protein");
	}

	private Map<String, String> checkAllThreeMatches(String type,
			String gene_type) {
		HashMap<String, String> map = new HashMap<String, String>();
		if (StringUtils.equalsIgnoreCase(type, "cdna")) {
			type = "cDNA";
		} else if (StringUtils.equalsIgnoreCase(type, "gdna")) {
			type = "gDNA";
		}

		if (StringUtils.equalsIgnoreCase(gene_type, "protein")) {
			gene_type = "Protein";
		} else if (StringUtils.equalsIgnoreCase(gene_type, "nucleotide")) {
			gene_type = "Nucleotide";
		}

		WebElement matches = null;
		try {
			matches = this.driver.findElement(By.id(type + "Straight"
					+ gene_type + "HeaderId"));
		} catch (Exception e) {
			matches = this.driver.findElement(By.id(type + "Cross" + gene_type
					+ "HeaderId"));
		}
		List<WebElement> labels = matches.findElements(By.tagName("label"));
		for (WebElement label : labels) {
			String temp = label.getText();
			// this will remove ":" char from the string. i.e "Exact Matches: 0"
			// will become "Exact Matches 0"
			temp = StringUtils.remove(temp, ":");
			if (StringUtils.containsIgnoreCase(temp, "Exact Matches")) {
				// removes string Exact Matches from temp
				temp = StringUtils.remove(temp, "Exact Matches");
				// remove any kind of space (white spaces) from temp
				temp = StringUtils.deleteWhitespace(temp);
				map.put("Exact Matches", temp);
			} else if (StringUtils.containsIgnoreCase(temp, "Close Matches")) {
				temp = StringUtils.remove(temp, "Close Matches");
				temp = StringUtils.deleteWhitespace(temp);
				map.put("Close Matches", temp);
			} else if (StringUtils.containsIgnoreCase(temp, "Distant Matches")) {
				temp = StringUtils.remove(temp, "Distant Matches");
				temp = StringUtils.deleteWhitespace(temp);
				map.put("Distant Matches", temp);
			}

		}

		return map;
	}

	public void expandOrColapseThisGFInBlastProtein(String gfName) {
		List<WebElement> divs;
		try {
			divs = this.driver.findElements(By.id("protein-straight-protein"));
		} catch (Exception e) {
			divs = this.driver.findElements(By.id("protein-cross-protein"));
		}
		boolean flag = false;
		WebElement theDiv = null;
		for (WebElement div : divs) {
			WebElement h2 = div.findElement(By.tagName("h2"));
			List<WebElement> aTags = h2.findElements(By.tagName("a"));
			for (WebElement a : aTags) {
				if (StringUtils.containsIgnoreCase(a.getText(), gfName)) {
					flag = true;
					theDiv = div;
					break;
				}
			}

			if (flag) {
				WebElement span = div.findElement(By
						.cssSelector(".ui-icon.ui-icon-plus.ui-icon-expand"));
				// if this is already in expanded state then collapse it.
				if (span == null) {
					span = div
							.findElement(By
									.cssSelector(".ui-icon.ui-icon-expand.ui-icon-minus"));
				}
				if (span != null) {
					span.click();
				}
				break;
			}
		}

		if (!flag) {
			throw new SyngentaException("Genetic Feature :" + gfName
					+ " could not be found inside blast protein ");
		} else {
			WebElement input = theDiv.findElement(By
					.cssSelector("input[value^='View']"));
			if (input == null) {
				throw new SyngentaException(
						"Could not expand Genetic Feature :" + gfName
								+ " in blast.");
			}
		}

	}

	public GeneticFeaturePage clickOnThisGFInBlastProtein(String gfName) {
		List<WebElement> divs;
		try {
			divs = this.driver.findElements(By.id("protein-straight-protein"));
		} catch (Exception e) {
			divs = this.driver.findElements(By.id("protein-cross-protein"));
		}
		WebElement gNameLink = null;
		for (WebElement div : divs) {
			WebElement h2 = div.findElement(By.tagName("h2"));
			List<WebElement> aTags = h2.findElements(By.tagName("a"));
			for (WebElement a : aTags) {
				if (StringUtils.containsIgnoreCase(a.getText(), gfName)) {
					gNameLink = a;
					break;
				}
			}

			if (gNameLink != null) {
				break;
			}
		}

		if (gNameLink != null) {
			gNameLink.click();
			this.waitForPageToLoad();
			GeneticFeaturePage gfPage = new GeneticFeaturePage(this.driver);
			PageFactory.initElements(this.driver, gfPage);
			return gfPage;

		} else {
			throw new SyngentaException("Genetic Feature :" + gfName
					+ " could not be found inside blast protein ");
		}
	}

	public GeneticFeaturePage clickViewGeneticFeatureInBlast(String gfName) {
		List<WebElement> divs;
		try {
			divs = this.driver.findElements(By.id("protein-straight-protein"));
		} catch (Exception e) {
			divs = this.driver.findElements(By.id("protein-cross-protein"));
		}
		boolean flag = false;
		GeneticFeaturePage page = null;
		for (WebElement div : divs) {
			WebElement h2 = div.findElement(By.tagName("h2"));
			List<WebElement> aTags = h2.findElements(By.tagName("a"));
			for (WebElement a : aTags) {
				if (StringUtils.containsIgnoreCase(a.getText(), gfName)) {
					flag = true;
					break;
				}
			}

			if (flag) {
				WebElement input = div.findElement(By
						.cssSelector("input[value^='View']"));

				if (input != null) {
					input.click();
					this.waitForPageToLoad();
					page = new GeneticFeaturePage(this.driver);
					PageFactory.initElements(this.driver, page);
				}
				break;
			}
		}

		if (!flag) {
			throw new SyngentaException("Genetic Feature :" + gfName
					+ " could not be found inside blast protein ");
		} else {
			return page;
		}
	}

	public GeneticFeaturePage clickThisAccessionNoInBlastProteinandGoToGFPage(
			String gfName) {
		boolean done = false;
		List<WebElement> divs;
		try {
			divs = this.driver.findElements(By.id("protein-straight-protein"));
		} catch (Exception e) {
			divs = this.driver.findElements(By.id("protein-cross-protein"));
		}
		for (WebElement div : divs) {
			WebElement h2 = div.findElement(By.tagName("h2"));
			List<WebElement> aTags = h2.findElements(By.tagName("a"));
			for (WebElement a : aTags) {
				if (StringUtils.containsIgnoreCase(a.getText(), gfName)) {
					a.click();
					done = true;
					break;
				}
			}

			if (done) {
				break;
			}
		}
		this.waitForPageToLoad();
		GeneticFeaturePage page = new GeneticFeaturePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;

	}

	public List<WebElement> getOneGFFromExpandedBlast(String type,
			String gene_type) {

		int max = 0;
		List<WebElement> returnList = new ArrayList<WebElement>();
		WebElement returnATag = null;
		WebElement span = null;
		WebElement viewButton = null;
		WebElement addButton = null;
		// constructing the id up

		String tempS = "div#" + type + "-straight-" + gene_type;

		WebElement innerDiv;
		try {
			innerDiv = this.driver.findElement(By.cssSelector(tempS));
		} catch (Exception e2) {
			tempS = "div#" + type + "-cross-" + gene_type;
			innerDiv = this.driver.findElement(By.cssSelector(tempS));
		}
		WebElement percentageTd = null;
		WebElement thisSpan = innerDiv.findElement(By.tagName("span"));
		List<WebElement> trs = innerDiv.findElements(By.tagName("tr"));
		for (WebElement tr : trs) {

			try {
				// The intend is to look through all the trs inside of div
				// class b
				// and grab the 6th td. Not every tr inside of this div has
				// six
				// td within it and
				// hence we may see exceptions which we should ignore.

				percentageTd = tr
						.findElement(By.cssSelector("td:nth-child(6)"));
				System.out.println("percentageTd:" + percentageTd.getText());
			} catch (Exception e1) {
				// Ignore this exception
				// but we will continue with the next tr in the list
				continue;
			}
			int temp = 0;
			try {
				// just in case this td has no value and the below line
				// throws a
				// NumberFormatException.
				// We ignore the exception as we are looking for the higest
				// value in this method.
				temp = Integer.parseInt(percentageTd.getText());
			} catch (NumberFormatException e) {
			}

			if (temp > max) {
				WebElement myGF = tr.findElement(By
						.cssSelector("td:nth-child(4) a"));
				viewButton = innerDiv.findElement(By.name("viewSequence"));
				addButton = innerDiv
						.findElement(By.name("addToGeneticFeature"));

				max = temp;
				returnATag = myGF;
				span = thisSpan;
			}
		}

		returnList.add(0, returnATag);
		returnList.add(1, span);
		returnList.add(2, viewButton);
		returnList.add(3, addButton);
		return returnList;
	}
	public GeneticFeaturePage clickAndOpenAssessionFromExpandedBlast_cdna_nucleotide() {

		// write the logic to click on to the highest percentage of identity.
		List<WebElement> aTag = this.getOneGFFromExpandedBlast("cdna",
				"nucleotide");
		return this.clickLinkAndInstantiateGFPage(aTag.get(0));

	}

	public GeneticFeaturePage clickAndExPandTheSequenceFromExpandedBlast_cdna_nucleotide() {

		// write the logic to click on to the highest percentage of identity.
		this.currentlySelectedAccession = this.getOneGFFromExpandedBlast(
				"cdna", "nucleotide");
		return this
				.clickLinkAndInstantiateGFPage(this.currentlySelectedAccession
						.get(1));

	}
	public GeneticFeaturePage clickViewButtonOnExpandedgeneticFeature() {
		this.currentlySelectedAccession.get(2).click();
		this.waitForPageToLoad();
		this.waitForAjax();
		GeneticFeaturePage page = new GeneticFeaturePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;

	}

	public PopUpFlagForCurationPage clickAddGFButtonOnExpandedgeneticFeature() {
		this.currentlySelectedAccession.get(3).click();
		this.waitForPopUpToLoad();
		this.waitForAjax();
		PopUpFlagForCurationPage page = new PopUpFlagForCurationPage(
				this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}
	public GeneticFeaturePage clickAndOpenAssessionFromExpandedBlast_gdna_nucleotide() {

		// write the logic to click on to the highest percentage of identity.
		List<WebElement> aTag = this.getOneGFFromExpandedBlast("gdna",
				"nucleotide");
		return this.clickLinkAndInstantiateGFPage(aTag.get(0));

	}

	private GeneticFeaturePage clickLinkAndInstantiateGFPage(WebElement aTag) {
		aTag.click();
		this.waitForPageToLoad();
		this.waitForAjax();
		GeneticFeaturePage page = new GeneticFeaturePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public PopUpFlagForCurationPage clickOnAddNewSequenceToThisGF() {

		this.waitForPageToLoad();
		PopUpFlagForCurationPage page = new PopUpFlagForCurationPage(
				this.driver);
		PageFactory.initElements(this.driver, page);
		return page;

	}

}
