import 'package:flutter/material.dart';
import 'login/login_page.dart';

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'ClassBridge Demo',
      theme: ThemeData(primarySwatch: Colors.blue),
      home: BasicAppbarExample(),
    );
  }
}

class BasicAppbarExample extends StatefulWidget {
  const BasicAppbarExample({super.key});

  @override
  State<BasicAppbarExample> createState() => _BasicAppbarExampleState();
}

class _BasicAppbarExampleState extends State<BasicAppbarExample> {
  String _var = "Welcome to ClassBridge"; // Initial text

  void _updateText(String newText) {
    setState(() {
      _var = newText;
    });
  }

  @override
  Widget build(BuildContext context) {
    const appBarColor = Color.fromARGB(255, 10, 56, 135);

    return Scaffold(
      appBar: AppBar(
        backgroundColor: appBarColor,
        title: Row(
          children: const [
            Icon(Icons.school_rounded, color: Colors.white),
            SizedBox(width: 9),
            Text(
              'ClassBridge',
              style: TextStyle(
                fontSize: 20,
                fontWeight: FontWeight.bold,
                color: Colors.white,
              ),
            ),
          ],
        ),
        actions: <Widget>[
          IconButton(
            icon: const Icon(Icons.notifications, color: Colors.white),
            onPressed: () {
              _updateText("Notifications");
            },
          ),
          PopupMenuButton<String>(
            icon: const Icon(Icons.more_vert, color: Colors.white),
            onSelected: (value) {
              _updateText(value);
            },
            itemBuilder: (BuildContext context) {
              return [
                PopupMenuItem(
                  value: 'Attendance',
                  child: Row(
                    children: const [
                      Icon(Icons.assignment, color: Colors.blue),
                      SizedBox(width: 8),
                      Text('Attendance'),
                    ],
                  ),
                ),
                PopupMenuItem(
                  value: 'Assignments',
                  child: Row(
                    children: const [
                      Icon(Icons.book, color: Colors.green),
                      SizedBox(width: 8),
                      Text('Assignments'),
                    ],
                  ),
                ),
                PopupMenuItem(
                  value: 'Logging out from ClassBridge',
                  child: Row(
                    children: const [
                      Icon(Icons.logout, color: Colors.red),
                      SizedBox(width: 8),
                      Text('Logout'),
                    ],
                  ),
                ),
              ];
            },
          ),
        ],
      ),

      // BODY WITH CARD
      body: SingleChildScrollView(
        child: Padding(
          padding: const EdgeInsets.all(16.0),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              Text(
                _var,
                style: const TextStyle(fontSize: 18, fontWeight: FontWeight.normal),
              ),
              const SizedBox(height: 15),
              Center(
                child: Card(
                  color: const Color.fromARGB(255, 58, 134, 169),
                  elevation: 5,
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(15),
                  ),
                  child: Padding(
                    padding: const EdgeInsets.all(16.0),
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: const [
                        Text(
                          'Today\'s Classes',
                          style: TextStyle(
                            fontSize: 18, 
                            fontWeight: FontWeight.bold,
                            color: Colors.white,
                          ),
                        ),
                        SizedBox(height: 10),
                        Text('1. Mathematics - 10:00 AM', style: TextStyle(color: Colors.white)),
                        Text('2. Science - 11:30 AM', style: TextStyle(color: Colors.white)),
                        Text('3. History - 2:00 PM', style: TextStyle(color: Colors.white)),
                        Text('Lunch Break - 3:00 PM', style: TextStyle(color: Color.fromARGB(255, 14, 13, 13))),
                      ],
                    ),
                  ),
                ),
              ),
              const SizedBox(height: 20),
              ElevatedButton(
                onPressed: () {
                  Navigator.push(
                    context,
                    MaterialPageRoute(builder: (context) => const LoginPage()),
                  );
                },
                child: const Text('Login Page'),
              ),
            ],
          ),
        ),
      ),

      bottomNavigationBar: SafeArea(
        child: Container(
          color: appBarColor,
          padding: const EdgeInsets.all(12),
          child: const Text(
            "Copyright © 2025  ClassBridge  -  Powered By Team TechHive",
            textAlign: TextAlign.center,
            style: TextStyle(color: Colors.white, fontSize: 10),
          ),
        ),
      ),
    );
  }
}