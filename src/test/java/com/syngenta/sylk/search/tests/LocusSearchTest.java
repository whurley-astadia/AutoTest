package com.syngenta.sylk.search.tests;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class LocusSearchTest {

	/*
	 * Search Locus GF >> Synonym
	 */
	@BeforeTest(alwaysRun = true)
	public void setupNewlyAddedGF_SYNONYMS() {
		System.out.println("Before searchForNewlyAddedGF_SYNONYMS...");
	}

	@AfterTest(alwaysRun = true)
	public void tearDownNewlyAddedGF_SYNONYMS() {
		System.out.println("After searchForNewlyAddedGF_SYNONYMS ..");
	}

	@Test(enabled = true, description = "check that user can search for a new added GF >> SYNONYMS", groups = {
			"searchForNewlyAddedGF_SYNONYMS", "locussearch", "search",
			"regression" })
	public void searchForNewlyAddedGF_SYNONYMS() {
		System.out.println("Inside searchForNewlyAddedGF_SYNONYMS...");
	}

	/*
	 * Search Locus RANI Trigger >> Synonym
	 */
	@BeforeTest(alwaysRun = true)
	public void setupNewlyAddedRANITrigger_SYNONYMS() {
		System.out.println("Before searchForNewlyAddedRANITrigger_SYNONYMS...");
	}

	@AfterTest(alwaysRun = true)
	public void tearDownNewlyAddedRANITrigger_SYNONYMS() {
		System.out.println("After searchForNewlyAddedRANITrigger_SYNONYMS ..");
	}

	@Test(enabled = true, description = "check that user can search for a new added RANI Trigger >> SYNONYMS", groups = {
			"searchForNewlyAddedRANITrigger_SYNONYMS", "locussearch", "search",
			"regression" })
	public void searchForNewlyAddedRANITrigger_SYNONYMS() {
		System.out.println("Inside searchForNewlyAddedRANITrigger_SYNONYMS...");
	}
}
