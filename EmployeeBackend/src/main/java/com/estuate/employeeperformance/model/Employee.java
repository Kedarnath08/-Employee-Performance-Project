package com.estuate.employeeperformance.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "employees")
public class Employee {
    @Id
    private String id; // mongodb assigned ID
    private int employeeId; // Standard Employee ID
    private String name;
    private String rating;
    private String suggestedNewRating;
}
