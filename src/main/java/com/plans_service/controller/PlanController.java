package com.plans_service.controller;

import com.plans_service.api.PlanApi;
import com.plans_service.dto.PlanDTO;
import com.plans_service.service.PlanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/plans")
public class PlanController implements PlanApi {

  private final PlanService planService;

  public PlanController(PlanService planService) {
    this.planService = planService;
  }

  @PostMapping
  public ResponseEntity<PlanDTO> createOrUpdatePlan(PlanDTO planDTO) {
    PlanDTO response = planService.createOrUpdatePlan(planDTO);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(response);
  }

  @GetMapping
  public ResponseEntity<PlanDTO> getPlanByDate(String date, String username) {
   PlanDTO response = planService.getPlanByDate(date, username);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(response);
  }
}
