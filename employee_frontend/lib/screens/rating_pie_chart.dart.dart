// ignore_for_file: library_private_types_in_public_api

import 'package:flutter/material.dart';
import 'package:fl_chart/fl_chart.dart';
import '../services/api_service.dart';

class RatingPieChart extends StatefulWidget {
  const RatingPieChart({super.key});

  @override
  _RatingPieChartState createState() => _RatingPieChartState();
}

class _RatingPieChartState extends State<RatingPieChart> {
  Map<String, double> ratingData = {};

  @override
  void initState() {
    super.initState();
    fetchData();
  }

  void fetchData() async {
    try {
      var data = await ApiService().fetchRatingPercentage();
      setState(() {
        ratingData = data;
      });
    } catch (e) {
      print("Error loading rating percentages: $e");
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text("Employee Rating Distribution")),
      body: ratingData.isEmpty
          ? const Center(child: CircularProgressIndicator())
          : SingleChildScrollView(
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  const SizedBox(height: 20),
                  const Text(
                    "Actual percentage of employees in each rating category :",
                    style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold),
                    textAlign: TextAlign.center,
                  ),
                  const SizedBox(height: 20),
                  Container(
                    height: 300, // Constrain the pie chart height
                    padding: const EdgeInsets.all(10),
                    child: PieChart(
                      PieChartData(
                        sections: ratingData.entries.map((entry) {
                          return PieChartSectionData(
                            title:
                                "${entry.key}: ${entry.value.toStringAsFixed(1)}%",
                            value: entry.value,
                            color: _getColorForRating(entry.key),
                            radius: 80,
                            titleStyle: const TextStyle(
                              color: Colors.white, // White text color
                              fontWeight: FontWeight.bold, // Bold font
                              fontSize: 14, // Adjust font size if needed
                            ),
                          );
                        }).toList(),
                        sectionsSpace: 2,
                        centerSpaceRadius: 40,
                      ),
                    ),
                  ),
                ],
              ),
            ),
    );
  }

  Color _getColorForRating(String rating) {
    switch (rating) {
      case "A":
        return Colors.green;
      case "B":
        return Colors.blue;
      case "C":
        return Colors.orange;
      case "D":
        return Colors.red;
      case "E":
        return Colors.purple;
      default:
        return Colors.grey;
    }
  }
}
