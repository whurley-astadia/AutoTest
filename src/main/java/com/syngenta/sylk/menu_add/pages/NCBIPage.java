package com.syngenta.sylk.menu_add.pages;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.syngenta.sylk.main.pages.BasePage;

public class NCBIPage extends BasePage {

	protected NCBIPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	public ImportGeneticFeatureDetailPage closeNCBIWindowAndGoToImportGFDetailpage() {
		this.driver.close();
		Set<String> set = this.driver.getWindowHandles();
		Iterator<String> i = set.iterator();
		while (i.hasNext()) {
			String handle = i.next();
			this.driver.switchTo().window(handle);
		}
		ImportGeneticFeatureDetailPage page = new ImportGeneticFeatureDetailPage(
				this.driver);
		PageFactory.initElements(this.driver, page);
		return page;

	}

	public GeneticFeaturePage closeNCBIWindowAndGoToGFpage() {

		this.driver.close();
		Set<String> set = this.driver.getWindowHandles();
		Iterator<String> i = set.iterator();
		while (i.hasNext()) {
			String handle = i.next();
			this.driver.switchTo().window(handle);
		}
		GeneticFeaturePage page = new GeneticFeaturePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;

	}

	@Override
	public String getPageTitle() {
		return this.driver.getTitle();
	}

}
