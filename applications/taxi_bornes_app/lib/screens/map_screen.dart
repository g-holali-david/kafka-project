import 'package:flutter/material.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';

import '../models/station.dart';

class MapScreen extends StatefulWidget {
  final List<Station> stations;

  const MapScreen({Key? key, required this.stations}) : super(key: key);

  @override
  State<MapScreen> createState() => _MapScreenState();
}

class _MapScreenState extends State<MapScreen> {
  GoogleMapController? _mapController;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: GoogleMap(
        initialCameraPosition: const CameraPosition(
          target: LatLng(48.8566, 2.3522), // Default to Paris
          zoom: 12.0,
        ),
        onMapCreated: (controller) => _mapController = controller,
        markers: widget.stations
            .map(
              (station) => Marker(
                markerId: MarkerId(station.id),
                position: LatLng(station.latitude, station.longitude),
                infoWindow: InfoWindow(
                  title: station.name,
                  snippet: station.address,
                ),
              ),
            )
            .toSet(),
      ),
    );
  }
}
