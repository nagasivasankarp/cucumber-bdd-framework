package com.stepdefinations;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import com.cucumber.listener.Reporter;
import com.helperclasses.BaseClass;
import com.helperclasses.ConfigFile;
import com.pageobjects.GoibiboFlightsSearchPage;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class GoibiboFlightsSearch extends BaseClass {
	public WebDriver driver;
	private ConfigFile configFileReader;
	private Logger logger;
	private GoibiboFlightsSearchPage goibiboSearchPage;

	public GoibiboFlightsSearch() throws Exception {
		goibiboSearchPage = new GoibiboFlightsSearchPage();
		PropertyConfigurator.configure("log4j.properties");
		configFileReader = new ConfigFile();
		logger = Logger.getLogger("GoibiboFlightsSearch");
	}

	@Given("^I navigated to goibibo application$")
	public void i_navigated_to_goibibo_application() throws Exception {
		try {
			configFileReader.loadPropertyFile("config.properties");
			driver = launchBrowser("chrome", configFileReader.getProperty("url"));
			Reporter.addStepLog("Opened url is : " + configFileReader.getProperty("url"));
			logger.info("Opened url is : " + configFileReader.getProperty("url"));
			boolean verifygoibibohomepage = goibiboSearchPage.verifyGoibiboAppOpened(driver);
			if (verifygoibibohomepage) {
				Reporter.addStepLog("Application is navigated to Goibibo home page");
				logger.info("Application is navigated to Goibibo home page");
				report(driver, "Goibibo Home Page");
			} else {
				Reporter.addStepLog("Application is not navigated to Goibibo home page");
				logger.info("Application is not navigated to Goibibo home page");
				report(driver, "Goibibo Page");
			}
		} catch (Exception ex) {
			logger.info("Exception is : " + ex.getMessage());
			Reporter.addStepLog("Exception is : " + ex.getMessage());
			report(driver, "Exception");
			tearDown(driver);
		}

	}

	@When("^I searched for flights between bangalore and mumbai$")
	public void i_searched_for_flights_between_bangalore_and_mumbai() throws Exception {
		try {
			goibiboSearchPage.clkOneWayTripBtn(driver);
			Reporter.addStepLog("Cliked on On-way button");
			logger.info("Cliked on On-way button");
			goibiboSearchPage.fillFromFld(driver, configFileReader.getProperty("FromFieldValue"));
			Reporter.addStepLog("Filled From field with : " + configFileReader.getProperty("FromFieldValue"));
			logger.info("Filled From field with : " + configFileReader.getProperty("FromFieldValue"));
			goibiboSearchPage.fillDestinationFld(driver, configFileReader.getProperty("DestinationFieldValue"));
			Reporter.addStepLog("Filled Destination field with : " + configFileReader.getProperty("DestinationFieldValue"));
			logger.info("Filled Destination field with : " + configFileReader.getProperty("DestinationFieldValue"));
			goibiboSearchPage.clkDepartureDateFld(driver);
			Reporter.addStepLog("Filled Departure Date field ");
			logger.info("Filled Departure Date field ");
			goibiboSearchPage.clkDateNextBtn(driver);
			Reporter.addStepLog("Filled Next Month Button in Calender");
			logger.info("Filled Next Month Button in Calender");
			goibiboSearchPage.clkDateLink(driver);
			Reporter.addStepLog("Selected the Date");
			logger.info("Selected the Date");
			goibiboSearchPage.clkSearchBtn(driver);
			Reporter.addStepLog("Clicked on Search Button");
			logger.info("Clicked on Search Button");
			Thread.sleep(1000);
			report(driver, "Search Results");
		} catch (Exception ex) {
			logger.info("Exception is : " + ex.getMessage());
			Reporter.addStepLog("Exception is : " + ex.getMessage());
			report(driver, "Exception");
			tearDown(driver);
		}
	}

	@Then("^I should see the prices list of fights$")
	public void i_should_see_the_prices_list_of_fights() throws Exception {
		try {
			boolean resultPricesDecreasingOrder = goibiboSearchPage.verifyPricesOrder(driver);
			if (resultPricesDecreasingOrder) {
				logger.info("The Results of Prices are in Decreasing Order");
				Reporter.addStepLog("The Results of Prices are in Decreasing Order");
			} else {
				logger.info("The Results of Prices are in Improper Order");
				Reporter.addStepLog("The Results of Prices are in Improper Order");
			}
			tearDown(driver);
		} catch (Exception ex) {
			logger.info("Exception is : " + ex.getMessage());
			Reporter.addStepLog("Exception is : " + ex.getMessage());
			report(driver, "Exception");
			tearDown(driver);
		}
	}
}
