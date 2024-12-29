package utils;

import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

/*Purpose
The DriverConfig class is a utility to configure and manage WebDriver 
instances for automated browser testing. It:

- Reads browser and URL configurations from a property file.
- Initializes the WebDriver instance for the specified browser.
- Sets the driver instance in a thread-safe manner for parallel test execution.
- Manages browser window state and implicit wait settings.*/

public class DriverConfig {

	// A Logger instance is used for structured logging.
	// It records informational messages and errors during browser setup and
	// execution.
	private static final Logger logger = Logger.getLogger(WebDriverManager.class.getName());

	// ThreadLocal ensures that each thread running parallel tests gets its own WebDriver instance.
	// This prevents interference between threads, making the code thread-safe.
	private static final ThreadLocal<RemoteWebDriver> threadDriver = new ThreadLocal<>();

	// Reads configuration values (like browser type and URL) from a
	// config.properties file
	// located in the test resources directory.
	// Uses the Properties class to load key-value pairs
	private static FileReader fileReader;
	private static Properties properties;

	// Get the thread-safe driver instance
	public static RemoteWebDriver getDriver() {
		return threadDriver.get();
	}

	public void invokeBrowser() throws IOException {

		try {
			// Load values from the property file
			String filePath = System.getProperty("user.dir") + "\\src\\test\\resources\\config.properties";
			fileReader = new FileReader(filePath);
			properties = new Properties();
			properties.load(fileReader);

			// Determines the browser type (Chrome, Firefox, Edge) from the property file.
			// Uses Bonigarcia’s WebDriverManager to dynamically download and set up the
			// required WebDriver executable.
			// Instantiates the appropriate browser-specific WebDriver.
			String browser = properties.getProperty("Browser").toLowerCase();
			RemoteWebDriver driver;
			switch (browser) {
			case "chrome":
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
				break;
			case "firefox":
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
				break;
			case "edge":
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();
				break;
			default:
				throw new IllegalArgumentException("The browser " + browser + " is not supported.");
			}
			
			// Stores the initialized WebDriver instance in the thread-local variable to
			// ensure thread-safe access.
			threadDriver.set(driver);

			// Maximize the browser window
			getDriver().manage().window().maximize();

			// Launch the URL
			String url = properties.getProperty("URL");
			getDriver().get(url);

			// Set implicit wait
			getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

			logger.info("Browser initialized successfully: " + browser);

		} catch (IOException e) {
			logger.log(Level.SEVERE, "Error loading configuration file: " + e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error initializing browser: " + e.getMessage(), e);
			throw new RuntimeException("Browser setup failed.", e);
		} finally {
			// Ensure resources are closed
			if (fileReader != null) {
				fileReader.close();
			}
		}
	}
	
	public void closeBrowser() {
		if (getDriver() != null) {
			getDriver().quit();
			threadDriver.remove();
			logger.info("Browser closed successfully.");
		}
	}
}
