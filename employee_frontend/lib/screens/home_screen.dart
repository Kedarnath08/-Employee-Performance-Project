import 'package:flutter/material.dart';

class HomeScreen extends StatelessWidget {
  const HomeScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text(
          'Employee Performance',
          style:
              TextStyle(fontWeight: FontWeight.bold), // Optional: Make it bold
        ),
        centerTitle: true, // Centers the text
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            _buildButton(context, 'View Employees', '/employee_list'),
            const SizedBox(height: 20),
            _buildButton(
                context, 'View Rating Suggestions', '/rating_suggestions'),
            const SizedBox(height: 20),
            _buildButton(context, 'View Pie Chart', '/pie_chart'),
          ],
        ),
      ),
    );
  }

  Widget _buildButton(BuildContext context, String text, String route) {
    return SizedBox(
      width: 350, // Set button width
      height: 150, // Set button height
      child: ElevatedButton(
        onPressed: () {
          Navigator.pushNamed(context, route);
        },
        style: ElevatedButton.styleFrom(
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(20), // Rounded edges
          ),
          padding:
              const EdgeInsets.symmetric(vertical: 15), // Padding inside button
          textStyle: const TextStyle(
              fontSize: 18, fontWeight: FontWeight.bold), // Text styling
        ),
        child: Text(text),
      ),
    );
  }
}
