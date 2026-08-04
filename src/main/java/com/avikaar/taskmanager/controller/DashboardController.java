package com.avikaar.taskmanager.controller;

import com.avikaar.taskmanager.dto.DashboardStats;
import com.avikaar.taskmanager.entity.User;
import com.avikaar.taskmanager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final TaskService taskService;

    @GetMapping("/stats")
    public ResponseEntity<DashboardStats> getDashboardStats(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(taskService.getDashboardStats(user));
    }
}
