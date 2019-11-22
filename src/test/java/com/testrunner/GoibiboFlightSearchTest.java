package com.testrunner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "FeatureFiles/GoibiboFlightsSearch.feature", glue = "com.stepdefinations", plugin = {
		"junit:target/cucumber-reports/Cucumber.xml", "json:target/cucumber-reports/Cucumber.json",
		"com.cucumber.listener.ExtentCucumberFormatter:Reports/Report.html" })

public class GoibiboFlightSearchTest {

}