package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utils.DriverConfig;

public class LoginPage {

	public WebDriver driver;

	// Constructor to initialize WebDriver from DriverConfig
	public LoginPage() {
		// Get the thread-local driver from DriverConfig
		this.driver = DriverConfig.getDriver();
		// Initialize elements using PageFactory
		PageFactory.initElements(driver, this);
	}

	// PageFactory annotations to find the web elements
	
	@FindBy(name = "email")
	public WebElement emailidField;

	@FindBy(name = "password")
	public WebElement passwordField;

	@FindBy(className = "title")
	public WebElement pageTitle;

	@FindBy(xpath = "//*[text()='Login']")
	public WebElement loginButton;

	@FindBy(xpath = "//h2[text()='Login to your account']")
	public WebElement loginToAccountText;
	
	@FindBy (xpath = "//a[contains(.,'Logged in as')]")
	public WebElement loggedInAsUsernameText;
}
