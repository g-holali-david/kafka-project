import 'package:flutter/material.dart';

import 'configs/app_config.dart';
import 'screens/home_screen.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Taxi Stations App',
      theme: AppConfig.getTheme(),
      home: HomeScreen(),
    );
  }
}
