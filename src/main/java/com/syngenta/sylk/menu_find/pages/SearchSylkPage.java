package com.syngenta.sylk.menu_find.pages;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.syngenta.sylk.libraries.PageTitles;
import com.syngenta.sylk.main.pages.BasePage;
import com.syngenta.sylk.main.pages.MenuPage;
import com.syngenta.sylk.menu_add.pages.AddNewROIPage;
import com.syngenta.sylk.menu_add.pages.GeneticFeaturePage;
import com.syngenta.sylk.menu_add.pages.RNAiTriggerDetailsPage;

public class SearchSylkPage extends MenuPage {

	public SearchSylkPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(id = "searchText")
	private WebElement sylkSearch;
	@FindBy(css = "input[type='button'][value='Search']")
	private WebElement search;
	@FindBy(css = "input[type='button'][value='Clear']")
	private WebElement clear;

	@FindBy(id = "createdBy")
	private WebElement addedBy;
	@FindBy(id = "createdByStart")
	private WebElement fromDate;
	@FindBy(id = "createdByEnd")
	private WebElement toDate;
	@FindBy(id = "searchDocType")
	private WebElement type;
	@FindBy(className = "w50")
	private WebElement view;
	@FindBy(id = "sortBy")
	private WebElement sort;
	@FindBy(id = "sortAsc")
	private WebElement direction;
	@FindBy(id = "search")
	private WebElement searchResultText;
	public void enterSylkSearch(String data) {
		this.sylkSearch.sendKeys(data);
	}

