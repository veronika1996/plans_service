package com.plans_service.cucumber.glue;

import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

@CucumberContextConfiguration
@ActiveProfiles("test")
public class SpringIntegrationTest {

  @Test
  public void test() {
    System.out.println("Running Cucumber tests...");
  }
}
