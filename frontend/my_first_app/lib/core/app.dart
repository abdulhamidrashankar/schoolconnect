import 'package:flutter/material.dart';
import 'package:flutter_localizations/flutter_localizations.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:my_first_app/l10n/app_localizations.dart';
import '../areas/auth/view/splash_screen_page.dart';
import '../areas/auth/view/login_page.dart';
import '../areas/auth/view/language_selection_page.dart';

class App extends StatefulWidget {
  const App({super.key});

  @override
  State<App> createState() => _AppState();
}

class _AppState extends State<App> {
  Locale? _locale;
  bool _isLanguageSelected = false;

  @override
  void initState() {
    super.initState();
    _loadLocale();
  }

  // Load saved locale
  Future<void> _loadLocale() async {
    try {
      final prefs = await SharedPreferences.getInstance();
      final String? languageCode = prefs.getString('languageCode');

      if (languageCode != null && languageCode.isNotEmpty) {
        setState(() {
          _locale = Locale(languageCode);
          _isLanguageSelected = true;
        });
      } else {
        setState(() {
          _locale = const Locale('en');
          _isLanguageSelected = false;
        });
      }
    } catch (e) {
      debugPrint("Error loading locale: $e");
    }
  }

  // Set and save locale
  Future<void> _setLocale(Locale locale) async {
    try {
      final prefs = await SharedPreferences.getInstance();
      await prefs.setString('languageCode', locale.languageCode);
      setState(() {
        _locale = locale;
        _isLanguageSelected = true;
      });
    } catch (e) {
      debugPrint("Error saving locale: $e");
    }
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'School Management App',
      debugShowCheckedModeBanner: false,

      // ✅ Safe fallback locale
      locale: _locale ?? const Locale('en'),

      supportedLocales: const [
        Locale('en'),
        Locale('hi'),
        Locale('mr'),
        Locale('ar'),
      ],

      localizationsDelegates: const [
        AppLocalizations.delegate,
        GlobalMaterialLocalizations.delegate,
        GlobalWidgetsLocalizations.delegate,
        GlobalCupertinoLocalizations.delegate,
      ],

      theme: ThemeData(
        primarySwatch: Colors.blue,
        scaffoldBackgroundColor: Colors.white,
        appBarTheme: const AppBarTheme(
          backgroundColor: Colors.blue,
          foregroundColor: Colors.white,
        ),
      ),

      // ✅ Define routes
      initialRoute: '/splash',
      routes: {
        '/splash': (context) => SplashScreenWrapper(
              isLanguageSelected: _isLanguageSelected,
              onLocaleChange: _setLocale,
            ),
        '/login': (context) => const LoginPage(),
        '/language': (context) =>
            LanguageSelectionPage(onLocaleChange: _setLocale),
      },

      // ✅ Handle invalid routes gracefully
      onUnknownRoute: (settings) {
        return MaterialPageRoute(
          builder: (context) => const Scaffold(
            body: Center(
              child: Text(
                'Page not found',
                style: TextStyle(fontSize: 18, color: Colors.red),
              ),
            ),
          ),
        );
      },
    );
  }
}

// ✅ Wrapper to handle splash safely
class SplashScreenWrapper extends StatefulWidget {
  final bool isLanguageSelected;
  final Function(Locale) onLocaleChange;

  const SplashScreenWrapper({
    Key? key,
    required this.isLanguageSelected,
    required this.onLocaleChange,
  }) : super(key: key);

  @override
  State<SplashScreenWrapper> createState() => _SplashScreenWrapperState();
}

class _SplashScreenWrapperState extends State<SplashScreenWrapper> {
  @override
  void initState() {
    super.initState();
    _navigate();
  }

  Future<void> _navigate() async {
    await Future.delayed(const Duration(seconds: 2));
    if (!mounted) return;

    try {
      if (widget.isLanguageSelected) {
        Navigator.pushReplacementNamed(context, '/login');
      } else {
        Navigator.pushReplacementNamed(context, '/language');
      }
    } catch (e) {
      debugPrint("Navigation error in SplashScreenWrapper: $e");
    }
  }

  @override
  Widget build(BuildContext context) {
    try {
      return const SplashScreen();
    } catch (e) {
      debugPrint("Error loading SplashScreen: $e");
      return const Scaffold(
        body: Center(child: CircularProgressIndicator()),
      );
    }
  }
}