package com.syngenta.sylk.search.tests;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ContainerSearchTest {

	/*
	 * Search GF >> Container source code
	 */
	@BeforeTest(alwaysRun = true)
	public void setupExistingGF_ContainerSourceCode() {
		System.out.println("Before searchForExistingGF_ContainerSourceCode...");
	}

	@AfterTest(alwaysRun = true)
	public void tearDownExistingGF_ContainerSourceCode() {
		System.out.println("After searchForExistingGF_ContainerSourceCode ..");
	}

	@Test(enabled = true, description = "check that user can search for a new added RANI Trigger >> SYNONYMS", groups = {
			"searchForExistingGF_ContainerSourceCode", "containersearch",
			"search", "regression" })
	public void searchForExistingGF_ContainerSourceCode() {
		System.out.println("Inside searchForExistingGF_ContainerSourceCode...");
	}

}
