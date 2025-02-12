📌 DESCRIPTION:
The Employee Performance Project is a full-stack web application designed to evaluate and balance employee ratings based on predefined distribution criteria. The system takes in employee performance ratings, calculates deviations from the expected distribution, and suggests employees for rating revision.
The backend is built using Spring Boot and MongoDB Atlas, while the frontend is developed using Flutter.


🛠️ TECHNOLOGIES USED:
Java: 17.0.12 (Stable version)
Spring Boot: 3.4.1
Apache Maven: 3.9.9
MongoDB Atlas (Cloud Database): 8.0.4
Flutter: 3.13.2 (Frontend)
Dart: 3.1.0 (Frontend)
Git: 2.43.0.windows.1
Postman - Used for API testing


🚀 PROJECT SETUP INSTRUCTIONS :
1️⃣ Clone the Repository
Open a terminal or command prompt and run:  git clone https://github.com/Kedarnath08/-Employee-Performance-Project.git
Navigate into the project directory:  cd EmployeePerformance

2️⃣ Backend Setup (Spring Boot)
Step 1: Install Dependencies, Ensure Java 17 and Maven are installed.
Run:  mvn clean install

Step 2: Configure MongoDB Atlas
Update application.properties (if needed) with your MongoDB connection URI.

Step 3: Run the Application
Start the backend server using: mvn spring-boot:run
The backend API will be accessible at ➡️ http://localhost:8080


3️⃣ Frontend Setup (Flutter)
Step 1: Install Flutter Dependencies, Ensure Flutter is installed.
Run:  flutter pub get

Step 2: Run the Application on an Emulator
Open VS Code.
Ensure an Android Emulator running.
Run the Flutter app using:  flutter run
The Emulator will be running on ➡️ http://10.0.2.2:8080/employees



👀 HOW TO CHECK THE PROJECT?
1️⃣ Run the Frontend on an Emulator (Recommended)
Follow the Frontend Setup (Flutter) steps above.

Once the app is running, you will see three buttons on the UI:
1. View Employees - Displays a list of all employees with their details.
2. View Rating Suggestions - Suggests which employees’ ratings should be revised to maintain the expected distribution.
3. Pie Chart Visualization - Displays a pie chart representing the percentage of employees in each rating category.
Each button fetches real-time data from the backend and presents it in an easy-to-understand format.


2️⃣ Check Only the Backend Using Postman
Step 1: Start the Backend
Ensure the backend is running (mvn spring-boot:run).

Step 2: Test API Endpoints in Postman
Use GET requests to test the following endpoints:
• Fetch all employees - http://localhost:8080/employees	
• Get count of employees per rating category - http://localhost:8080/employees/rating-count	
• Get deviation from expected rating distribution - http://localhost:8080/employees/deviation	
• Get employees who should be considered for rating revision - http://localhost:8080/employees/revise-ratings	
• Get the percentage of employees per rating category - http://localhost:8080/employees/rating-percentage	



🧪 RUNNING UNIT TESTS:
Run all tests using: mvn test

This will execute:
1. Controller Layer Tests 
2. Service Layer Tests
3. Repository Layer Tests 



📂PROJECT STRUCTURE: ![EmployeePerformance-ProjectStructure](https://github.com/user-attachments/assets/420afe3f-2f97-4ca0-9dc9-202e55f0e192)



