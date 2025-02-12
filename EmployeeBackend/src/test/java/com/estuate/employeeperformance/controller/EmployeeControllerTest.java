package com.estuate.employeeperformance.controller;

import com.estuate.employeeperformance.model.Employee;
import com.estuate.employeeperformance.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("removal")
    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    private Employee sampleEmployee;

    @BeforeEach
    void setUp() {
        // ✅ Updated constructor to match Employee.java
        sampleEmployee = new Employee("123", 101, "John Doe", "A", "B");
    }

    // Test GET /employees
    @Test
    void testGetAllEmployees() throws Exception {
        List<Employee> employees = Arrays.asList(sampleEmployee);
        when(employeeService.getAllEmployees()).thenReturn(employees);

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(employees)));
    }

    // Test POST /employees
    @Test
    void testSaveEmployee() throws Exception {
        when(employeeService.saveEmployee(Mockito.any(Employee.class))).thenReturn(sampleEmployee);

        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sampleEmployee)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(sampleEmployee)));
    }

    // Test GET /employees/rating-count
    @Test
    void testGetRatingCount() throws Exception {
        Map<String, Integer> ratingCount = new HashMap<>();
        ratingCount.put("A", 3);
        ratingCount.put("B", 5);
        when(employeeService.getRatingCount()).thenReturn(ratingCount);

        mockMvc.perform(get("/employees/rating-count"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(ratingCount)));
    }

    // Test GET /employees/rating-percentage
    @Test
    void testGetRatingPercentage() throws Exception {
        Map<String, Double> ratingPercentage = new HashMap<>();
        ratingPercentage.put("A", 20.0);
        ratingPercentage.put("B", 33.3);
        when(employeeService.getRatingPercentage()).thenReturn(ratingPercentage);

        mockMvc.perform(get("/employees/rating-percentage"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(ratingPercentage)));
    }

    // Test GET /employees/deviation
    @Test
    void testGetDeviation() throws Exception {
        Map<String, Integer> deviation = new HashMap<>();
        deviation.put("A", -1);
        deviation.put("B", 2);
        when(employeeService.getDeviationFromStandard()).thenReturn(deviation);

        mockMvc.perform(get("/employees/deviation"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(deviation)));
    }

    // Test GET /employees/revise-ratings
    @Test
    void testGetEmployeesForRatingRevision() throws Exception {
        List<Employee> revisionList = Collections.singletonList(sampleEmployee);
        when(employeeService.getEmployeesForRevision()).thenReturn(revisionList);

        mockMvc.perform(get("/employees/revise-ratings"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(revisionList)));
    }
}
