class Employee {
  final String id;
  final int employeeId; // Add this field
  final String name;
  final String rating;
  final String suggestedNewRating;

  Employee({
    required this.id,
    required this.employeeId, // Add this field
    required this.name,
    required this.rating,
    required this.suggestedNewRating,
  });

  // Factory constructor to create Employee from JSON
  factory Employee.fromJson(Map<String, dynamic> json) {
    return Employee(
      id: json['_id'] ?? '',
      employeeId: json['employeeId'] ?? 0, // Parse employeeId
      name: json['name'] ?? '',
      rating: json['rating'] ?? '',
      suggestedNewRating: json['suggestedNewRating'] ?? '',
    );
  }
}
