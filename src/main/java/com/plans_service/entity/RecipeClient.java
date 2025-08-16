package com.plans_service.entity;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RecipeClient {

  private final RestTemplate restTemplate;

  @Value("${recipes.service.url}")
  private String recipesServiceUrl;

  public RecipeClient(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public Integer getRecipesCalories(Long id) {
    return restTemplate.postForObject(
        recipesServiceUrl + "/recipes/calorie", id, Integer.class
    );
  }

  public Integer calculateConsumedCalories(List<Long> ids) {
    return restTemplate.postForObject(
        recipesServiceUrl + "/recipes/calories", ids, Integer.class
    );
  }

}