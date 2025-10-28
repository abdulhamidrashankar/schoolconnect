import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

import 'package:my_first_app/core/utilities/url_utils.dart';
import 'package:my_first_app/areas/auth/model/login_model.dart';
import 'package:my_first_app/areas/auth/model/user_model.dart';


class LoginViewModel extends ChangeNotifier {
  final TextEditingController emailController = TextEditingController();
  final TextEditingController passwordController = TextEditingController();

  bool _isLoading = false;
  bool get isLoading => _isLoading;

  String? _errorMessage;
  String? get errorMessage => _errorMessage;

  User? _loggedInUser;
  User? get loggedInUser => _loggedInUser;

  void setLoading(bool value) {
    _isLoading = value;
    notifyListeners();
  }

  Future<bool> login() async {
    final email = emailController.text.trim();
    final password = passwordController.text.trim();

    if (email.isEmpty || password.isEmpty) {
      _errorMessage = "Email and Password cannot be empty";
      notifyListeners();
      return false;
    }

    setLoading(true);

    try {
      final request = LoginRequest(email: email, password: password);
      final loginUrl = UrlUtils.buildLoginUrl();

      final response = await http.post(
        Uri.parse(loginUrl),
        headers: {'Content-Type': 'application/json'},
        body: jsonEncode(request.toJson()),
      ); 

      setLoading(false);

      if (response.statusCode == 200) {
        final data = jsonDecode(response.body);
        _loggedInUser = User.fromJson(data);
        return true;
      } else {
        final data = jsonDecode(response.body);
        _errorMessage = data['message'] ?? "Login failed";
        notifyListeners();
        return false;
      }
    } catch (e) {
      _errorMessage = e.toString();
      setLoading(false);
      notifyListeners();
      return false;
    }
  }

  @override
  void dispose() {
    emailController.dispose();
    passwordController.dispose();
    super.dispose();
  }
}
