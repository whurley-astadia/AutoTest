package com.syngenta.sylk.main.pages;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.syngenta.sylk.libraries.PageTitles;
import com.syngenta.sylk.libraries.SyngentaException;
import com.syngenta.sylk.menu_add.pages.AddNewGeneticFeaturePage;
import com.syngenta.sylk.menu_add.pages.AddNewROIPage;
import com.syngenta.sylk.menu_add.pages.AddRNAiTriggerPage;
import com.syngenta.sylk.menu_find.pages.SearchSylkPage;

public class MenuPage extends BasePage {

	protected MenuPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(css = "ul.nv li:nth-child(2)")
	private WebElement addLi;

	@FindBy(xpath = "//div[@id='nv']/div/ul/li[3]/a/span")
	private WebElement findUl;

	@FindBy(linkText = "GF/RNAi Triggers/ROI/Promoter")
	private WebElement findGFROI;
	@FindBy(css = "ul.nv")
	private WebElement menu;

	public MenuPage getMenuPage() {
		try {
			Actions action = new Actions(this.driver);
			action.sendKeys(Keys.PAGE_UP).sendKeys(Keys.PAGE_UP)
					.sendKeys(Keys.PAGE_UP).sendKeys(Keys.PAGE_UP);
			action.perform();
		} catch (Exception e) {
		}
		MenuPage page = new MenuPage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public AddRNAiTriggerPage goToRNAiTriggerPage() {
		AddRNAiTriggerPage page = null;
		WebElement span = this.addLi.findElement(By.tagName("span"));
		span.click();

		WebElement aTag = this.addLi.findElement(By.linkText("RNAi Trigger"));
		aTag.click();
		this.waitForPageToLoad();
		this.waitForAjax();
		if (StringUtils.equalsIgnoreCase(this.getPageTitle(),
				PageTitles.add_rnai_trigger_page_title)) {
			page = new AddRNAiTriggerPage(this.driver);
			PageFactory.initElements(this.driver, page);
		} else {
			throw new SyngentaException(
					"Add RNAi trigger Page did not open up correctly.");
		}
		return page;
	}

	public AddNewGeneticFeaturePage goToAddGeneticFeaturePage() {

		AddNewGeneticFeaturePage page = null;
		WebElement span = this.addLi.findElement(By.tagName("span"));
		span.click();

		WebElement aTag = this.addLi
				.findElement(By.linkText("Genetic Feature"));
		aTag.click();
		this.waitForPageToLoad();
		this.waitForAjax();
		if (StringUtils.equalsIgnoreCase(this.getPageTitle(),
				PageTitles.add_new_genetic_feature_page_title)) {
			page = new AddNewGeneticFeaturePage(this.driver);
			PageFactory.initElements(this.driver, page);
		} else {
			throw new SyngentaException(
					"Add Genetic Feature Page did not open up correctly.");
		}
		return page;
	}

	public AddNewROIPage goToAddNewROIPage() {
		AddNewROIPage page = null;
		WebElement span = this.addLi.findElement(By.tagName("span"));
		span.click();

		WebElement aTag = this.addLi.findElement(By
				.linkText("Region Of Interest"));
		aTag.click();
		this.waitForPageToLoad();
		this.waitForAjax();
		if (StringUtils.equalsIgnoreCase(this.getPageTitle(),
				PageTitles.add_New_ROI_Page_title)) {
			page = new AddNewROIPage(this.driver);
			PageFactory.initElements(this.driver, page);
		} else {
			throw new SyngentaException(
					"Add New ROI Page did not open up correctly.");
		}
		return page;
	}

	public SearchSylkPage goToGFRNAiTriggerROIpromoter() {

		SearchSylkPage page = null;
		try {
			this.findUl.click();

			this.findGFROI.click();

			this.waitForPageToLoad();
			this.waitForAjax();
			if (StringUtils.equalsIgnoreCase(this.getPageTitle(),
					PageTitles.search_sylk_page_title)) {
				page = new SearchSylkPage(this.driver);
				PageFactory.initElements(this.driver, page);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new SyngentaException(
					"'Search Sylk Page' did not open up correctly.");
		}
		return page;
	}

	@SuppressWarnings("unchecked")
	public HomePage gotoHomePage() {
		HomePage homePage = null;
		this.waitForWebElement(By.linkText("Home"));
		WebElement homeMenu = this.driver.findElement(By.linkText("Home"));
		// WebElement span = homeMenu.findElement(By.tagName("span"));
		homeMenu.click();
		this.waitForPageToLoad();
		this.waitForAjax();

		@SuppressWarnings("rawtypes")
		FluentWait wait = new WebDriverWait(this.driver, DEFAULT_WAIT_TIME);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.tagName("h3")));

		if (StringUtils.equalsIgnoreCase(this.getPageTitle(),
				PageTitles.home_page_title)) {
			homePage = new HomePage(this.driver);
			PageFactory.initElements(this.driver, homePage);
		} else {
			throw new SyngentaException("Home Page did not load up correctly.");
		}

		return homePage;
	}

}
