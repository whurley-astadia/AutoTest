package com.syngenta.sylk.menu_add.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.syngenta.sylk.main.pages.BasePage;

public class PopUpExcelNewSeqPage extends BasePage {

	protected PopUpExcelNewSeqPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	public GeneticFeaturePage closeExcel() {

		GeneticFeaturePage page = new GeneticFeaturePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;

	}

}
