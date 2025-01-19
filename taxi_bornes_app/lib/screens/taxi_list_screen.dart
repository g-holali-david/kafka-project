import 'package:flutter/material.dart';
import 'package:taxi_bornes_app/model/station.dart';
import 'package:taxi_bornes_app/service/api_service.dart';

import 'station_detail_screen.dart';

class TaxiListScreen extends StatefulWidget {
  const TaxiListScreen({Key? key}) : super(key: key);

  @override
  State<TaxiListScreen> createState() => _TaxiListScreenState();
}

class _TaxiListScreenState extends State<TaxiListScreen> {
  List<Station> stations = [];
  int currentPage = 0;
  bool isLoading = false;
  bool hasMore = true;

  @override
  void initState() {
    super.initState();
    _fetchStations();
  }

  Future<void> _fetchStations() async {
    if (isLoading || !hasMore) return;

    setState(() {
      isLoading = true;
    });

    try {
      final newStations = await ApiService.fetchStations(currentPage, 5);
      setState(() {
        stations.addAll(newStations);
        currentPage++;
        if (newStations.length < 5) {
          hasMore = false; // Plus de données disponibles
        }
      });
    } catch (e) {
      ScaffoldMessenger.of(context)
          .showSnackBar(SnackBar(content: Text('Error: $e')));
    } finally {
      setState(() {
        isLoading = false;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Taxi Stations')),
      body: ListView.builder(
        itemCount: stations.length + (hasMore ? 1 : 0),
        itemBuilder: (context, index) {
          if (index == stations.length) {
            return isLoading
                ? const Center(child: CircularProgressIndicator())
                : ElevatedButton(
                    onPressed: _fetchStations,
                    child: const Text("Load More"),
                  );
          }
          final station = stations[index];
          return ListTile(
            title: Text(station.name),
            subtitle: Text(station.address),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(
                  builder: (context) => StationDetailScreen(station: station),
                ),
              );
            },
          );
        },
      ),
    );
  }
}
