@tag
Feature: Login functionality

  @tag2
  Scenario Outline: User logs in with valid credentials
    Given User is in Login Page
    When User enters correct email address and password 
    And Click login button
    Then I verify the <status> in stp

    Examples: 
      | name  | value | status  |
      | name1 |     5 | success |
      | name2 |     7 | Fail    |
