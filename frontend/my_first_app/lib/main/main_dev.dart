import 'package:flutter/material.dart';
import 'package:my_first_app/config/config_service.dart';
import '../my_app.dart';
import 'package:flutter/services.dart' show rootBundle;
import 'dart:convert';

const String configFile = 'assets/environment/dev_config.json';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();

  // Load the configuration file
  String jsonString = await rootBundle.loadString(configFile);
  ConfigService().setConfig(jsonDecode(jsonString));

  // Run the app with the loaded configuration
  runApp(MyApp());
}