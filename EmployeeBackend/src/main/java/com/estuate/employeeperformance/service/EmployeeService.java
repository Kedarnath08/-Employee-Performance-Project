package com.estuate.employeeperformance.service;

import com.estuate.employeeperformance.model.Employee;
import com.estuate.employeeperformance.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    private static final Map<String, Double> STANDARD_PERCENTAGES = Map.of(
            "A", 10.0, "B", 20.0, "C", 40.0, "D", 20.0, "E", 10.0);

    public List<Employee> getAllEmployees() {
        logger.info("Fetching all employees");
        return employeeRepository.findAll();
    }

    public Employee saveEmployee(Employee employee) {
        logger.info("Saving new employee: {}", employee.getName());
        return employeeRepository.save(employee);
    }

    public Map<String, Integer> getRatingCount() {
        logger.info("Fetching rating count for employees");
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .collect(Collectors.groupingBy(Employee::getRating, Collectors.summingInt(e -> 1)));
    }

    public Map<String, Double> getRatingPercentage() {
        logger.info("Calculating rating percentage for employees");
        List<Employee> employees = employeeRepository.findAll();
        int totalEmployees = employees.size();

        if (totalEmployees == 0) {
            logger.warn("No employees found, returning empty percentage map.");
            return Collections.emptyMap();
        }

        Map<String, Long> ratingCounts = employees.stream()
                .collect(Collectors.groupingBy(Employee::getRating, Collectors.counting()));

        Map<String, Double> percentageMap = new HashMap<>();
        for (Map.Entry<String, Long> entry : ratingCounts.entrySet()) {
            double percentage = (entry.getValue() * 100.0) / totalEmployees;
            percentageMap.put(entry.getKey(), percentage);
        }
        return percentageMap;
    }

    public Map<String, Integer> getExpectedRatingCount(int totalEmployees) {
        Map<String, Integer> expectedCounts = new HashMap<>();
        for (Map.Entry<String, Double> entry : STANDARD_PERCENTAGES.entrySet()) {
            int expected = (int) Math.round((entry.getValue() / 100) * totalEmployees);
            expectedCounts.put(entry.getKey(), expected);
        }
        return expectedCounts;
    }

    public Map<String, Integer> getDeviationFromStandard() {
        logger.info("Calculating deviation from standard rating distribution");
        Map<String, Integer> actualCounts = getRatingCount();
        int totalEmployees = actualCounts.values().stream().mapToInt(Integer::intValue).sum();
        Map<String, Integer> expectedCounts = getExpectedRatingCount(totalEmployees);

        Map<String, Integer> deviation = new HashMap<>();
        for (String rating : STANDARD_PERCENTAGES.keySet()) {
            int actual = actualCounts.getOrDefault(rating, 0);
            int expected = expectedCounts.getOrDefault(rating, 0);
            deviation.put(rating, actual - expected);
        }
        return deviation;
    }

    public List<Employee> getEmployeesForRevision() {
        logger.info("Fetching employees for rating revision");

        List<Employee> employeesForRevision = new ArrayList<>();
        List<Employee> allEmployees = employeeRepository.findAll();

        Map<String, Integer> targetCounts = Map.of(
                "A", 1, "B", 3, "C", 6, "D", 3, "E", 2 // Follow expected logic
        );

        Map<String, List<Employee>> employeesByRating = allEmployees.stream()
                .collect(Collectors.groupingBy(Employee::getRating));

        Map<String, Integer> currentCounts = employeesByRating.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> ((List<Employee>) entry.getValue()).size()));

        logger.info("Current Rating Counts: {}", currentCounts);

        List<String> overpopulated = new ArrayList<>();
        List<String> underpopulated = targetCounts.entrySet().stream()
                .filter(entry -> currentCounts.getOrDefault(entry.getKey(), 0) < entry.getValue())
                .sorted(Comparator
                        .comparingInt((Map.Entry<String, Integer> entry) -> entry.getValue()
                                - currentCounts.getOrDefault(entry.getKey(), 0)) // Sort by shortage
                        .reversed()) // Highest shortage first
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        for (String rating : targetCounts.keySet()) {
            int actual = currentCounts.getOrDefault(rating, 0);
            int expected = targetCounts.get(rating);

            if (actual > expected)
                overpopulated.add(rating);
            if (actual < expected)
                underpopulated.add(rating);
        }

        logger.info("Overpopulated Categories: {}", overpopulated);
        logger.info("Underpopulated Categories: {}", underpopulated);

        // If no categories need adjustment, return an empty list
        if (overpopulated.isEmpty() || underpopulated.isEmpty()) {
            logger.info("No rating revision needed.");
            return employeesForRevision;
        }

        balanceRatings(employeesByRating, overpopulated, underpopulated, employeesForRevision, currentCounts,
                targetCounts);

        logger.info("Employees Suggested for Revision: {}", employeesForRevision);

        return employeesForRevision;
    }

    private void balanceRatings(
            Map<String, List<Employee>> employeesByRating,
            List<String> overpopulated,
            List<String> underpopulated,
            List<Employee> employeesForRevision,
            Map<String, Integer> currentCounts,
            Map<String, Integer> targetCounts) {

        Iterator<String> underIt = underpopulated.iterator();

        for (String overCat : overpopulated) {
            List<Employee> surplusEmployees = new ArrayList<>(
                    employeesByRating.getOrDefault(overCat, new ArrayList<>()));

            while (currentCounts.get(overCat) > targetCounts.get(overCat) && underIt.hasNext()) {
                Employee emp = surplusEmployees.remove(0);
                String newCategory = underIt.next();

                // Debug: Log who is being moved
                logger.info("Moving Employee: {} from {} to {}", emp.getName(), overCat, newCategory);

                emp.setSuggestedNewRating(newCategory);
                employeesForRevision.add(emp);

                currentCounts.put(overCat, currentCounts.get(overCat) - 1);
                currentCounts.put(newCategory, currentCounts.get(newCategory) + 1);

                if (currentCounts.get(newCategory) >= targetCounts.get(newCategory)) {
                    underIt.remove();
                }
            }
        }
    }

}
