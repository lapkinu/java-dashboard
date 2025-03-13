package com.javarush.lapkinu.dashboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RestController
public class HealthCheckController {

    @Autowired
    private DataSource dataSource;

    @GetMapping("/health")
    public String checkDbConnection() {
        try (Connection conn = dataSource.getConnection()) {
            return "Database connection OK!";
        } catch (SQLException e) {
            return "Connection failed: " + e.getMessage();
        }
    }
}
