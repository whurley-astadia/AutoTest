package com.syngenta.sylk.menu_add.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.syngenta.sylk.main.pages.BasePage;

public class PopUpXrefPage extends BasePage {

	protected PopUpXrefPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(css = "input[id='xrefAccessionField']")
	private WebElement accessionNo;

	@FindBy(id = "xrefSourceField")
	private WebElement source;

	@FindBy(css = "input[ class='btn'][value='Cancel']")
	private WebElement cancel;

	@FindBy(css = "input[ class='btn'][value='Save']")
	private WebElement save;

	public void enterAccessionNo(String text) {
		this.accessionNo.sendKeys(text);

	}

	// Select a source from dropdown
	public void selectSource(String selection) {
		List<WebElement> elements = this.source.findElements(By
				.tagName("option"));
		for (WebElement element : elements) {
			if (element.getText().equalsIgnoreCase(selection)) {
				element.click();
			}

		}
	}

	// click save
	public GeneticFeaturePage clickSave() {
		this.save.click();
		GeneticFeaturePage page = new GeneticFeaturePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	// click cancel
	public GeneticFeaturePage clickCancel() {
		this.cancel.click();
		GeneticFeaturePage page = new GeneticFeaturePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

}
