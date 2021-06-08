@RegressionTests

Feature: User
  Testing User API for the COVID game

 Background:
   Given I have an API Service user

Scenario: Get All users
  When I retrieve user api
  Then 200 response code is returned
  And all the users registered in the game are returned

 Scenario: Add a new user successfully
    When I add a new user with name TestUser_<RandomNumber>
    Then 201 response code is returned
    And new user is added

 Scenario: Edit an existing user
    When I update an existing user TestUser with score 100
    Then 204 response code is returned
    And user is updated

   Scenario Outline: Validations while adding user
     When I add a new user with name <Username>
     Then 400 response code is returned
     Examples:
     | Description                                    | Username      |
     | Add New User with Existing Username            | TestUser      |
#     | Add new user with Null username -- Defect 1    |               |


  Scenario: Validations while adding user without score
    When add a new user with name TestUser11235 without score
    Then 400 response code is returned

    # Defect 2 -returning 503 instead of 400 Bad Request
   Scenario: Validations while editing user without score
      When I update an existing user TestUser11233 without score
      Then 400 response code is returned








