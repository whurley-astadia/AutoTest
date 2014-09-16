package com.syngenta.sylk.menu_add.pages;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.syngenta.sylk.libraries.PageTitles;
import com.syngenta.sylk.main.pages.BasePage;

public class PopUpFlagForCurationPage extends BasePage {

	protected PopUpFlagForCurationPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(id = "curationUserRationale")
	private WebElement rationale;
	@FindBy(css = "div.ui-dialog-buttonset button:nth-child(1)")
	private WebElement continuebutton;
	@FindBy(css = "div.ui-dialog-buttonset button:nth-child(2)")
	private WebElement cancel;
	@FindBy(id = "urlError")
	private WebElement errorMsg;

	@FindBy(css = "div.ui-dialog.ui-widget.ui-widget-content.ui-corner-all.dialogButtons.ui-draggable div")
	private WebElement dialogBoxheader;

	public GeneticFeaturePage enterRationale(String data) {
		this.rationale.sendKeys(data);
		this.waitForPageToLoad();
		GeneticFeaturePage page = new GeneticFeaturePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;

	}

	public GeneticFeaturePage enterRationaleRNAi(String data) {
		WebElement rat = this.driver.findElement(By
				.cssSelector("textarea#curationReason"));
		rat.sendKeys(data);
		this.waitForPageToLoad();
		GeneticFeaturePage page = new GeneticFeaturePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;

	}
	public BLASTSearchResultPage clickCancel() {
		this.cancel.click();
		BLASTSearchResultPage page = new BLASTSearchResultPage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;

	}
	public BasePage clickContinueAndGoToNewGFPage() {
		this.continuebutton.click();
		this.waitForPageToLoad();
		String title = this.getPageTitle();
		if (StringUtils.equalsIgnoreCase(title,
				PageTitles.new_genetic_feature_page_title)) {
			NewGeneticFeaturePage page = new NewGeneticFeaturePage(this.driver);
			PageFactory.initElements(this.driver, page);
			return page;
		} else if (StringUtils.equalsIgnoreCase(title,
				PageTitles.genetic_feature_page_title)) {
			GeneticFeaturePage page = new GeneticFeaturePage(this.driver);
			PageFactory.initElements(this.driver, page);
			return page;
		} else if (StringUtils.equalsIgnoreCase(title,
				PageTitles.rnai_trigger_details_page_title_page)) {
			RNAiTriggerDetailsPage page = new RNAiTriggerDetailsPage(
					this.driver);
			PageFactory.initElements(this.driver, page);
			return page;
		}
		return null;
	}

	/**
	 * 
	 * Dragging of the div and moving it is not happening. This needs to be
	 * investigated.
	 * 
	 * Points collected from investigation of the problem thus far 1. Manually
	 * dragging through the UI happens when we get the mouse over a div
	 * (class=ui-dialog-titlebar ui-widget-header ui-corner-all
	 * ui-helper-clearfix) and this can then be moved around 2. When running
	 * through webdriver, this div does not appear in the dom. When we doa
	 * driver.findElement we dont get an elementNotFound but ElementNotVisible
	 * instead. Not sure what is going on. This needs tobe investigated further.
	 */

	public void clickAndDragThisWindow() {
		this.driver.manage().window().maximize();
		Actions builder = new Actions(this.driver);
		// WebElement we = this.driver
		// .findElement(By
		// .cssSelector(".ui-dialog-titlebar.ui-widget-header.ui-corner-all.ui-helper-clearfix"));
		// builder.moveToElement(we).build().perform();

		WebDriverWait wait = new WebDriverWait(this.driver, 10);
		WebElement div = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By
				// .cssSelector(".ui-dialog-titlebar.ui-widget-header.ui-corner-all.ui-helper-clearfix")));
						.id("flagForCurationDialog")));
		div.click();
		Point p = div.getLocation();
		int x = p.x;
		int y = p.y;
		builder.clickAndHold(div).moveByOffset(x + 300, y + 400).release()
				.perform();

	}
}
