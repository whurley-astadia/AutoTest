package com.syngenta.sylk.menu_add.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.syngenta.sylk.main.pages.MenuPage;

public class ViewLiteratureEvidenceDetailsPageSequence extends MenuPage {

	@FindBy(id = "evidence.title")
	private WebElement title;

	@FindBy(id = "evidence.authors")
	private WebElement author;

	@FindBy(id = "backButton")
	private WebElement back;

	@FindBy(id = "evidence.link")
	private WebElement pmidLink;

	public ViewLiteratureEvidenceDetailsPageSequence(WebDriver driver) {
		super(driver);
	}

	public GeneticFeaturePage clickOnBack() {
		this.back.click();
		this.waitForPageToLoad();
		GeneticFeaturePage page = new GeneticFeaturePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}
	public String getTitle() {
		return this.title.getText();
	}
	public String getAuthor() {
		return this.author.getText();
	}
	public String getPMID() {
		WebElement aTag = this.pmidLink.findElement(By.tagName("a"));
		String id = aTag.getText();
		return id;
	}

	public GeneticFeaturePage clickOnDelete() {
		WebElement button = this.driver.findElement(By.id("deleteEvidence"));
		button.click();
		this.driver.switchTo().alert().accept();
		this.waitForPageToLoad();
		this.waitForAjax();
		GeneticFeaturePage page = new GeneticFeaturePage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

}
