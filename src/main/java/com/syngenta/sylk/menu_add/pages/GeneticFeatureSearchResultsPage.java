package com.syngenta.sylk.menu_add.pages;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.syngenta.sylk.main.pages.BasePage;

public class GeneticFeatureSearchResultsPage extends BasePage {

	protected GeneticFeatureSearchResultsPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(css = ".title ")
	private List<WebElement> pTag;

	public boolean doesTheHeaderReadNoMatches() {
		WebElement h3 = this.driver.findElement(By.tagName("h3"));
		String message = h3.getText();
		if (StringUtils.equalsIgnoreCase(message,
				"No Match In External Database")) {
			return true;
		} else {
			return false;
		}
	}
	public ImportGeneticFeaturePage clickOnAnyGeneIdToOpenImportGF() {
		if (this.pTag != null) {
			WebElement aTag = this.pTag.get(0).findElement(By.tagName("a"));
			aTag.click();
			this.waitForPageToLoad();
			ImportGeneticFeaturePage page = new ImportGeneticFeaturePage(
					this.driver);
			PageFactory.initElements(this.driver, page);
			return page;
		}

		return null;
	}
}
