package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utils.DriverConfig;

public class HomePage {

	public WebDriver driver;

	// Constructor to initialize WebDriver from DriverConfig
	public HomePage() {
		// Get the thread-local driver from DriverConfig
		this.driver = DriverConfig.getDriver();
		// Initialize elements using PageFactory
		PageFactory.initElements(driver, this);
	}

	// PageFactory annotations to find the web elements
	@FindBy(className = "title")
	public WebElement pageTitle;

	@FindBy(xpath = "//*[@href='/login']")
	public WebElement loginPageButton;

}
