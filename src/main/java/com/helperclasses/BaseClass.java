package com.helperclasses;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.cucumber.listener.Reporter;
import com.google.common.io.Files;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {
	public WebDriver driver;
	public static String projectPath = System.getProperty("user.dir");
	public Date date;
	static Date dte = new Date();
	static String dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(dte);
	static String reportFileName = "Testing_" + dateFormat;
	private static String screenShotFilePath = projectPath + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "screenshots";
	public String reportsPath = projectPath + File.separator + "Reports";
	private String reportConfigPath;

	/**
	 * Method for Driver(Browser) Instance and Launch Url in Browser
	 * 
	 * @param browserName
	 * @param url
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public WebDriver launchBrowser(String browserName, String url) throws Exception {
		if (browserName.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		} else if (browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			System.out.println("FireFox Browser is Launched");
		} else if (browserName.equalsIgnoreCase("safari")) {
			// Note Should AllowRemoteAutomation in safari browser DeveloperMenu
			// Directions -- > launchSafariBrowser --> Preferences --> Advanced Tab -->
			// Show Developer Menu --> Click on DevloperMenu --> Enable
			// AllowRemoteAutomation
			driver = new SafariDriver();
			System.out.println("Safari Browser is Launched");
		} else if (browserName.equalsIgnoreCase("ie")) {
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			WebDriverManager.iedriver().setup();
			driver = new InternetExplorerDriver(capabilities);
			System.out.println("IE Browser is Launched");
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(url);
		return driver;
	}


	/**
	 * Method for Explicit wait(120 seconds)
	 * 
	 * @param driver
	 * @param locator
	 * @return
	 */
	public WebElement waitForExpectedElement(WebDriver driver, final By locator) {
		WebDriverWait wait = new WebDriverWait(driver, 120);
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	/**
	 * extent-config.xml file path
	 * 
	 * @return
	 */
	public String getReportConfigPath() {
		reportConfigPath = System.getProperty("user.dir") + File.separator + "extent-config.xml";
		if (reportConfigPath != null) {
			return reportConfigPath;
		} else {
			throw new RuntimeException(
					"Report Config Path not specified in the Configuration.properties file for the Key:reportConfigPath");
		}
	}
	
	/**
	 * Driver(Browser) Instance Close
	 * 
	 * @param driver
	 */
	public void tearDown(WebDriver driver) {
		driver.quit();
	}

	/**
	 * Add Screenshot to Report file
	 * 
	 * @param driver
	 * @param screenshotName
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void report(WebDriver driver, String screenshotName) throws Exception {
		String html = "";
		try {
			// This takes a screenshot from the driver at save it to the specified location
			File sourcePath = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			File destinationPath = new File(screenShotFilePath + File.separator + screenshotName + ".png");
			Files.copy(sourcePath, destinationPath); // This attach the specified screenshot to the test
			Reporter.addStepLog("Screenshot is taken : " + screenshotName + "\n" + covertScreenshotToBase64(destinationPath));
		} catch (IOException e) {
		}
	}

	/**
	 * ConvertImage to Base64 format
	 * 
	 * @param file
	 * @return
	 */
	@SuppressWarnings("resource")
	public static String covertScreenshotToBase64(File file) {
		try {
			FileInputStream fis = new FileInputStream(file);
			byte byteArray[] = new byte[(int) file.length()];
			fis.read(byteArray);
			String imageString = Base64.encodeBase64String(byteArray);
			return doImageClickAnimation(imageString, "Test");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	/**
	 * To click on image in report file
	 * 
	 * @param img
	 * @param screenName
	 * @return
	 */
	public static String doImageClickAnimation(String img, String screenName) {
		String image = "<img onclick='clickImage(this)' src=\"data:image/png;base64, " + img + "\" alt=\"" + screenName
				+ "\" width=\"710\" height=\"450\"/>";
		return image;
	}

	/**
	 * Create Directory
	 * 
	 * @param directory
	 * @return
	 */
	public static boolean createDirectory(String directory) {
		File fileDirectory = new File(directory);
		if (!fileDirectory.exists()) {
			fileDirectory.mkdir();
			return true;
		}
		return false;
	}

	/**
	 * Add Screenshot
	 * 
	 * @param file
	 * @return
	 */
	public String addScreenshot(File file) {
		String encodedBase64 = null;
		FileInputStream fileInputStreamReader = null;
		try {
			fileInputStreamReader = new FileInputStream(file);
			byte[] bytes = new byte[(int) file.length()];
			fileInputStreamReader.read(bytes);
			encodedBase64 = new String(Base64.encodeBase64(bytes));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return encodedBase64;
	}
}
