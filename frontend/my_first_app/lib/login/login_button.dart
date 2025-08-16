import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:my_first_app/dashboard/user_dashboard.dart';
import 'package:my_first_app/utilities/toats_utils.dart';
import 'dart:convert';
import 'package:my_first_app/utilities/url_utils.dart' show UrlUtils;
import 'login_strings.dart';

class LoginButton extends StatelessWidget {
  final TextEditingController emailController;
  final TextEditingController passwordController;
  final VoidCallback? onSuccess;

  const LoginButton({
    super.key,
    required this.emailController,
    required this.passwordController,
    this.onSuccess,
  });

  Future<void> _login(BuildContext context) async {
    final email = emailController.text.trim();
    final password = passwordController.text.trim();

    if (email.isEmpty || password.isEmpty) {
      ToastUtils.show(AppStrings.loginError, bgColor: Colors.red);
      return;
    }
    // Build the login URL using the utility
    final loginUrl = UrlUtils.buildLoginUrl();

    try {
      final response = await http.post(
        Uri.parse(loginUrl),
        headers: {'Content-Type': 'application/json'},
        body: jsonEncode({'username': email, 'password': password}),
      );

      if (response.statusCode == 200) {
        ToastUtils.show(AppStrings.loginSuccess, bgColor: Colors.green);
          // Navigate to HomePage
        Navigator.pushReplacement(
          context,
          MaterialPageRoute(builder: (context) => const UserDashboard()),
        );

        // Optional callback
        if (onSuccess != null) onSuccess!();

      } else {
        final responseBody = jsonDecode(response.body);
        final errorMessage = responseBody['message'] ?? 'Unknown error';

        ToastUtils.show('Login failed: $errorMessage', bgColor: Colors.red);
      }
    } catch (e) {
      ToastUtils.show('Error: $e', bgColor: Colors.red);
    }
  }

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      width: double.infinity,
      height: 48,
      child: ElevatedButton(
        onPressed: () => _login(context),
        style: ElevatedButton.styleFrom(
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(12),
          ),
          backgroundColor: Colors.blue,
          foregroundColor: Colors.white,
        ),
        child: const Text('Log in', style: TextStyle(fontSize: 16)),
      ),
    );
  }
}