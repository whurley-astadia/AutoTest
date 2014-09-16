package com.syngenta.sylk.menu_find.pages;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.syngenta.sylk.libraries.CommonLibrary;
import com.syngenta.sylk.main.pages.MenuPage;
import com.syngenta.sylk.menu_add.pages.LiteratureSearchPage;
import com.syngenta.sylk.menu_add.pages.ViewLiteratureEvidenceDetailsRNAI;

public class RNAiPage extends MenuPage {

	public RNAiPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(css = "ul#RNAi_tabs li:nth-child(4) a")
	private WebElement rNAIConstructs;

	@FindBy(css = "input[id='edit'][value='Edit']")
	private WebElement edit;

	@FindBy(css = "@FindBy(css=ul#RNAi_tabs ")
	private WebElement detailActive;

	@FindBy(css = "select[id='type'][name='typeCode']")
	private WebElement rNAiTriggerType;

	@FindBy(css = "span#ro_typeCode")
	private WebElement getrNAiTriggerType;

	@FindBy(css = "input[id='name'][name='name']")
	private WebElement name;

	@FindBy(css = "span#ro_name")
	private WebElement getname;

	@FindBy(css = "textarea[name='description']")
	private WebElement description;

	@FindBy(css = "span#ro_description")
	private WebElement getDescription;

	@FindBy(css = "input[name='genieId']")
	private WebElement genieID;

	@FindBy(css = "span#ro_genieId")
	private WebElement getGenieID;

	@FindBy(css = "input[name='synonyms']")
	private WebElement synonyms;

	@FindBy(css = "span#ro_synonyms")
	private WebElement getSynonyms;

	@FindBy(css = "input[id='save']")
	private WebElement save;

	@FindBy(css = "form#submitForm table tbody tr:nth-child(1)")
	private WebElement createdDate;

	@FindBy(css = "form#submitForm table tbody tr:nth-child(2) td:nth-child(2)")
	private WebElement AntisenseSequence;

	@FindBy(css = "ul#RNAi_tabs li:nth-child(2) a")
	private WebElement sequence;

	@FindBy(css = "textarea[id='entire'][name='entireRNAiTriggerSequence']")
	private WebElement entireRNAiTriggerSeq;

	@FindBy(css = "label[id='entire_error'][class='error']")
	private WebElement errorMessage;

	@FindBy(css = "label[id='name_error'][class='error']")
	private WebElement nameErrorMessage;

	@FindBy(css = "label[id='accession_error'][class='error']")
	private WebElement accessionErrorMessage;

	@FindBy(css = "input[id='accession'][name='accession']")
	private WebElement accessionNo;

