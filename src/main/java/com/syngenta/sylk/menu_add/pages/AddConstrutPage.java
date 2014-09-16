package com.syngenta.sylk.menu_add.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.syngenta.sylk.main.pages.MenuPage;

public class AddConstrutPage extends MenuPage {

	public AddConstrutPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(id = "projectsPulldown")
	private WebElement project;

	// selects a project from the dropdown
	public void selectProject(String selection) {
		this.project.click();
		List<WebElement> element = this.project.findElements(By
				.tagName("option"));
		for (WebElement e : element) {
			if (e.getText().equalsIgnoreCase("Other Proj:Other Projects")) {

			}

		}
	}

}
