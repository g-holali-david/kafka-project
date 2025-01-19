import 'package:flutter/material.dart';

class AppConfig {
  static const Color primaryColor = Colors.blue;
  static const Color secondaryColor = Colors.orange;
  static const Color drawerBackgroundColor = Colors.white;
  static const Color appBarColor = Colors.blue;
  static const Color bottomNavBackgroundColor = Colors.blue;
  static const Color bottomNavIconColor = Colors.white;
  static const Color bottomNavSelectedColor = Colors.orange;

  static ThemeData getTheme() {
    return ThemeData(
      primarySwatch: Colors.blue,
      scaffoldBackgroundColor: Colors.grey[100],
      appBarTheme: const AppBarTheme(
        backgroundColor: appBarColor,
        foregroundColor: Colors.white,
      ),
      bottomNavigationBarTheme: const BottomNavigationBarThemeData(
        backgroundColor: bottomNavBackgroundColor,
        selectedItemColor: bottomNavSelectedColor,
        unselectedItemColor: bottomNavIconColor,
      ),
    );
  }
}
