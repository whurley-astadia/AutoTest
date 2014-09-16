package com.syngenta.sylk.menu_add.pages;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.syngenta.sylk.main.pages.BasePage;
import com.syngenta.sylk.main.pages.MenuPage;

public class BLASTnRnaiResultPage extends MenuPage {

	protected BLASTnRnaiResultPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(css = "input[value='Add as New RNAi Trigger']")
	private WebElement AddASaNewRnaiTrigger;

	@FindBy(css = "input[className='btn'][value='Cancel']")
	private WebElement cancel;

	@FindBy(className = "pointer")
	private WebElement rnaiMatches;

	public BasePage clickAddAsNewRNAITriggerButton() {
		List<WebElement> buttons = this.driver.findElements(By
				.cssSelector("div#bd div#pg input"));
		for (WebElement button : buttons) {
			if (StringUtils.equalsIgnoreCase(button.getAttribute("value"),
					"Add as New RNAi Trigger")) {
				button.click();
				break;
			}
		}
		this.waitForPageToLoad();
		this.waitForAjax();
		BasePage page = null;
		String id = "flagForCurationForm";
		WebElement popup = null;
		try {
			popup = this.driver.findElement(By.id(id));
		} catch (Exception e) {
			// do nothing
		}
		if (popup != null) {
			page = new PopUpFlagForCurationPage(this.driver);
			PageFactory.initElements(this.driver, page);

		} else {
			page = new RNAiTriggerDetailsPage(this.driver);
			PageFactory.initElements(this.driver, page);
		}

		return page;
	}
}