	// enter Accession No
	public RNAiPage enterAccessionNo() {
		this.accessionNo.clear();
		this.waitForPageToLoad();
		RNAiPage page = new RNAiPage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	// enter Entire RNAi sequence.
	public void enterEntireRNAiTriggerSequence(String text) {
		this.entireRNAiTriggerSeq.clear();
		this.entireRNAiTriggerSeq.sendKeys(text);

	}

	public String getTextInEntireRNAiTriggerTextbox() {
		String text = this.entireRNAiTriggerSeq.getText();
		return text;

	}

	public String getTextInEntireRNAiTriggerReadOnlyText() {
		WebElement element = this.driver.findElement(By
				.id("ro_entireRNAiTriggerSequence"));
		String text = element.getText();
		return text;
	}

	public String getCreatedDate() {
		// List<WebElement> elements = this.createdDate.findElements(By
		// .tagName("td"));
		String date = null;
		// if (elements != null) {
		// WebElement element = elements.get(1);
		// date = this.createdDate.getText();
		// }

		List<WebElement> tables = this.createdDate.findElements(By
				.cssSelector("table.table"));
		WebElement td = tables.get(1).findElement(
				By.cssSelector("tr:nth-child(4) td:nth-child(2)"));
		date = td.getText();
		return date;

	}

	public static boolean checkValidFormat(String format, String value) {

		Date date = null;
		try {
			date = new SimpleDateFormat(format).parse(value);
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return date != null;
	}

	public String getValidationErrorName() {
		String errormsg = this.nameErrorMessage.getText();
		return errormsg;
	}

	public String getValidationError() {
		String errormsg = this.errorMessage.getText();
		return errormsg;
	}
	public String getValidationErrorAccession() {
		String error = this.accessionErrorMessage.getText();
		return error;
	}

	public boolean isEditButtonUnderDetailTabEnabled() {
		return this.edit.isEnabled();

	}
	// save button enabled
	public boolean isSaveButtonBeneathEditableFieldEnabled() {
		return this.save.isEnabled();
	}

	public boolean isAntisenseSequenceEditable() {
		return this.AntisenseSequence.isEnabled();
	}

	public void selectRNAiTriggerType(String type) {
		List<WebElement> elements = this.rNAiTriggerType.findElements(By
				.tagName("option"));
		for (WebElement element : elements) {
			if (element.getText().equalsIgnoreCase(type)) {
				element.click();

			}

		}

	}

	public String getRNAiTriggerType() {
		String text = this.getrNAiTriggerType.getText();
		return text;
	}

	public RNAiPage clickSave() {
		// this.save.click();
		WebElement save = this.driver.findElement(By.id("save"));
		Actions actions = new Actions(this.driver);
		actions.sendKeys(save, Keys.ENTER);
		actions.perform();
		this.waitForPageToLoad();
		this.waitForAjax();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RNAiPage page = new RNAiPage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;

	}
	public void enterSynonyms(String text) {
		this.synonyms.clear();
		this.synonyms.sendKeys(text);
	}

	public String getSynonyms() {
		String text = this.getSynonyms.getText();
		return text;
	}

	public void enterGenieID(String text) {
		this.genieID.clear();
		this.genieID.sendKeys(text);

	}

	public String getGenieID() {
		String text = this.getGenieID.getText();
		return text;
	}

	public void enterDescription(String text) {
		this.description.clear();
		this.description.sendKeys(text);
	}

	public String getDescription() {
		String text = this.getDescription.getText();
		return text;
	}

	public void clearText() {
		this.name.clear();
	}

	public void enterName(String text) {
		this.name.sendKeys(text);
	}

	public String getName() {
		String text = this.getname.getText();
		return text;

	}

	public RNAiPage clickEdit() {
		this.edit.click();
		this.waitForPageToLoad();
		RNAiPage page = new RNAiPage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;

	}
	private void waitForEvidenceSequenceToLoad() {
		try {
			if (this.getEvidenceCountOnTab() > 0) {
				this.waitForWebElement(By.cssSelector("div#ui-tabs-3 div.clip"));
			} else {
				new CommonLibrary().slowDown();
			}
		} catch (Exception e) {

		}
	}

	// public void clickRNAiConstruct() {
	// this.rNAIConstructs.click();
	// this.waitForPageToLoad();
	// this.waitForAjax();
	// }

	public int getEvidenceCountOnTab() {

		WebElement evidenceTab = this.driver.findElement(By
				.cssSelector("ul#RNAi_tabs li:nth-child(3)"));
		WebElement evi = evidenceTab.findElement(By.tagName("a"));
		String text = evi.getText();
		text = StringUtils.substringBetween(text, "(", ")");
		if (StringUtils.isBlank(text)) {
			return 0;
		} else {
			return Integer.parseInt(text);
		}

	}

	public int getConstructCountOnTab() {
		WebElement evidenceTab = this.driver.findElement(By
				.cssSelector("ul#RNAi_tabs li:nth-child(4)"));
		WebElement evi = evidenceTab.findElement(By.tagName("a"));
		String text = evi.getText();
		text = StringUtils.substringBetween(text, "(", ")");
		if (StringUtils.isBlank(text)) {
			return 0;
		} else {
			return Integer.parseInt(text);
		}
	}

	public RNAiPage clickOnSequenceTab() {
		this.sequence.click();
		this.waitForPageToLoad();
		this.waitForAjax();
		RNAiPage page = new RNAiPage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;

	}

	public RNAiPage clickOnEvidenceTab() {
		WebElement evidenceTab = this.driver.findElement(By
				.cssSelector("ul#RNAi_tabs li:nth-child(3)"));
		if (!StringUtils.containsIgnoreCase(evidenceTab.getAttribute("class"),
				"ui-state-active")) {
			WebElement evi = evidenceTab.findElement(By.tagName("a"));
			evi.click();
			this.waitForPageToLoad();
			this.waitForAjax();
			this.waitForEvidenceSequenceToLoad();
		}
		RNAiPage page = new RNAiPage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;

	}

	public RNAiPage clickOnDetailTab() {
		WebElement evidenceTab = this.driver.findElement(By
				.cssSelector("ul#RNAi_tabs li:nth-child(1)"));
		if (!StringUtils.containsIgnoreCase(evidenceTab.getAttribute("class"),
				"ui-state-active")) {
			WebElement evi = evidenceTab.findElement(By.tagName("a"));
			evi.click();
			this.waitForPageToLoad();
			this.waitForAjax();
			this.waitForEvidenceSequenceToLoad();
		}
		RNAiPage page = new RNAiPage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public LiteratureSearchPage selectAddEvidence(String selection) {

		if (StringUtils.containsIgnoreCase(selection, "literature")) {
			selection = "literature";
		} else if (StringUtils.containsIgnoreCase(selection, "other")) {
			selection = "other";
		}
		WebElement div = this.driver.findElement(By.id("ui-tabs-3"));
		List<WebElement> elements = div.findElements(By.tagName("option"));
		for (WebElement element : elements) {
			String value = StringUtils.lowerCase(element.getAttribute("value"));
			if (StringUtils.equals(StringUtils.deleteWhitespace(value),
					selection)) {
				element.click();
				break;
			}
		}
		this.waitForPageToLoad();
		this.waitForAjax();
		LiteratureSearchPage page = new LiteratureSearchPage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;

	}

	public boolean checkIfMagnifyingGlassImageIsVerticallyAlligned() {
		WebElement mainDiv = this.driver.findElement(By.id("ui-tabs-3"));
		List<WebElement> tables = mainDiv.findElements(By.tagName("table"));

		boolean flag = true;
		for (WebElement table : tables) {
			List<WebElement> trs = table.findElements(By.tagName("tr"));
			int a = 0;
			for (WebElement tr : trs) {
				a++;
				if (a == 1) {
					continue;
				}

				try {
					WebElement view = tr.findElement(By
							.cssSelector("td:nth-child(1) span.view"));
				} catch (Exception e) {
					flag = false;
					break;
				}

			}

			if (!flag) {
				break;
			}
		}

		return flag;
	}

	//
	public ViewLiteratureEvidenceDetailsRNAI clickFirstViewLiteratureEvidence() {
		List<WebElement> evidences = this.driver.findElements(By
				.cssSelector("div#ui-tabs-3 span.view"));

		evidences.get(0).click();

		this.waitForPageToLoad();
		this.waitForAjax();
		ViewLiteratureEvidenceDetailsRNAI page = new ViewLiteratureEvidenceDetailsRNAI(
				this.driver);
		PageFactory.initElements(this.driver, page);
		return page;

	}
	public RNAiPage deleteAllEvdence() {
		int count = this.getEvidenceCountOnTab();
		if (count == 0) {

			RNAiPage page = new RNAiPage(this.driver);
			PageFactory.initElements(this.driver, page);
			return page;
		}
		RNAiPage page = null;
		for (int i = 1; i <= count; i++) {
			this.clickOnEvidenceTab();
			ViewLiteratureEvidenceDetailsRNAI literature = this
					.clickFirstViewLiteratureEvidence();
			page = literature.clickDeleteToDeleteThisLiteratureEvidence();
		}

		page = new RNAiPage(this.driver);
		PageFactory.initElements(this.driver, page);
		return page;
	}

	public boolean isDeleteRNAiButtonEnabled() {
		WebElement mainDiv = this.driver.findElement(By
				.cssSelector("div#bd div#pg"));
		WebElement button = mainDiv.findElement(By
				.cssSelector("input[value='Delete This RNAi Trigger']"));
		String className = button.getAttribute("class");
		if (StringUtils.containsIgnoreCase(className, "disabled")) {
			return false;
		} else {
			return true;
		}
	}

	public String getTooTipOnDeleteRNAiButton() {
		WebElement mainDiv = this.driver.findElement(By
				.cssSelector("div#bd div#pg"));
		WebElement button = mainDiv.findElement(By
				.cssSelector("input[value='Delete This RNAi Trigger']"));
		String toolTip = button.getAttribute("title");
		return toolTip;
	}

}
