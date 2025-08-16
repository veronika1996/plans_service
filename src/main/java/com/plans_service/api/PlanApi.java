package com.plans_service.api;

import com.plans_service.dto.PlanDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface PlanApi {

  @Operation(
      summary = "Create or update a plan for the day",
      description = "This endpoint allows you to create or update a plan for the day.",
      responses = {
        @ApiResponse(
            responseCode = "201",
            description = "Plan successfully created",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PlanDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input")
      })
  ResponseEntity<PlanDTO> createOrUpdatePlan(
      @Parameter(description = "Plan to be created") @RequestBody PlanDTO planDTO);

  @Operation(
      summary = "Get a plan by date",
      description =
          "This endpoint allows you to get a plan for your user and date for which the plan is created.",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Plan retrieved successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PlanDTO.class))),
        @ApiResponse(responseCode = "404", description = "Plan not found")
      })
  ResponseEntity<PlanDTO> getPlanByDate(
      @Parameter(description = "Date of the plan to be fetched") @RequestParam String date,
      @Parameter(description = "Username for which the plan is created") @RequestParam
          String username);

}
