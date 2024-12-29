package stepDefinition;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.HomePage;
import pages.LoginPage;
import utils.DriverConfig;
import utils.TechnicalReusables;

public class LoginSteps extends DriverConfig {

	WebDriver driver = DriverConfig.getDriver(); // Thread-safe driver
    HomePage homePage = new HomePage(); // Home Page Object
    LoginPage loginPage = new LoginPage(); // Login Page Object
    TechnicalReusables technicalReusables = new TechnicalReusables(); // Reusable Methods

    @Given("User is in Login Page")
    public void user_is_in_home_page() {
        driver.get("https://automationexercise.com"); // Replace with actual home page URL
        technicalReusables.waitForPageLoad(10);
    }
    
    @Given("User Clicks on Signup  Login button")
    public void user_navigates_to_login_page_by_clicking_signup_login_button() {
        homePage.loginPageButton.click();
        technicalReusables.waitForPageLoad(5);
    }
    
    @Given("Verify 'Login to your account' is visible")
    public void verify_login_to_your_account_is_visible() {
        String headerText = loginPage.loginToAccountText.getText();
        Assert.assertEquals(headerText, "Login to your account");
    }
    
    @When("User enters correct email address and password")
    public void user_enters_correct_email_address_and_password() {
        loginPage.emailidField.sendKeys("musthaq53ahamed@gmail.com"); // Replace with actual email
        loginPage.passwordField.sendKeys("test1"); // Replace with actual password
    }
    
    @When("Click login button")
    public void click_login_button() {
        loginPage.loginButton.click();
        technicalReusables.waitForPageLoad(5);
    }
    
    @Then("Verify that 'Logged in as username' is visible")
    public void verify_logged_in_as_username_is_visible() {
        String loggedInMessage = loginPage.loggedInAsUsernameText.getText();
        Assert.assertEquals(loggedInMessage, "Logged in as test1");
    }
}
