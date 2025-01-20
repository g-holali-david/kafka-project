import 'package:flutter/material.dart';

import '../models/station.dart';
import '../services/api_service.dart';
import 'map_screen.dart';
import 'taxi_list_screen.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({Key? key}) : super(key: key);

  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  int _currentIndex = 0;
  List<Station> stations = [];

  @override
  void initState() {
    super.initState();
    _fetchStations();
  }

  Future<void> _fetchStations() async {
    try {
      final fetchedStations = await ApiService.fetchStations(
        page: 0,
        size: 50,
      );
      setState(() {
        stations = fetchedStations;
      });
    } catch (e) {
      ScaffoldMessenger.of(context)
          .showSnackBar(SnackBar(content: Text('Erreur : $e')));
    }
  }

  @override
  Widget build(BuildContext context) {
    final List<Widget> _screens = [
      const TaxiListScreen(),
      MapScreen(stations: stations),
    ];

    return Scaffold(
      appBar: AppBar(
          title: const Column(
        children: [
          Text('Taxi Stations'),
        ],
      )),
      body: _screens[_currentIndex],
      bottomNavigationBar: BottomNavigationBar(
        currentIndex: _currentIndex,
        onTap: (index) {
          setState(() {
            _currentIndex = index;
          });
        },
        items: const [
          BottomNavigationBarItem(
            icon: Icon(Icons.list),
            label: 'List',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.map),
            label: 'Map',
          ),
        ],
      ),
    );
  }
}
