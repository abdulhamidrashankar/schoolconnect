import 'package:flutter/material.dart';
import 'core/utilities/toats_utils.dart';
import 'package:my_first_app/core/app.dart'; 


Future<void> main() async 
{
  WidgetsFlutterBinding.ensureInitialized();
  await NotificationUtils.init();
  runApp(const App()); 
}
