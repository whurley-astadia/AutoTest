package com.syngenta.sylk.main.pages;

import org.apache.commons.lang.StringUtils;
import org.testng.Reporter;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;

public class SyngentaReporter {
	private SoftAssert softAssert = new SoftAssert();
	private Assertion hardAssert = new Assertion();
	// private StringBuilder reporterLog = new StringBuilder();
	public void assertAll() {
		this.softAssert.assertAll();
	}

	public void verifyEqual(String actual, String expected, String failMessage,
			String passMessage) {
		actual = StringUtils.deleteWhitespace(actual);
		expected = StringUtils.deleteWhitespace(expected);
		this.softAssert.assertEquals(StringUtils.lowerCase(actual),
				StringUtils.lowerCase(expected), failMessage);
		if (!StringUtils.equalsIgnoreCase(StringUtils.lowerCase(actual),
				StringUtils.lowerCase(expected))) {
			this.reportThisAsFail(failMessage);
		} else {
			this.reportThisAsPass(passMessage);
		}
	}
	public void verifyEqual(String actual, String expected, String message) {
		actual = StringUtils.deleteWhitespace(actual);
		expected = StringUtils.deleteWhitespace(expected);
		System.out.println(actual);
		System.out.println(expected);
		this.softAssert.assertEquals(StringUtils.lowerCase(actual),
				StringUtils.lowerCase(expected), message + " - FAIL");
		if (!StringUtils.equalsIgnoreCase(StringUtils.lowerCase(actual),
				StringUtils.lowerCase(expected))) {
			this.reportThisAsFail(message);
		} else {
			this.reportThisAsPass(message);
		}
	}

	public void assertEqual(String actual, String expected, String message) {
		actual = StringUtils.deleteWhitespace(actual);
		expected = StringUtils.deleteWhitespace(expected);

		if (!StringUtils.equalsIgnoreCase(StringUtils.lowerCase(actual),
				StringUtils.lowerCase(expected))) {
			this.assertThisAsFail(message);
		} else {
			this.reportThisAsPass(message);
		}
	}

	public void verifyThisAsFail(String failMessage) {
		this.reportThisAsFail(failMessage);
		this.softAssert.fail(failMessage);
	}

	private void reportThisAsPass(String passMessage) {
		Reporter.log(passMessage + " - PASS ");

		/*
		 * If we are using emailable-report.html, we dont need to uncomment the
		 * below line. But if we are going to use index.html and the Reporter
		 * output we need to uncomment the below.
		 */
		// Reporter.log(this.getLineBreak());

	}

	private void reportThisAsFail(String failMessage) {
		Reporter.log(failMessage + " - FAIL ");

	}
	public void verifyTrue(boolean actual, String failMessage,
			String passMessage) {
		this.softAssert.assertTrue(actual, failMessage);
		if (actual) {
			this.reportPass(passMessage);
		} else {
			this.reportThisAsFail(failMessage);
		}

	}

	public void verifyTrue(boolean actual, String message) {
		this.softAssert.assertTrue(actual, message);
		if (actual) {
			this.reportPass(message);
		} else {
			this.reportThisAsFail(message);
		}

	}

	public void assertTrue(boolean actual, String failMessage,
			String passMessage) {
		if (actual) {
			this.reportPass(passMessage);
		} else {
			this.assertThisAsFail(failMessage);
		}

	}

	public void assertTrue(boolean actual, String message) {
		if (actual) {
			this.reportPass(message);
		} else {
			this.assertThisAsFail(message);
		}

	}
	public void reportPass(String string) {
		this.reportThisAsPass(string);
	}

	public void assertThisAsFail(String string) {
		this.reportThisAsFail(string);
		this.softAssert.assertAll();
		this.hardAssert.fail(string);
	}

}
