package com.syngenta.sylk.menu_add.pages;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.syngenta.sylk.main.pages.MenuPage;

public class AddRNAiTriggerPage extends MenuPage {

	public AddRNAiTriggerPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(id = "rnaiSequence")
	private WebElement sequence;

	@FindBy(css = "input[class='btn'][value='Select Target Gene']")
	private WebElement selectTargetGene;

	@FindBy(css = "input[value='Duplicate Check']")
	private WebElement duplicateChick;

	@FindBy(css = "input[value='Clear']")
	private WebElement clear;

	@FindBy(id = "error")
	private WebElement errorMsg;

	public void enterSequence(String data) {
		this.sequence.sendKeys(data);
	}

	public SearchTargetGenePage clickSelectTargetGene() {
		this.selectTargetGene.click();
		SearchTargetGenePage page = new SearchTargetGenePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public BLASTnRnaiResultPage clickDuplicateCheck() {
		this.duplicateChick.click();
		this.waitForPageToLoad();
		this.waitForAjax();
		BLASTnRnaiResultPage page = new BLASTnRnaiResultPage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public void clickClear() {
		this.clear.click();
	}

	public String getErrorMsgForInvalidSquence(String actualError) {
		String expectedEror = "Invalid sequence: sequence is too short";
		String actualErrorMsg = this.errorMsg.getText();
		return actualErrorMsg;
	}

	public BLASTnRnaiResultPage gotoBlastRnaiResultPage() {
		this.clickDuplicateCheck();
		BLASTnRnaiResultPage page = new BLASTnRnaiResultPage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public boolean checkGFIsSelected(String data) {
		List<WebElement> elements = this.driver.findElements(By
				.cssSelector("form#rnaiForm table a"));
		for (WebElement element : elements) {
			if (StringUtils.equalsIgnoreCase(data, element.getText())) {
				return true;
			}
		}

		return false;
	}
}
