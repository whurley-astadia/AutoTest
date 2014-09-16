package com.syngenta.sylk.menu_add.pages;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.syngenta.sylk.main.pages.HomePage;
import com.syngenta.sylk.main.pages.MenuPage;

public class ImportGeneticFeatureDetailPage extends MenuPage {

	protected ImportGeneticFeatureDetailPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(id = "organismSearchableField")
	private WebElement organism;

	@FindBy(id = "sourceSequenceAccessionSearchableField")
	private WebElement accession;

	@FindBy(id = "symbolSearchableField")
	private WebElement symbol;

	@FindBy(id = "ResultHeader1")
	private WebElement expandMainBlast;

	@FindBy(id = "straightBLAST0")
	private WebElement expandBLASTp;

	@FindBy(id = "crossBLASTAccordionHeader0")
	private WebElement expandtBlastn;

	@FindBy(id = "addAsNewGeneticFeature")
	private WebElement addSequenceAsANewGF;

	@FindBy(css = "p[style='color: blue; background-color: transparent;']")
	private WebElement associatedSeqProtein;

	public boolean isThisGeneticFeatureDetailPage() {
		List<WebElement> h3s = this.driver.findElements(By.tagName("h3"));
		for (WebElement e : h3s) {
			if (StringUtils.equalsIgnoreCase(e.getText(), "Identification")) {
				return true;
			}
		}

		return false;
	}
	public HashMap<String, String> getGenticFeatureDetails() {
		HashMap<String, String> details = new HashMap<String, String>();
		try {
			details.put("organism", this.organism.getText());
		} catch (Exception e) {
			details.put("organism", "Organish label was blank");
		}
		try {
			details.put("symbol", this.symbol.getText());
		} catch (Exception e) {
			details.put("symbol", "Symbol was blank");
		}
		try {
			details.put("accession", this.accession.getText());
		} catch (Exception e) {
			details.put("accession", "Accession# was blank");
		}

		return details;
	}

	public void expandColapseResultForBlast() {
		WebElement expand = this.expandMainBlast
				.findElement(By.tagName("span"));
		expand.click();

	}

	public void expandBlastp() {
		WebElement expand = this.expandBLASTp.findElement(By.tagName("span"));
		expand.click();

	}

	public void expandtBlastn() {
		WebElement expand = this.expandtBlastn.findElement(By.tagName("span"));
		expand.click();
	}

	public boolean isMainBlastExpanded() {
		List<WebElement> elements = this.driver.findElements(By.tagName("h3"));
		for (WebElement element : elements) {
			String a = element.getText();
			System.out.println(a);
			if (StringUtils.equalsIgnoreCase(a,
					"BLASTp (Protein-Protein) Results")) {
				return true;
			}
		}
		return false;

	}

	public List<String> checktBLASTnResultForMatches(List<String> expected) {

		List<WebElement> elements = this.expandMainBlast.findElements(By
				.tagName("label"));
		for (WebElement e : elements) {
			String actual = e.getText();
			if (expected.contains(actual)) {
				expected.remove(actual);
			}
		}
		return expected;
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

	public HomePage clickAddSequenceAsANewGF() {
		this.addSequenceAsANewGF.click();
		this.waitForPageToLoad();
		this.waitForAjax();
		HomePage page = new HomePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;

	}

	public void clickExpandtheNCBIRelatedSeq() {
		WebElement element = this.driver.findElement(By
				.cssSelector("div#relatedSeq0 span#relatedSeqExpander0"));
		element.click();

	}

	public NCBIPage clickOnProteinLinkUnderRelatedSeq() {
		String currentHandle = this.driver.getWindowHandle();
		WebElement pTag = this.driver
				.findElement(By
						.cssSelector("label#gfid_acc_related-sequences_0_protein_val + p"));
		WebElement element = pTag.findElement(By.tagName("a"));
		element.click();
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

}
