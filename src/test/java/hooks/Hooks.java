package hooks;

import java.io.IOException;
import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import pages.HomePage;
//import pages.LoginFunctionalityPages;
import utils.DriverConfig;
import utils.TechnicalReusables;

public class Hooks {

	// Initialize objects
	DriverConfig driverConfig = new DriverConfig();
	HomePage homePage;
	TechnicalReusables technicalReusables = new TechnicalReusables();
	// ReusableMethods reusableMethods = new ReusableMethods();
	// Logger instance for logging
	private static final Logger logger = Logger.getLogger(Hooks.class.getName());

	@Before
	public void invokeURL(Scenario scenario) throws IOException {

		logger.info("Starting scenario: " + scenario.getName());
		try {
			// Initialize WebDriver using DriverConfig
			driverConfig.invokeBrowser();

			// Fetch WebDriver instance using thread-safe method
			WebDriver driver = DriverConfig.getDriver();
			homePage = new HomePage();

			technicalReusables.waitUntilElementVisible(homePage.pageTitle, 10);
			//Assert.assertEquals(homePage.pageTitle.getText(), "Automation Exercise");

			logger.info("Successfully launched the application: Swag Labs");

		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error in scenario setup: " + scenario.getName(), e);
		}
	}

	@After
	public void tearDown(Scenario scenario) {
		try {
            // Close the browser using DriverConfig
            driverConfig.closeBrowser();
            logger.info("Browser closed successfully for scenario: " + scenario.getName());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error during browser shutdown: " + scenario.getName(), e);
        }
	}

	@AfterStep
	public void screenshot(Scenario scenario) {
		if (scenario.isFailed()) {
            logger.warning("Scenario failed, taking screenshot: " + scenario.getName());
            //RM.takeScreenshot(scenario);
        }
	}
	
	// Optionally, you can add custom methods for reusable functionality
    private void logScenarioStart(Scenario scenario) {
        logger.info("Starting scenario: " + scenario.getName());
    }

    private void logScenarioEnd(Scenario scenario) {
        logger.info("Ending scenario: " + scenario.getName());
    }
}
