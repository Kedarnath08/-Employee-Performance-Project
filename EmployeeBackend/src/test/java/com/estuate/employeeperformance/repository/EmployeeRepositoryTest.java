package com.estuate.employeeperformance.repository;

import com.estuate.employeeperformance.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.Optional;
import java.util.List;

@DataMongoTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    private Employee testEmployee1;
    private Employee testEmployee2;

    @BeforeEach
    void setUp() {
        // Clearing existing records before each test
        mongoTemplate.dropCollection(Employee.class);

        // Creating sample employees based on your JSON data
        testEmployee1 = new Employee(null, 5001, "Harry1", "A", null);
        testEmployee2 = new Employee(null, 5002, "Harry3", "B", null);

        // Saving them in the test database
        employeeRepository.save(testEmployee1);
        employeeRepository.save(testEmployee2);
    }

    @Test
    void testSaveEmployee() {
        Employee newEmployee = new Employee(null, 5016, "Harry16", "C", null);
        Employee savedEmployee = employeeRepository.save(newEmployee);

        assertThat(savedEmployee.getId()).isNotNull();
        assertThat(savedEmployee.getName()).isEqualTo("Harry16");
        assertThat(savedEmployee.getRating()).isEqualTo("C");
    }

    @Test
    void testFindById() {
        Optional<Employee> foundEmployee = employeeRepository.findById(testEmployee1.getId());

        assertThat(foundEmployee).isPresent();
        assertThat(foundEmployee.get().getName()).isEqualTo("Harry1");
        assertThat(foundEmployee.get().getRating()).isEqualTo("A");
    }

    @Test
    void testFindAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSize(2); // Since we added 2 employees in setUp()
    }

    @Test
    void testDeleteEmployee() {
        employeeRepository.delete(testEmployee1);
        Optional<Employee> foundEmployee = employeeRepository.findById(testEmployee1.getId());

        assertThat(foundEmployee).isNotPresent();
    }
}
