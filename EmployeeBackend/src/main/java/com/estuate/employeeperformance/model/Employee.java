package com.estuate.employeeperformance.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//Lombok is a Java library that helps to reduce boilerplate code by using simple annotations.

@Data // automatically generates getters and setters
@AllArgsConstructor // Generates a constructor with all the fields as parameters.
@NoArgsConstructor // Generates a no-argument constructor (default constructor).
@Document(collection = "employees") // Maps this class to the employees collection in MongoDB.
public class Employee {
    @Id // primary identifier
    private String id; // mongodb assigned ID
    private int employeeId; // Standard Employee ID
    private String name;
    private String rating;
    private String suggestedNewRating;
}
