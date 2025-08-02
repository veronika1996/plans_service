Feature: Get a plan by date and username

  Background:
    Given the system is initialized

  @getByDateAndUsername
  Scenario: Successfully retrieve a plan by date and username
    When I send a GET request to "/plans?username=Admin&date=2025-08-28"
    Then I should receive a 200 response
    And the plan response should be like the plan data in JSON file "/responses/get_plan_by_name_and_username.json"

  @error
  Scenario: Get a non-existing plan for given username
    When I send a GET request to "/plans?username=Admin1&date=2025-08-28"
    Then I should receive a 404 response
    And the response should contain error message "Plan nije pronadjen za datum: 2025-08-28"

  @error
  Scenario: Get a non-existing plan for given date
    When I send a GET request to "/plans?username=Admin&date=2025-08-27"
    Then I should receive a 404 response
    And the response should contain error message "Plan nije pronadjen za datum: 2025-08-27"