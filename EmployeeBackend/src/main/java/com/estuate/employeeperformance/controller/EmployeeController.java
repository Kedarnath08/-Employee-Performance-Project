package com.estuate.employeeperformance.controller;

import com.estuate.employeeperformance.model.Employee;
import com.estuate.employeeperformance.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    // Get all employees
    @GetMapping
    public List<Employee> getAllEmployees() {
        logger.info("API called: GET /employees");
        return employeeService.getAllEmployees();
    }

    // Add a new employee
    @PostMapping
    public Employee saveEmployee(@RequestBody Employee employee) {
        logger.info("API called: POST /employees - Employee: {}", employee.getName());
        return employeeService.saveEmployee(employee);
    }

    // Get count of employees per rating
    @GetMapping("/rating-count")
    public Map<String, Integer> getRatingCount() {
        logger.info("API called: GET /employees/rating-count");
        return employeeService.getRatingCount();
    }

    // Get percentage of employees in each rating category
    @GetMapping("/rating-percentage")
    public Map<String, Double> getRatingPercentage() {
        logger.info("API called: GET /employees/rating-percentage");
        return employeeService.getRatingPercentage();
    }

    // Get deviation from standard percentages
    @GetMapping("/deviation")
    public Map<String, Integer> getDeviation() {
        logger.info("API called: GET /employees/deviation");
        return employeeService.getDeviationFromStandard();
    }

    // Get employees whose ratings should be revised
    @GetMapping("/revise-ratings")
    public List<Employee> getEmployeesForRatingRevision() {
        logger.info("API called: GET /employees/revise-ratings");
        return employeeService.getEmployeesForRevision();
    }
}
