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

	// Logger for better control over logs
	private static final Logger logger = Logger.getLogger(WebDriverManager.class.getName());

	// ThreadLocal driver for thread safety in parallel execution
	private static final ThreadLocal<RemoteWebDriver> threadDriver = new ThreadLocal<>();
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

			// Initialize the browser based on the property file
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

			// Set the thread-local driver
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

}
