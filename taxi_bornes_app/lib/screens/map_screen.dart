import 'package:flutter/material.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:taxi_bornes_app/model/station.dart';
import 'package:taxi_bornes_app/service/api_service.dart';


class MapScreen extends StatefulWidget {
  const MapScreen({Key? key}) : super(key: key);

  @override
  State<MapScreen> createState() => _MapScreenState();
}

class _MapScreenState extends State<MapScreen> {
  GoogleMapController? _mapController;
  List<Station> stations = [];
  final TextEditingController _searchController = TextEditingController();
  final CameraPosition _initialPosition = const CameraPosition(
    target: LatLng(48.8566, 2.3522), // Coordonnées par défaut (Paris)
    zoom: 12.0,
  );

  @override
  void initState() {
    super.initState();
    _fetchStations();
  }

  Future<void> _fetchStations() async {
    try {
      final fetchedStations = await ApiService.fetchStations(0, 50);
      setState(() {
        stations = fetchedStations;
      });
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Error loading stations: $e')),
      );
    }
  }

  void _searchStation(String query) {
    if (query.isEmpty) return;
    final matchingStation = stations.firstWhere(
      (station) =>
          station.name.toLowerCase().contains(query.toLowerCase()),
      orElse: () => Station(
        id: '',
        name: 'Not Found',
        insee: '',
        address: '',
        emplacements: '',
        status: '',
        latitude: 0.0,
        longitude: 0.0,
      ),
    );

    if (matchingStation.latitude != 0.0 && matchingStation.longitude != 0.0) {
      _mapController?.animateCamera(
        CameraUpdate.newLatLng(
          LatLng(matchingStation.latitude, matchingStation.longitude),
        ),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Stack(
        children: [
          GoogleMap(
            initialCameraPosition: _initialPosition,
            onMapCreated: (controller) => _mapController = controller,
            markers: stations
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
          Positioned(
            top: 40,
            left: 10,
            right: 10,
            child: Card(
              elevation: 4,
              child: Padding(
                padding: const EdgeInsets.symmetric(horizontal: 10),
                child: Row(
                  children: [
                    Expanded(
                      child: TextField(
                        controller: _searchController,
                        decoration: const InputDecoration(
                          hintText: 'Search for a station...',
                          border: InputBorder.none,
                        ),
                        onSubmitted: _searchStation,
                      ),
                    ),
                    IconButton(
                      icon: const Icon(Icons.search),
                      onPressed: () => _searchStation(_searchController.text),
                    ),
                  ],
                ),
              ),
            ),
          ),
          Positioned(
            bottom: 20,
            right: 10,
            child: FloatingActionButton(
              onPressed: () {
                _mapController?.animateCamera(
                  CameraUpdate.zoomIn(),
                );
              },
              child: const Icon(Icons.zoom_in),
            ),
          ),
          Positioned(
            bottom: 80,
            right: 10,
            child: FloatingActionButton(
              onPressed: () {
                _mapController?.animateCamera(
                  CameraUpdate.zoomOut(),
                );
              },
              child: const Icon(Icons.zoom_out),
            ),
          ),
        ],
      ),
    );
  }
}
