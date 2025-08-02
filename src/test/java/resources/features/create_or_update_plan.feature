Feature: Create or update a new plan

  @create
  Scenario: Successfully create a new plan
    Given the plan data in JSON file "requests/create_plan_request.json"
    And the database table is empty
    When I send a POST request to "/plans"
    Then I should receive a 201 response
    And the plan response should be like the plan data in JSON file "/responses/create_plan_response.json"

  @update
  Scenario: Successfully update existing plan
    Given the system is initialized
    And the plan data in JSON file "/requests/update_plan_request.json"
    When I send a POST request to "/plans"
    Then I should receive a 201 response
    And the plan response should be like the plan data in JSON file "/responses/update_plan_response.json"

  @error
  Scenario: Create plan causes error when username is empty
    Given the plan data in JSON file "/requests/create_recipe_request_missing_username.json"
    And the database table is empty
    When I send a POST request to "/plans"
    Then I should receive a 400 response
    And the response should contain error message "Username cannot be empty"

  @error
  Scenario: Create plan causes error when date is empty
    Given the plan data in JSON file "/requests/create_recipe_request_missing_date.json"
    And the database table is empty
    When I send a POST request to "/plans"
    Then I should receive a 400 response
    And the response should contain error message "Date cannot be null"

  Scenario: Update plan causes error when username is empty
    Given the system is initialized
    And the plan data in JSON file "/requests/update_recipe_request_missing_username.json"
    When I send a POST request to "/plans"
    Then I should receive a 400 response
    And the response should contain error message "Username cannot be empty"

  Scenario: Update plan causes error when date is empty
    Given the system is initialized
    And the plan data in JSON file "/requests/update_recipe_request_missing_date.json"
    When I send a POST request to "/plans"
    Then I should receive a 400 response
    And the response should contain error message "Date cannot be null"
