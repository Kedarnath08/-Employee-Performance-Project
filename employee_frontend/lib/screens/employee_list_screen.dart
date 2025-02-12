// ignore_for_file: library_private_types_in_public_api

import 'package:flutter/material.dart';
import '../models/employee.dart';
import '../services/api_service.dart';

class EmployeeListScreen extends StatefulWidget {
  const EmployeeListScreen({super.key});

  @override
  _EmployeeListScreenState createState() => _EmployeeListScreenState();
}

class _EmployeeListScreenState extends State<EmployeeListScreen> {
  final ApiService apiService = ApiService();
  late Future<List<Employee>> employees;

  @override
  void initState() {
    super.initState();
    employees = apiService.fetchEmployees();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text("Employee List")),
      body: FutureBuilder<List<Employee>>(
        future: employees,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator());
          } else if (snapshot.hasError) {
            return Center(child: Text("Error: ${snapshot.error}"));
          } else if (!snapshot.hasData || snapshot.data!.isEmpty) {
            return const Center(child: Text("No employees found"));
          } else {
            return ListView.separated(
              itemCount: snapshot.data!.length,
              itemBuilder: (context, index) {
                Employee employee = snapshot.data![index];
                return ListTile(
                  title: Text("${employee.name} (ID: ${employee.employeeId})",
                      style: const TextStyle(fontWeight: FontWeight.bold)),
                  subtitle: Text("Rating: ${employee.rating}"),
                  leading: CircleAvatar(
                    child: Text(employee.employeeId.toString()),
                  ),
                );
              },
              separatorBuilder: (context, index) => Divider(
                color: Colors.grey.shade400, // Medium gray line
                thickness: 0.8, // Not too thick
                indent: 15, // Space from left
                endIndent: 15, // Space from right
              ),
            );
          }
        },
      ),
    );
  }
}
