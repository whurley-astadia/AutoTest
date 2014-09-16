package com.syngenta.sylk.menu_add.pages;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.syngenta.sylk.main.pages.MenuPage;

public class SearchTargetGenePage extends MenuPage {

	protected SearchTargetGenePage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(className = "f12")
	private WebElement searchTargetGene;
	@FindBy(css = "input[class='btn'][value='Search']")
	private WebElement search;
	@FindBy(css = "input[class='btn'][value='Clear']")
	private WebElement clear;
	@FindBy(css = "select[id = 'createdBy'][name='createdBy']")
	private WebElement addedBy;
	@FindBy(id = "cbs")
	private WebElement fromDate;
	@FindBy(id = "cbe")
	private WebElement toDate;
	@FindBy(className = "w50")
	private WebElement view;
	@FindBy(id = "results")
	private WebElement addGene;

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

	public void enterSearchTargetGene(String data) {
		this.searchTargetGene.sendKeys(data);
	}

	public SearchTargetGenePage clickSearch() {
		this.search.click();
		this.waitForPageToLoad();

		SearchTargetGenePage searchPage = new SearchTargetGenePage(this.driver);
		PageFactory.initElements(this.driver, searchPage);

		return searchPage;

	}

	public void clickClear() {
		this.clear.click();
	}

	public RNAiTriggerDetailsPage clickAddGeneForRNAi() {
		List<WebElement> inputs = this.driver.findElements(By
				.cssSelector("input.btn"));
		for (WebElement input : inputs) {
			if (StringUtils.equalsIgnoreCase(input.getAttribute("value"),
					"add gene")) {
				input.click();
				break;
			}
		}

		this.waitForPageToLoad();
		this.waitForAjax();

		RNAiTriggerDetailsPage page = new RNAiTriggerDetailsPage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public AddNewROIPage clickAddGeneForROI() {
		List<WebElement> inputs = this.driver.findElements(By
				.cssSelector("input.btn"));
		for (WebElement input : inputs) {
			if (StringUtils.equalsIgnoreCase(input.getAttribute("value"),
					"add gene")) {
				input.click();
				break;
			}
		}

		this.waitForPageToLoad();
		this.waitForAjax();

		AddNewROIPage page = new AddNewROIPage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public void selectUser(String string) {
		WebElement user = this.driver.findElement(By.id("createdBy"));
		List<WebElement> elements = user.findElements(By.tagName("option"));
		for (WebElement e : elements) {
			if (e.getText().equalsIgnoreCase(string)) {
				e.click();
				break;
			}
		}
	}

}
