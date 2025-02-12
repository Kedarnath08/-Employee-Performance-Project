// ignore_for_file: library_private_types_in_public_api

import 'package:flutter/material.dart';
import '../models/employee.dart';
import '../services/api_service.dart';

class RatingSuggestionsScreen extends StatefulWidget {
  const RatingSuggestionsScreen({super.key});

  @override
  _RatingSuggestionsScreenState createState() =>
      _RatingSuggestionsScreenState();
}

class _RatingSuggestionsScreenState extends State<RatingSuggestionsScreen> {
  final ApiService apiService = ApiService();
  late Future<List<Employee>> revisedEmployees;

  @override
  void initState() {
    super.initState();
    revisedEmployees = apiService.fetchRevisedRatings();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text("Suggested Rating Revisions")),
      body: FutureBuilder<List<Employee>>(
        future: revisedEmployees,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator());
          } else if (snapshot.hasError) {
            return Center(child: Text("Error: ${snapshot.error}"));
          } else if (!snapshot.hasData || snapshot.data!.isEmpty) {
            return const Center(child: Text("No revisions found"));
          } else {
            return ListView.separated(
              // ✅ Use ListView.separated instead of builder
              itemCount: snapshot.data!.length,
              itemBuilder: (context, index) {
                Employee employee = snapshot.data![index];
                return ListTile(
                  leading: CircleAvatar(
                    child: Text(
                        employee.employeeId.toString()), // Display employeeId
                  ),
                  title: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text("${employee.name} (ID: ${employee.employeeId})",
                          style: const TextStyle(fontWeight: FontWeight.bold)),
                      Text(
                        "Actual Rating: ${employee.rating} → Suggested Rating: ${employee.suggestedNewRating}",
                        style: const TextStyle(color: Colors.green),
                      ),
                    ],
                  ),
                  subtitle: Text(
                    "Currently has a rating of ${employee.rating}. It is suggested to revise their rating to ${employee.suggestedNewRating}.",
                    style: const TextStyle(color: Colors.blue),
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
