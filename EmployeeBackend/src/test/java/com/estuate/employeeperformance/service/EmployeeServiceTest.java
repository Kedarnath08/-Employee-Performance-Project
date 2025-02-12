package com.estuate.employeeperformance.service;

import com.estuate.employeeperformance.model.Employee;
import com.estuate.employeeperformance.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private List<Employee> mockEmployees;

    @BeforeEach
    void setUp() {
        // Sample Employees with correct constructor
        mockEmployees = List.of(
                new Employee("1", 101, "John Doe", "A", null),
                new Employee("2", 102, "Jane Doe", "B", null),
                new Employee("3", 103, "Alice", "C", null),
                new Employee("4", 104, "Bob", "D", null),
                new Employee("5", 105, "Charlie", "E", null));
    }

    @Test
    void testGetAllEmployees() {
        when(employeeRepository.findAll()).thenReturn(mockEmployees);

        List<Employee> employees = employeeService.getAllEmployees();

        assertEquals(5, employees.size());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void testSaveEmployee() {
        Employee employee = new Employee("6", 106, "David", "C", null);
        when(employeeRepository.save(employee)).thenReturn(employee);

        Employee savedEmployee = employeeService.saveEmployee(employee);

        assertNotNull(savedEmployee);
        assertEquals("David", savedEmployee.getName());
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void testGetRatingCount() {
        when(employeeRepository.findAll()).thenReturn(mockEmployees);

        Map<String, Integer> ratingCount = employeeService.getRatingCount();

        assertEquals(1, ratingCount.get("A"));
        assertEquals(1, ratingCount.get("B"));
        assertEquals(1, ratingCount.get("C"));
        assertEquals(1, ratingCount.get("D"));
        assertEquals(1, ratingCount.get("E"));
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void testGetRatingPercentage() {
        when(employeeRepository.findAll()).thenReturn(mockEmployees);

        Map<String, Double> ratingPercentage = employeeService.getRatingPercentage();

        assertEquals(20.0, ratingPercentage.get("A"));
        assertEquals(20.0, ratingPercentage.get("B"));
        assertEquals(20.0, ratingPercentage.get("C"));
        assertEquals(20.0, ratingPercentage.get("D"));
        assertEquals(20.0, ratingPercentage.get("E"));
    }

    @Test
    void testGetDeviationFromStandard() {
        when(employeeRepository.findAll()).thenReturn(mockEmployees);

        Map<String, Integer> deviation = employeeService.getDeviationFromStandard();

        assertNotNull(deviation);
        assertTrue(deviation.containsKey("A"));
        assertTrue(deviation.containsKey("B"));
    }

    @Test
    void testGetEmployeesForRevision() {
        when(employeeRepository.findAll()).thenReturn(mockEmployees);

        List<Employee> employeesForRevision = employeeService.getEmployeesForRevision();

        assertNotNull(employeesForRevision);
        verify(employeeRepository, times(1)).findAll();
    }
}
