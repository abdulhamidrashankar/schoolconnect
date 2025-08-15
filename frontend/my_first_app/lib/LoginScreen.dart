import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';


void main() {
  runApp(const SchoolApp());
}

class SchoolApp extends StatelessWidget {
  const SchoolApp({super.key});

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      debugShowCheckedModeBanner: false,
      home: LoginScreen(),
    );
  }
}

class LoginScreen extends StatefulWidget {
  const LoginScreen({super.key});

  @override
  State<LoginScreen> createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  bool _obscureText = true; // 👁️‍🗨️ Password chhupana by default
  final TextEditingController __emailTxt = TextEditingController();
  final TextEditingController _passwordTxt = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      body: SafeArea(
        child: SingleChildScrollView(
          padding: const EdgeInsets.symmetric(horizontal: 30, vertical: 30),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              // 👧 Illustration image
              Center(
                child: SizedBox(
                  child: Image.asset(
                    'assets/images/school.png', // You can change this image
                    fit: BoxFit.contain,
                  ),
                ),
              ),
              const SizedBox(height: 5),

              const Text(
                'Welcome to',
                style: TextStyle(fontSize: 22, color: Colors.black54),
              ),
              const Text(
                'ClassBridge',
                style: TextStyle(
                  fontSize: 32,
                  fontWeight: FontWeight.bold,
                  color: Colors.black87,
                ),
              ),
              const SizedBox(height: 22),

              // 📧 Email Field
              TextField(
                controller: __emailTxt,
                decoration: InputDecoration(
                  
                  prefixIcon: const Icon(Icons.email_outlined),
                  hintText: 'Email Address',
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(20),
                  ),
                ),
              ),
              const SizedBox(height: 16),

              // 🔒 Password Field with Toggle
              TextField(
                controller: _passwordTxt,
                obscureText: _obscureText,
                decoration: InputDecoration
                (
                  prefixIcon: const Icon(Icons.lock_outline),
                  suffixIcon: 
                    IconButton(
                      icon: Icon(
                                  _obscureText ? Icons.visibility_outlined: Icons.visibility_off_outlined,
                                ),
                    onPressed: () 
                    {
                      setState(()
                      {
                        _obscureText = !_obscureText;
                      });
                    },
                  ),
                  hintText: 'Password',
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(20),
                  ),
                ),
              ),
              const SizedBox(height: 10),

              // Forgot Password
              Align(
                alignment: Alignment.centerRight,
                child: TextButton(
                  onPressed: () {},
                  child: const Text('Forgot password?'),
                ),
              ),

              const SizedBox(height: 10),

              // 🔵 Log in Button
              SizedBox(
                width: double.infinity,
                height: 48,
                child: ElevatedButton(
                  onPressed: () {
                    String email = __emailTxt.text.trim();
                    String password = _passwordTxt.text.trim();
                     if (email.isNotEmpty && password.isNotEmpty) 
                     {
                        //Toast
                        Fluttertoast.showToast(
                          msg: "Login Successful!",
                          toastLength: Toast.LENGTH_SHORT,
                          gravity: ToastGravity.CENTER, 
                          backgroundColor: Colors.green,
                          textColor: Colors.white,
                          fontSize: 16.0,
                        );
                     }
                        // Optionally navigate to next screen here
                      else 
                      {
                        Fluttertoast.showToast(
                          msg: "Please enter required feilds!",
                          toastLength: Toast.LENGTH_SHORT,
                          gravity: ToastGravity.CENTER,
                          backgroundColor: Colors.red,
                          textColor: Colors.white,  
                        );
                      }
                  },
                  style: ElevatedButton.styleFrom
                  (
                    shape: RoundedRectangleBorder
                    (
                      borderRadius: BorderRadius.circular(12),
                    ),
                    backgroundColor: Colors.blue,
                    foregroundColor: Colors.white,
                  ),
                  child: const Text
                  (
                    'Log in',
                     style: TextStyle(fontSize: 16),
                  ),
                ),
              ),
              const SizedBox(height: 24),

              // 👤 Sign up
              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  const Text("Don't have an account? "),
                  GestureDetector(
                    onTap: () {},
                    child: const Text(
                      'Sign up',
                      style: TextStyle(
                        color: Colors.blue,
                        fontWeight: FontWeight.w500,
                      ),
                    ),
                  ),
                ],
              )
            ],
          ),
        ),
      ),

      // Footer
      bottomNavigationBar: SafeArea(
        child: Container(
          color: Colors.blue,
          padding: const EdgeInsets.all(10),
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
