import 'package:employee_frontend/screens/rating_pie_chart.dart.dart';
import 'package:flutter/material.dart';
import 'package:employee_frontend/screens/home_screen.dart';
import 'package:employee_frontend/screens/employee_list_screen.dart';
import 'package:employee_frontend/screens/rating_suggestions_screen.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Employee Performance',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
        useMaterial3: true,
      ),
      initialRoute: '/', // Default starting screen
      routes: {
        '/': (context) => const HomeScreen(),
        '/employee_list': (context) => const EmployeeListScreen(),
        '/rating_suggestions': (context) => const RatingSuggestionsScreen(),
        '/pie_chart': (context) => const RatingPieChart(),
      },
    );
  }
}
