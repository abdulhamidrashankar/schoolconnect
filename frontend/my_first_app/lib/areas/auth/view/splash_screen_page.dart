import 'package:flutter/material.dart';

class SplashScreen extends StatelessWidget {
  const SplashScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: LayoutBuilder(
        builder: (context, constraints) {
          final width = constraints.maxWidth;
          final height = constraints.maxHeight;

          final bool isLargeScreen = width > 800;

          // Fonts and button sizes responsive
          final titleFont = isLargeScreen ? width * 0.03 : width * 0.08;
          final taglineFont = isLargeScreen ? width * 0.015 : width * 0.035;
          final buttonWidth = isLargeScreen ? width * 0.2 : width * 0.5;
          final buttonHeight = isLargeScreen ? height * 0.07 : height * 0.065;

          return Stack(
            children: [
              // 🔹 Gradient background
              Container(
                decoration: BoxDecoration(
                  gradient: LinearGradient(
                    colors: [Colors.blue.shade900, Colors.blue.shade400],
                    begin: Alignment.topLeft,
                    end: Alignment.bottomRight,
                  ),
                ),
              ),

              // 🔹 Floating circles for modern UI trend
              Positioned(
                top: -height * 0.15,
                left: -width * 0.2,
                child: Container(
                  width: width * 0.5,
                  height: width * 0.5,
                  decoration: BoxDecoration(
                    color: Colors.white.withOpacity(0.1),
                    shape: BoxShape.circle,
                  ),
                ),
              ),
              Positioned(
                bottom: -height * 0.1,
                right: -width * 0.15,
                child: Container(
                  width: width * 0.4,
                  height: width * 0.4,
                  decoration: BoxDecoration(
                    color: Colors.white.withOpacity(0.08),
                    shape: BoxShape.circle,
                  ),
                ),
              ),

              // 🔹 Main content
              Center(
                child: Column(
                  mainAxisSize: MainAxisSize.min,
                  children: [
                    // 🖼️ Image floating naturally
                    Image.asset(
                      'assets/image/ClassBirdgeL.png',
                      width: isLargeScreen ? width * 0.35 : width * 0.6,
                      fit: BoxFit.contain,
                    ),

                    SizedBox(height: height * 0.03),

                    // Title
                    Text(
                      'ClassBridge',
                      textAlign: TextAlign.center,
                      style: TextStyle(
                        fontSize: titleFont,
                        fontWeight: FontWeight.bold,
                        color: Colors.white,
                        shadows: [
                          Shadow(
                            color: Colors.black26,
                            offset: Offset(2, 2),
                            blurRadius: 4,
                          ),
                        ],
                      ),
                    ),

                    SizedBox(height: height * 0.015),

                    // Tagline
                    Text(
                      'Where Technology & Education Unite.',
                      textAlign: TextAlign.center,
                      style: TextStyle(
                        fontSize: taglineFont,
                        color: Colors.white70,
                        fontWeight: FontWeight.w500,
                        height: 1.3,
                        shadows: [
                          Shadow(
                            color: Colors.black26,
                            offset: Offset(1, 1),
                            blurRadius: 3,
                          ),
                        ],
                      ),
                    ),

                    SizedBox(height: height * 0.05),

                    // Start Button
                    SizedBox(
                      width: buttonWidth,
                      height: buttonHeight,
                      child: ElevatedButton(
                        style: ElevatedButton.styleFrom(
                          backgroundColor: Colors.white,
                          shape: RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(30),
                          ),
                          elevation: 6,
                        ),
                        onPressed: () {
                          Navigator.pushReplacementNamed(context, '/login');
                        },
                        child: Text(
                          'START',
                          style: TextStyle(
                            fontSize: width * 0.045,
                            fontWeight: FontWeight.bold,
                            color: Colors.blue.shade900,
                            letterSpacing: 1.2,
                          ),
                        ),
                      ),
                    ),
                  ],
                ),
              ),
            ],
          );
        },
      ),
    );
  }
}
