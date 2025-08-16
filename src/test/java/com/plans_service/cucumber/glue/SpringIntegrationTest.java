package com.plans_service.cucumber.glue;

import com.plans_service.PlansServiceApplication;
import com.plans_service.config.GlobalExceptionHandler;
import com.plans_service.config.SecurityConfig;
import com.plans_service.entity.RecipeClient;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@CucumberContextConfiguration
@SpringBootTest(classes = {PlansServiceApplication.class, SecurityConfig.class,
    GlobalExceptionHandler.class,
    RecipeClient.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class SpringIntegrationTest {

    @MockitoBean
    private RecipeClient recipeClient;

    @Test
    public void test() {
        System.out.println("Running Cucumber tests...");
    }
}
