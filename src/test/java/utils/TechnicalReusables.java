package utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.Scenario;

public class TechnicalReusables {
	public WebDriver driver;

	// Constructor to initialize WebDriver from DriverConfig
	public TechnicalReusables() {
		// Get the thread-local driver from DriverConfig
		this.driver = DriverConfig.getDriver();
	}

	public static WebDriverWait wait;

	public void waitUntilElementVisible(WebElement Element, int seconds) {
		wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
		wait.until(ExpectedConditions.visibilityOf(Element));
	}

	// Wait until an element is clickable
	public void waitUntilElementClickable(WebElement element, int timeoutInSeconds) {
		wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	// Send text to an input field
	public void sendTextToField(WebElement element, String text) {
		waitUntilElementVisible(element, 10);
		element.clear(); // Clear any pre-existing text
		element.sendKeys(text);
	}

	// Click on an element
	public void clickElement(WebElement element) {
		waitUntilElementClickable(element, 10);
		element.click();
	}

	// Capture a screenshot during test failure
	public void takeScreenshot(Scenario scenario) {
		if (scenario.isFailed()) {
			TakesScreenshot ts = (TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);
			String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
			String destPath = "target/screenshots/" + scenario.getName() + "_" + timestamp + ".png";
			File destination = new File(destPath);
			try {
				FileUtils.copyFile(source, destination);
				scenario.attach(FileUtils.readFileToByteArray(destination), "image/png", scenario.getName());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// Get page title
	public String getPageTitle() {
		return driver.getTitle();
	}

	// Check if an element is displayed
	public boolean isElementDisplayed(WebElement element) {
		try {
			return element.isDisplayed();
		} catch (StaleElementReferenceException e) {
			return false;
		}
	}

	public void waitForPageLoad(int timeoutInSeconds) {
        try {
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            for (int i = 0; i < timeoutInSeconds; i++) {
                // Check if the document is in a complete state
                if ("complete".equals(jsExecutor.executeScript("return document.readyState"))) {
                    return; // Page has fully loaded
                }
                Thread.sleep(1000); // Wait for 1 second before checking again
            }
            throw new RuntimeException("Page did not load completely within " + timeoutInSeconds + " seconds.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while waiting for page to load.", e);
        }
    }
	
}
