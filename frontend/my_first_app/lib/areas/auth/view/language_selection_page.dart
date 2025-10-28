import 'package:flutter/material.dart';
import 'package:my_first_app/l10n/app_localizations.dart';

class LanguageSelectionPage extends StatefulWidget {
  final Function(Locale) onLocaleChange;

  const LanguageSelectionPage({super.key, required this.onLocaleChange});

  @override
  State<LanguageSelectionPage> createState() => _LanguageSelectionPageState();
}

class _LanguageSelectionPageState extends State<LanguageSelectionPage> {
  String? _selectedLang;

  @override
  Widget build(BuildContext context) {
    final t = AppLocalizations.of(context)!;

    final languages = {
      "en": t.english,
      "hi": t.hindi,
      "mr": t.marathi,
      "ar": t.arabic,
    };

    return Scaffold(
      appBar: AppBar(title: Text(t.select_language)),
      body: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          ...languages.entries.map(
            (entry) => RadioListTile<String>(
              title: Text(entry.value),
              value: entry.key,
              groupValue: _selectedLang,
              onChanged: (value) => setState(() => _selectedLang = value),
            ),
          ),
          const SizedBox(height: 20),
          ElevatedButton(
            onPressed: _selectedLang == null
                ? null
                : () {
                    widget.onLocaleChange(Locale(_selectedLang!));
                    Navigator.pushReplacementNamed(context, '/login');
                  },
            child: Text(t.continue_button),
          ),
        ],
      ),
    );
  }
}
