import 'package:flutter/material.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:url_launcher/url_launcher.dart'; // Import du package URL launcher

import '../models/station.dart';

class StationDetailScreen extends StatelessWidget {
  final Station station;

  const StationDetailScreen({Key? key, required this.station})
      : super(key: key);

  // Méthode pour ouvrir Google Maps
  Future<void> _openGoogleMaps(double latitude, double longitude) async {
    final Uri googleMapsUrl = Uri.parse(
        'https://www.google.com/maps/dir/?api=1&destination=$latitude,$longitude&travelmode=driving');

    if (await canLaunchUrl(googleMapsUrl)) {
      await launchUrl(googleMapsUrl, mode: LaunchMode.externalApplication);
    } else {
      throw 'Impossible d\'ouvrir Google Maps.';
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(station.name),
      ),
      body: Column(
        children: [
          Expanded(
            child: GoogleMap(
              initialCameraPosition: CameraPosition(
                target: LatLng(station.latitude, station.longitude),
                zoom: 15,
              ),
              markers: {
                Marker(
                  markerId: MarkerId(station.id),
                  position: LatLng(station.latitude, station.longitude),
                  infoWindow: InfoWindow(title: station.name),
                ),
              },
            ),
          ),
          Padding(
            padding: const EdgeInsets.all(16.0),
            child: ElevatedButton.icon(
              onPressed: () {
                _openGoogleMaps(station.latitude, station.longitude);
              },
              icon: const Icon(
                Icons.directions,
                color: Colors.white,
              ),
              label: const Text(
                'Obtenir des itinéraires',
                style: TextStyle(
                  color: Colors.white,
                ),
              ),
              style: ElevatedButton.styleFrom(
                backgroundColor: Colors.blue,
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(10),
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }
}