	public SearchSylkPage clickSearch() {
		this.selectView("50");
		this.search.click();
		this.waitForPopUpToLoad();
		SearchSylkPage page = new SearchSylkPage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public void clickClear() {
		this.clear.click();
	}

	public void selectAddedBy(String name) {
		name = StringUtils.deleteWhitespace(name);
		List<WebElement> elements = this.addedBy.findElements(By
				.tagName("option"));
		for (WebElement e : elements) {
			System.out.println(e.getText());
			if (StringUtils.equalsIgnoreCase(
					StringUtils.deleteWhitespace(e.getText()), name)) {
				e.click();
				break;
			}
		}
	}

	public void selectType(String data) {
		data = StringUtils.deleteWhitespace(data);
		List<WebElement> elements = this.type
				.findElements(By.tagName("option"));
		for (WebElement e : elements) {
			if (StringUtils.equalsIgnoreCase(
					StringUtils.deleteWhitespace(e.getText()), data)) {
				e.click();
				break;
			}
		}

	}

	public void selectView(String no) {
		no = StringUtils.deleteWhitespace(no);
		List<WebElement> elements = this.view
				.findElements(By.tagName("option"));
		for (WebElement e : elements) {
			if (StringUtils.equalsIgnoreCase(
					StringUtils.deleteWhitespace(e.getText()), no)) {
				e.click();
			}
		}

	}

	public void SelectSort(String data) {
		data = StringUtils.deleteWhitespace(data);
		List<WebElement> elements = this.sort
				.findElements(By.tagName("option"));
		for (WebElement e : elements) {
			if (StringUtils.equalsIgnoreCase(
					StringUtils.deleteWhitespace(e.getText()), data)) {
				e.click();
			}
		}
	}

	public BasePage clickAndOpenAGFWithConstruct() {
		int totalcount = this.getTotalResultCount();
		if (totalcount == 0) {
			SearchSylkPage spage = new SearchSylkPage(this.driver);
			PageFactory.initElements(this.driver, spage);
			return spage;
		}
		GeneticFeaturePage page = null;
		// List<WebElement> divs = this.driver.findElements(By
		// .cssSelector("div.hit"));
		for (int a = 0; a < totalcount; a++) {
			WebElement span = this.driver.findElement(By.cssSelector("div#hit_"
					+ a + " a.pointer.f12.underline span"));
			span.click();
			GeneticFeaturePage gfPage = new GeneticFeaturePage(this.driver);
			PageFactory.initElements(this.driver, gfPage);
			int count = gfPage.getConstructCountOnTab();
			if (count == 0) {
				this.browserBack();
			} else {
				page = gfPage;
				PageFactory.initElements(this.driver, page);
				break;
			}
		}
		return page;
	}

	public BasePage clickAndOpenThisGF(String gfName) {

		BasePage page = null;
		for (int a = 0; a < 50; a++) {
			WebElement span = null;
			try {
				span = this.driver.findElement(By.cssSelector("div#hit_" + a
						+ " a.pointer.f12.underline span"));
			} catch (Exception e) {
				// do nothing
			}

			if (span != null) {
				if (StringUtils.equalsIgnoreCase(span.getText(), gfName)) {
					span.click();
					page = new GeneticFeaturePage(this.driver);
					PageFactory.initElements(this.driver, page);
				}
			}
		}

		return page;

	}

	public String isThereAClickableGFLink() {
		String name = null;
		int totalcount = this.getTotalResultCount();
		if (totalcount == 0) {
			return null;
		}
		for (int a = 0; a < 50; a++) {
			WebElement span = null;
			try {
				span = this.driver.findElement(By.cssSelector("div#hit_" + a
						+ " a.pointer.f12.underline span"));
			} catch (Exception e) {
				// do nothing
			}

			if (span != null) {
				name = span.getText();
			}
		}

		return name;
	}

	public BasePage selectThisTriggerFromSearchResult(String Name) {

		int totalcount = this.getTotalResultCount();
		BasePage page = null;
		if (totalcount == 0) {
			page = this;
			return page;
		}
		for (int a = 0; a < 50; a++) {
			WebElement span = null;
			try {
				span = this.driver.findElement(By.cssSelector("div#hit_" + a
						+ " a.pointer.f12.underline span"));
			} catch (Exception e) {
				// do nothing
			}

			if (span != null) {
				String linkText = span.getText();

				if (StringUtils.containsIgnoreCase(linkText, Name)) {
					span.click();
					this.waitForPageToLoad();
					this.waitForAjax();
					if (StringUtils.equalsIgnoreCase(this.getPageTitle(),
							PageTitles.rnai_trigger_details_page_title_page)) {
						page = new RNAiTriggerDetailsPage(this.driver);
						PageFactory.initElements(this.driver, page);
					} else if (StringUtils.equalsIgnoreCase(
							this.getPageTitle(),
							PageTitles.add_New_ROI_Page_title)) {
						page = new AddNewROIPage(this.driver);
						PageFactory.initElements(this.driver, page);
						break;

					} else if (StringUtils.equalsIgnoreCase(
							this.getPageTitle(),
							PageTitles.genetic_feature_page_title)) {
						page = new GeneticFeaturePage(this.driver);
						PageFactory.initElements(this.driver, page);
						break;

					}
				}
			}
		}
		return page;
	}
	public int getTotalResultCount() {
		WebElement span;
		try {
			span = this.searchResultText.findElement(By
					.cssSelector("span.count.centered"));
		} catch (Exception e) {
			return 0;
		}
		String rawResultText = span.getText();
		System.out.println("The search result text is:" + rawResultText);
		String totalResultCount = StringUtils.substringAfterLast(rawResultText,
				"of");
		totalResultCount = StringUtils.deleteWhitespace(totalResultCount);
		return Integer.parseInt(totalResultCount);
	}

	public BasePage clickAndOpenRNAiWithLiteratureEvidence() {
		int totalcount = this.getTotalResultCount();
		if (totalcount == 0) {
			SearchSylkPage spage = new SearchSylkPage(this.driver);
			PageFactory.initElements(this.driver, spage);
			return spage;
		}
		RNAiPage page = null;
		for (int a = 0; a < totalcount; a++) {
			WebElement span = this.driver.findElement(By.cssSelector("div#hit_"
					+ a + " a.pointer.f12.underline span"));
			span.click();
			RNAiPage rnaiPage = new RNAiPage(this.driver);
			PageFactory.initElements(this.driver, rnaiPage);
			int count = rnaiPage.getEvidenceCountOnTab();
			if (count == 0) {
				this.browserBack();
			} else {
				page = rnaiPage;
				PageFactory.initElements(this.driver, page);
				break;
			}
		}

		return page;
	}

	public BasePage clickAndOpenRNAiSearch() {

		int totalcount = this.getTotalResultCount();
		RNAiPage page = null;
		if (totalcount == 0) {

			SearchSylkPage spage = new SearchSylkPage(this.driver);
			PageFactory.initElements(this.driver, spage);
			return spage;
		}

		/*
		 * need to add the logic to click Next if 50 records displayed does not
		 * have a link to click
		 */
		for (int a = 0; a < totalcount; a++) {
			WebElement span = null;
			try {
				span = this.driver.findElement(By.cssSelector("div#hit_0 a"));
			} catch (Exception e) {
				// do nothing
			}
			if (span == null) {
				continue;
			}
			span.click();
			page = new RNAiPage(this.driver);
			PageFactory.initElements(this.driver, page);

		}
		return page;
	}

	public BasePage clickAndOpenRNAiWithNominatedConstruct() {
		int totalcount = this.getTotalResultCount();
		if (totalcount == 0) {
			SearchSylkPage spage = new SearchSylkPage(this.driver);
			PageFactory.initElements(this.driver, spage);
			return spage;
		}
		RNAiPage page = null;
		for (int a = 0; a < totalcount; a++) {
			WebElement span = this.driver.findElement(By.cssSelector("div#hit_"
					+ a + " a.pointer.f12.underline span"));
			span.click();
			RNAiPage rnaiPage = new RNAiPage(this.driver);
			PageFactory.initElements(this.driver, rnaiPage);
			int count = rnaiPage.getConstructCountOnTab();
			if (count == 0) {
				this.browserBack();
			} else {
				page = rnaiPage;
				PageFactory.initElements(this.driver, page);
				break;
			}
		}

		return page;
	}

	public BasePage clickAndOpenRNAiWithNoConstruct() {
		int totalcount = this.getTotalResultCount();
		if (totalcount == 0) {
			SearchSylkPage spage = new SearchSylkPage(this.driver);
			PageFactory.initElements(this.driver, spage);
			return spage;
		}
		RNAiPage page = null;
		for (int a = 0; a < totalcount; a++) {
			WebElement span = this.driver.findElement(By.cssSelector("div#hit_"
					+ a + " a.pointer.f12.underline span"));
			span.click();
			RNAiPage rnaiPage = new RNAiPage(this.driver);
			PageFactory.initElements(this.driver, rnaiPage);
			int count = rnaiPage.getConstructCountOnTab();
			if (count != 0) {
				this.browserBack();
			} else {
				page = rnaiPage;
				PageFactory.initElements(this.driver, page);
				break;
			}
		}

		return page;
	}

	public BasePage clickAndOpenAGFWithoutLeadInfo() {
		int totalcount = this.getTotalResultCount();
		if (totalcount == 0) {
			SearchSylkPage spage = new SearchSylkPage(this.driver);
			PageFactory.initElements(this.driver, spage);
			return spage;
		}
		GeneticFeaturePage page = null;
		for (int a = 0; a < totalcount; a++) {
			WebElement span = this.driver.findElement(By.cssSelector("div#hit_"
					+ a + " a.pointer.f12.underline span"));
			span.click();
			GeneticFeaturePage gfPage = new GeneticFeaturePage(this.driver);
			PageFactory.initElements(this.driver, gfPage);
			int count = gfPage.getLeadInfoCountOnTab();
			if (count != 0) {
				this.browserBack();
			} else {
				page = gfPage;
				PageFactory.initElements(this.driver, page);
				break;
			}
		}

		return page;
	}

	public BasePage clickAndOpenAGFWithLeadInfo() {
		int totalcount = this.getTotalResultCount();
		if (totalcount == 0) {
			SearchSylkPage spage = new SearchSylkPage(this.driver);
			PageFactory.initElements(this.driver, spage);
			return spage;
		}
		GeneticFeaturePage page = null;
		for (int a = 0; a < totalcount; a++) {
			WebElement span = this.driver.findElement(By.cssSelector("div#hit_"
					+ a + " a.pointer.f12.underline span"));
			span.click();
			GeneticFeaturePage gfPage = new GeneticFeaturePage(this.driver);
			PageFactory.initElements(this.driver, gfPage);
			int count = gfPage.getLeadInfoCountOnTab();
			if (count == 0) {
				this.browserBack();
			} else {
				page = gfPage;
				PageFactory.initElements(this.driver, page);
				break;
			}
		}

		return page;
	}
}
