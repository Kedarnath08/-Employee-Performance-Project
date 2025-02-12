import 'dart:convert';
import 'package:flutter/foundation.dart';
import 'package:http/http.dart' as http;
import '../models/employee.dart';

class ApiService {
  static const String baseUrl = kIsWeb
      ? "http://localhost:8080/employees" // For Web (Chrome)
      : "http://10.0.2.2:8080/employees"; // For Android Emulator

  // Fetch all employees
  Future<List<Employee>> fetchEmployees() async {
    final response = await http.get(Uri.parse(baseUrl));
    if (response.statusCode == 200) {
      List<dynamic> data = json.decode(response.body);
      return data.map((e) => Employee.fromJson(e)).toList();
    } else {
      throw Exception("Failed to load employees");
    }
  }

  // Fetch employees who need rating revision
  Future<List<Employee>> fetchRevisedRatings() async {
    final response = await http.get(Uri.parse('$baseUrl/revise-ratings'));
    if (response.statusCode == 200) {
      List<dynamic> data = json.decode(response.body);
      return data.map((e) => Employee.fromJson(e)).toList();
    } else {
      throw Exception("Failed to load revised ratings");
    }
  }

  // Fetch rating percentage distribution for Pie Chart
  Future<Map<String, double>> fetchRatingPercentage() async {
    final response = await http.get(Uri.parse('$baseUrl/rating-percentage'));
    if (response.statusCode == 200) {
      Map<String, dynamic> data = json.decode(response.body);
      return data.map((key, value) => MapEntry(key, (value as num).toDouble()));
    } else {
      throw Exception("Failed to load rating percentages");
    }
  }
}
