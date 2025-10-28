import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../../../core/utilities/toats_utils.dart';
import 'package:my_first_app/areas/auth/viewmodel/login_viewmodel.dart';
import 'package:my_first_app/l10n/app_localizations.dart';

class LoginPage extends StatelessWidget {
  const LoginPage({super.key});

  @override
  Widget build(BuildContext context) {
    final loc = AppLocalizations.of(context)!;

    return ChangeNotifierProvider(
      create: (_) => LoginViewModel(),
      child: Consumer<LoginViewModel>(
        builder: (context, vm, _) {
          return Scaffold(
            backgroundColor: Colors.white,
            body: LayoutBuilder(
              builder: (context, constraints) {
                double formWidth = constraints.maxWidth < 600 ? double.infinity : 400;
                return SafeArea(
                  child: SingleChildScrollView(
                    padding: const EdgeInsets.symmetric(horizontal: 20, vertical: 30),
                    child: Center(
                      child: SizedBox(
                        width: formWidth,
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.center,
                          children: [
                            Image.asset('assets/images/school.png', height: 180, fit: BoxFit.contain),
                            const SizedBox(height: 10),
                            Text(loc.welcome_to,
                                style: const TextStyle(fontSize: 22, color: Colors.black54)),
                            Text(loc.school_name,
                                style: const TextStyle(
                                    fontSize: 32, fontWeight: FontWeight.bold, color: Colors.black87)),
                            const SizedBox(height: 22),

                            // Email field
                            TextField(
                              controller: vm.emailController,
                              decoration: InputDecoration(
                                prefixIcon: const Icon(Icons.email_outlined),
                                hintText: loc.email_placeholder,
                                border: OutlineInputBorder(
                                  borderRadius: BorderRadius.circular(20),
                                ),
                              ),
                            ),
                            const SizedBox(height: 16),

                            // Password field
                            TextField(
                              controller: vm.passwordController,
                              obscureText: true,
                              decoration: InputDecoration(
                                prefixIcon: const Icon(Icons.lock_outline),
                                hintText: loc.password_placeholder,
                                border: OutlineInputBorder(
                                  borderRadius: BorderRadius.circular(20),
                                ),
                              ),
                            ),
                            const SizedBox(height: 10),

                            Align(
                              alignment: Alignment.centerRight,
                              child: TextButton(
                                onPressed: () {},
                                child: Text(loc.forgot_password),
                              ),
                            ),

                            const SizedBox(height: 10),

                            vm.isLoading
                                ? const CircularProgressIndicator()
                                : SizedBox(
                                    width: double.infinity,
                                    height: 48,
                                    child: ElevatedButton(
                                      onPressed: () async {
                                        bool success = await vm.login();
                                        if (success && vm.loggedInUser != null) {
                                          ToastUtils.show(
                                              'Welcome ${vm.loggedInUser!.name}',
                                              bgColor: Colors.green);
                                          Navigator.pushReplacement(
                                            context,
                                            MaterialPageRoute(
                                                builder: (_) => const LoginPage()),
                                          );
                                        }
                                      },
                                      style: ElevatedButton.styleFrom(
                                        shape: RoundedRectangleBorder(
                                          borderRadius: BorderRadius.circular(20),
                                        ),
                                      ),
                                      child: Text(loc.login_button,
                                          style: const TextStyle(fontSize: 16, color: Color.fromARGB(255, 10, 10, 10))),
                                    ),
                                  ),

                            const SizedBox(height: 20),

                            Row(
                              mainAxisAlignment: MainAxisAlignment.center,
                              children: [
                                Text(loc.no_account_text),
                                GestureDetector(
                                  onTap: () {},
                                  child: Text(
                                    loc.sign_up,
                                    style: const TextStyle(
                                      color: Colors.blue,
                                      fontWeight: FontWeight.w500,
                                    ),
                                  ),
                                ),
                              ],
                            ),
                          ],
                        ),
                      ),
                    ),
                  ),
                );
              },
            ),

            // Footer
            bottomNavigationBar: SafeArea(
              child: Container(
                color: Colors.blue,
                padding: const EdgeInsets.all(10),
                child: Text(
                  loc.copyright,
                  textAlign: TextAlign.center,
                  style: const TextStyle(color: Colors.white, fontSize: 10),
                ),
              ),
            ),
          );
        },
      ),
    );
  }
}
