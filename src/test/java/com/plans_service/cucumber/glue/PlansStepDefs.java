package com.plans_service.cucumber.glue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.plans_service.dto.ErrorDto;
import com.plans_service.dto.PlanDTO;
import com.plans_service.entity.PlanEntity;
import com.plans_service.entity.PlanRecipeEntity;
import com.plans_service.entity.RecipeClient;
import com.plans_service.enums.RecipeCategory;
import com.plans_service.repository.PlanRepository;
import com.plans_service.service.PlanService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@ActiveProfiles("test")
public class PlansStepDefs {

  private static final String BASE_PATH = "http://localhost:" + 8085 + "/meal_plan";
  private static final String RESOURCES_PATH = "src/test/java/resources/";
  private final ObjectMapper objectMapper = new ObjectMapper();
  private final RestTemplate restTemplate = new RestTemplate();
  List<Long> byIdsRequest = new ArrayList<>();
  @Autowired
  private PlanRepository planRepository;
  @Autowired
  private PlanService planService;
  @Autowired
  private RecipeClient recipeClient;
  private ResponseEntity<String> response;
  private ResponseEntity<Integer> responseInteger;
  private PlanDTO planDTO;
  private Long planId;

  @When("I send a POST request to {string}")
  public void iSendAPostRequestTo(String path) {
    when(recipeClient.getRecipesCalories(20L)).thenReturn(
        500);
    when(recipeClient.calculateConsumedCalories(List.of(20L))).thenReturn(
        500);
    when(recipeClient.getRecipesCalories(25L)).thenReturn(
        600);
    when(recipeClient.calculateConsumedCalories(List.of(25L, 20L))).thenReturn(
        1100);
    String url = BASE_PATH + path;
    try {
      response = restTemplate.postForEntity(url, planDTO, String.class);
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      response = ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
    }
  }

  @Given("the system is initialized")
  public void theSystemIsInitialized() {
    planRepository.deleteAll();
    PlanEntity entity = new PlanEntity(null, "Admin", LocalDate.of(2025, 8, 28), 500);
    PlanRecipeEntity rie = new PlanRecipeEntity();
    rie.setRecipeId(20L);
    rie.setPlan(entity);
    rie.setRecipeCategory(RecipeCategory.BREAKFAST);
    entity.setPlanRecipes(List.of(rie));
    PlanEntity savedEntity = planRepository.save(entity);

    planId = savedEntity.getId();
  }

  @Given("the plan data in JSON file {string}")
  public void thePlanDataInJsonFile(String fileName) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());

    // Load the JSON file from the resources folder
    planDTO =
        objectMapper.readValue(
            new File(RESOURCES_PATH + fileName), PlanDTO.class);
  }

  @When("I send a DELETE request to {string}")
  public void iSendADELETERequestTo(String path) {
    String url = BASE_PATH + path;

    try {
      response = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      response = ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
    }
  }

  @Then("I should receive a {int} response")
  public void iShouldReceiveAResponse(int statusCode) {
    assertEquals(HttpStatus.valueOf(statusCode), response.getStatusCode());
  }

  @And("the plan response should be like the plan data in JSON file {string}")
  public void thePlanResponseShouldBeLikeThePlanDataInJsonFile(String fileName)
      throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    PlanDTO expectedPlanDTO =
        objectMapper.readValue(
            new File(RESOURCES_PATH + fileName), PlanDTO.class);
    PlanDTO actualPlanDTO =
        objectMapper.readValue(response.getBody(), PlanDTO.class);

    assertEquals(expectedPlanDTO.getUsername(), actualPlanDTO.getUsername());
    assertEquals(expectedPlanDTO.getPlanRecipes().size(), actualPlanDTO.getPlanRecipes().size());
    assertEquals(expectedPlanDTO.getConsumedCalories(), actualPlanDTO.getConsumedCalories());
    assertEquals(expectedPlanDTO.getDate(), actualPlanDTO.getDate());
  }


  @Then("the response should contain error message {string}")
  public void theResponseShouldContainErrorMessage(String errorMessage) throws IOException {
    ErrorDto errorDto = objectMapper.readValue(response.getBody(), ErrorDto.class);
    assertEquals(errorMessage, errorDto.getMessage());
  }

  @And("the plan {string} should be deleted from the system")
  public void thePlanShouldBeDeletedFromTheSystem(String planName) {
    try {
      // Try to get the plan after deletion
      ResponseEntity<String> getResponse =
          restTemplate.exchange(BASE_PATH + planName, HttpMethod.GET, null, String.class);
      // Expect 404 response when plan is not found
      assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
    } catch (Exception e) {
      // Plan is deleted, so no entity is found, and exception will be thrown
    }
  }

  @When("I send a GET request to {string}")
  public void iSendAGETRequestTo(String path) {
    String url = BASE_PATH + path;
    when(recipeClient.getRecipesCalories(20L)).thenReturn(
        500);
    when(recipeClient.calculateConsumedCalories(List.of(20L))).thenReturn(
        500);
      try {
      response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      response = ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
    }
  }

  @Given("the plan list is empty")
  public void thePlanListIsEmpty() {
    planRepository.deleteAll();
  }

  @And("the response should contain an empty list")
  public void theResponseShouldContainAnEmptyList() throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    List<PlanDTO> plans =
        objectMapper.readValue(response.getBody(), new TypeReference<List<PlanDTO>>() {
        });

    assertTrue(plans == null || plans.isEmpty());
  }

  @And("the response should contain {int} plan")
  public void theResponseShouldContainNumberPlan(int size) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    List<PlanDTO> plans =
        objectMapper.readValue(response.getBody(), new TypeReference<List<PlanDTO>>() {
        });

    assertEquals(plans.size(), size);
  }

  @And("the response should be list of data as in JSON file {string}")
  public void theResponseShouldBeListOfDataAsInJSONFile(String filePath) {
    try {
      String expectedJson =
          new String(Files.readAllBytes(Paths.get(RESOURCES_PATH + filePath)));

      String actualJson = response.getBody();
      System.out.println("Actual " + actualJson);

      JSONAssert.assertEquals(
          expectedJson, actualJson, false);

      System.out.println("Expected " + expectedJson);

    } catch (Exception e) {
      throw new RuntimeException("Error during JSON comparison: " + e.getMessage(), e);
    }
  }

  @When("I send a DELETE by id request")
  public void iSendADELETEByIdRequest() {
    String url =  BASE_PATH + "/plans?id=" + planId;

    try {
      response = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      response = ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
    }
  }

  @And("the response should be {int}")
  public void theResponseShouldBe(int result) {
    assertEquals(result, responseInteger.getBody().intValue());
  }

  @Then("I should receive as integer and code is {int}")
  public void iShouldReceiveAsIntegerAndCodeIs(int statusCode) {
    assertEquals(HttpStatus.valueOf(statusCode), responseInteger.getStatusCode());
  }

  @When("I send a POST request by ids")
  public void iSendAPOSTRequestByIds() {
    String url = BASE_PATH + "/plans/calories";

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<List<Long>> requestEntity =
        new HttpEntity<>(byIdsRequest, headers);

    responseInteger = restTemplate.postForEntity(
        url,
        requestEntity,
        Integer.class
    );
  }

  @And("the database table is empty")
  public void theDatabaseTableIsEmpty() {
    planRepository.deleteAll();
  }

}
