package com.syngenta.sylk.menu_add.pages;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.syngenta.sylk.main.pages.MenuPage;
import com.syngenta.sylk.menu_find.pages.RNAiPage;

public class RNAiTriggerDetailsPage extends MenuPage {

	public RNAiTriggerDetailsPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(css = "input[id='name'][name='name']")
	private WebElement name;

	@FindBy(css = "select[id='type'][name='typeCode']")
	private WebElement rNAiTriggerType;

	@FindBy(css = "input[class='btn'][value='Add RNAi Trigger']")
	private WebElement addRNAiTrigger;

	public void enterName(String text) {
		this.name.sendKeys(text);

	}

	public void selectRNAiTriggerType(String type) {
		this.rNAiTriggerType = this.driver.findElement(By.id("type"));
		List<WebElement> elements = this.rNAiTriggerType.findElements(By
				.tagName("option"));
		for (WebElement element : elements) {
			if (element.getText().equalsIgnoreCase(type)) {
				element.click();
			}

		}

	}

	public SearchTargetGenePage clickOnSelectTargetGeneButton() {
		List<WebElement> buttons = this.driver.findElements(By
				.cssSelector("input.btn"));
		for (WebElement button : buttons) {
			if (StringUtils.equalsIgnoreCase(button.getAttribute("value"),
					"Select Target Gene")) {
				button.click();
				break;
			}
		}
		this.waitForPageToLoad();
		this.waitForAjax();
		SearchTargetGenePage page = new SearchTargetGenePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public RNAiPage clickAddRNAiTrigger() {
		this.addRNAiTrigger.click();
		this.waitForPageToLoad();
		this.waitForAjax();
		RNAiPage page = new RNAiPage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;

	}

	public RNAiTriggerDetailsPage clickOnDetailTab() {
		WebElement detailTab = this.driver.findElement(By
				.cssSelector("ul#RNAi_tabs li:nth-child(1)"));
		if (!StringUtils.containsIgnoreCase(detailTab.getAttribute("class"),
				"ui-state-active")) {
			WebElement element = detailTab.findElement(By.tagName("a"));
			element.click();
			this.waitForPageToLoad();
			this.waitForAjax();
		}
		RNAiTriggerDetailsPage page = new RNAiTriggerDetailsPage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public RNAiTriggerDetailsPage deleteTargetGene() {
		WebElement targetGeneDiv = null;
		try {
			targetGeneDiv = this.driver.findElement(By.name("targetGenes"));
		} catch (Exception e) {
			// ignore this exception.
			// An exception means there is no target gene to delete
		}
		if (targetGeneDiv != null) {
			List<WebElement> aTags = targetGeneDiv
					.findElements(By.tagName("a"));
			for (WebElement aTag : aTags) {
				String className = aTag.getAttribute("class");
				if (StringUtils.equalsIgnoreCase(className,
						"pointer.targetGeneDelete")) {
					aTag.click();
					this.driver.switchTo().alert().accept();
					this.waitForPageToLoad();
					this.waitForAjax();
					break;
				}
			}

		}

		RNAiTriggerDetailsPage page = new RNAiTriggerDetailsPage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public RNAiTriggerDetailsPage clickOnDeleteRNAiTrigger() {
		List<WebElement> buttons = this.driver.findElements(By
				.cssSelector("input.floatleft.btn"));
		for (WebElement button : buttons) {
			if (StringUtils.equalsIgnoreCase(button.getAttribute("value"),
					"Delete This RNAi Trigger")) {
				button.click();
				this.driver.switchTo().alert().accept();
				this.waitForPageToLoad();
				this.waitForAjax();
				break;
			}
		}
		RNAiTriggerDetailsPage page = new RNAiTriggerDetailsPage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}
}
