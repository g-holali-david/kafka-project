import 'package:flutter/material.dart';
import 'package:taxi_bornes_app/models/station.dart';

class StationDetailScreen extends StatelessWidget {
  final Station station;

  const StationDetailScreen({Key? key, required this.station})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text(station.name)),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              'Name: ${station.name}',
              style: const TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 8),
            Text('Address: ${station.address}',
                style: const TextStyle(fontSize: 16)),
            const SizedBox(height: 8),
            Text('INSEE: ${station.insee}',
                style: const TextStyle(fontSize: 16)),
            const SizedBox(height: 8),
            Text('Emplacements: ${station.emplacements}',
                style: const TextStyle(fontSize: 16)),
            const SizedBox(height: 8),
            Text('Status: ${station.status}',
                style: const TextStyle(fontSize: 16)),
            const SizedBox(height: 16),
            const Text('Coordinates:',
                style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold)),
            Text('Latitude: ${station.latitude}'),
            Text('Longitude: ${station.longitude}'),
          ],
        ),
      ),
    );
  }
}
