package com.pageobjects;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.helperclasses.BaseClass;

public class GoibiboFlightsSearchPage extends BaseClass {
	/* Page Objects Locators*/
	private static final By ONE_WAY_TRIP_BTN = By.xpath("//span[contains(text(),'One way')]");
	private static final By FROM_FLD = By.id("gosuggest_inputSrc");
	private static final By DESTINATION_FLD = By.id("gosuggest_inputDest");
	private static final By DEPARTURE_DATE_FLD = By.xpath("//div[@id='searchWidgetCommon']/div[1]/div[1]/div[1]/div/div[5]/input");
	private static final By DATE_NXT_BTN = By.xpath("//div[@id='searchWidgetCommon']/div[1]/div[1]/div[1]/div/div[5]/div/div/div[1]/span");
	private static final By DATE_PICKER_DAY = By.xpath("//div[contains(text(),'15')]");
	private static final By SEARCH_BTN = By.id("gi_search_btn");
	private static final By PRICE_LIST = By.xpath("//span[@class='ico22 fr fb']");

	/* Constructor */
	public void clkOneWayTripBtn(WebDriver driver) {
		waitForExpectedElement(driver, ONE_WAY_TRIP_BTN);
		driver.findElement(ONE_WAY_TRIP_BTN).click();
	}

	/* Method for Fill From field */
	public void fillFromFld(WebDriver driver, String fromFldValue) throws Exception {
		waitForExpectedElement(driver, FROM_FLD);
		driver.findElement(FROM_FLD).sendKeys(fromFldValue);
		Thread.sleep(1000);
		driver.findElement(FROM_FLD).sendKeys(Keys.DOWN);
		driver.findElement(FROM_FLD).sendKeys(Keys.ENTER);
	}

	/* Method for Fill Destination Field */
	public void fillDestinationFld(WebDriver driver, String destinationFldValue) throws Exception {
		waitForExpectedElement(driver, DESTINATION_FLD);
		driver.findElement(DESTINATION_FLD).sendKeys(destinationFldValue);
		Thread.sleep(1000);
		driver.findElement(DESTINATION_FLD).sendKeys(Keys.DOWN);
		driver.findElement(DESTINATION_FLD).sendKeys(Keys.ENTER);
	}

	/* Method for Click on Date Field */
	public void clkDepartureDateFld(WebDriver driver) {
		waitForExpectedElement(driver, DEPARTURE_DATE_FLD);
		driver.findElement(DEPARTURE_DATE_FLD).click();
	}

	/* Click on Next Month Button in Calendar */
	public void clkDateNextBtn(WebDriver driver) {
		waitForExpectedElement(driver, DATE_NXT_BTN);
		driver.findElement(DATE_NXT_BTN).click();
	}

	/* Method to Select the date */
	public void clkDateLink(WebDriver driver) {
		waitForExpectedElement(driver, DATE_PICKER_DAY);
		driver.findElement(DATE_PICKER_DAY).click();
	}

	public void clkSearchBtn(WebDriver driver) {
		waitForExpectedElement(driver, SEARCH_BTN);
		driver.findElement(SEARCH_BTN).click();
	}

	/* Method to verify Prices Order */
	public boolean verifyPricesOrder(WebDriver driver) throws Exception {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		for (int second = 0;; second++) {
			if (second >= 30) {
				break;
			}
			jse.executeScript("window.scrollBy(0,720)", "");
		}
		List<WebElement> resultPrices = driver.findElements(PRICE_LIST);
		List<Integer> prices = new ArrayList<Integer>();
		for (WebElement resultPrice : resultPrices) {
			if (resultPrice.getText().equals("")) {
				continue;
			}
			String replaceResultPrice = resultPrice.getText().replace(",", "");
			int replaceResultPriceList = Integer.parseInt(replaceResultPrice);
			prices.add(replaceResultPriceList);
		}
		boolean resultPricesDecreasingOrder = true;
		for (int i = 0; i < prices.size() - 1; i++) {
			if (prices.get(i) >= prices.get(i + 1) - 1) {
				continue;
			} else {
				resultPricesDecreasingOrder = false;
				return resultPricesDecreasingOrder;
			}
		}
		return resultPricesDecreasingOrder;
	}

	/* Method to verify Home page */
	public boolean verifyGoibiboAppOpened(WebDriver driver) {
		boolean verifyOneWayButton = driver.findElement(ONE_WAY_TRIP_BTN).isDisplayed();
		if (verifyOneWayButton == true) {
			return true;
		} else {
			return false;
		}
	}
}
