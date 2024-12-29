@tag
Feature: Login functionality

  @tag2
  Scenario: User logs in with valid credentials
    Given User is in Login Page
    And User Clicks on Signup  Login button
    And Verify 'Login to your account' is visible
    When User enters correct email address and password
    And Click login button
    And Verify that 'Logged in as username' is visible

