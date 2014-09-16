package com.syngenta.sylk.menu_add.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.syngenta.sylk.main.pages.MenuPage;

public class PopUpFileDownLoad extends MenuPage {

	protected PopUpFileDownLoad(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	public void clickOpen() {

	}

	public PopUpExcelNewSeqPage clickOk() {

		PopUpExcelNewSeqPage page = new PopUpExcelNewSeqPage(this.driver);
		PageFactory.initElements(this.driver, page);

		return page;

	}

}
