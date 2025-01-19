import 'package:flutter/material.dart';

import '../models/station.dart';
import '../services/api_service.dart';
import 'station_detail_screen.dart';

class TaxiListScreen extends StatefulWidget {
  const TaxiListScreen({Key? key}) : super(key: key);

  @override
  State<TaxiListScreen> createState() => _TaxiListScreenState();
}

class _TaxiListScreenState extends State<TaxiListScreen> {
  List<Station> stations = [];
  int currentPage = 0;
  int totalPages = 1; // Nombre total de pages
  bool isLoading = false;
  String searchTerm = "";

  final TextEditingController _searchController = TextEditingController();

  @override
  void initState() {
    super.initState();
    _fetchStations();
  }

  Future<void> _fetchStations({String? query}) async {
    if (isLoading) return;

    setState(() {
      isLoading = true;
    });

    try {
      final newStations = await ApiService.fetchStations(
        page: currentPage,
        size: 5,
        query: query,
      );
      setState(() {
        stations = newStations;
        totalPages = 10; // Ajustez selon votre API
      });
    } catch (e) {
      ScaffoldMessenger.of(context)
          .showSnackBar(SnackBar(content: Text('Erreur : $e')));
    } finally {
      setState(() {
        isLoading = false;
      });
    }
  }

  void _searchStations(String query) {
    setState(() {
      currentPage = 0;
      searchTerm = query;
    });
    _fetchStations(query: query);
  }

  void _goToNextPage() {
    if (currentPage < totalPages - 1) {
      setState(() {
        currentPage++;
      });
      _fetchStations(query: searchTerm);
    }
  }

  void _goToPreviousPage() {
    if (currentPage > 0) {
      setState(() {
        currentPage--;
      });
      _fetchStations(query: searchTerm);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Stations de Taxis')),
      body: Column(
        children: [
          Padding(
            padding: const EdgeInsets.all(8.0),
            child: TextField(
              controller: _searchController,
              decoration: InputDecoration(
                labelText: 'Rechercher une station',
                border: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(8.0),
                ),
                suffixIcon: IconButton(
                  icon: const Icon(Icons.search),
                  onPressed: () => _searchStations(_searchController.text),
                ),
              ),
              onSubmitted: (value) => _searchStations(value),
            ),
          ),
          Expanded(
            child: ListView.builder(
              itemCount: stations.length,
              itemBuilder: (context, index) {
                final station = stations[index];
                return GestureDetector(
                  onTap: () {
                    Navigator.push(
                      context,
                      MaterialPageRoute(
                        builder: (context) =>
                            StationDetailScreen(station: station),
                      ),
                    );
                  },
                  child: Container(
                    color: index % 2 == 0 ? Colors.grey[200] : Colors.white,
                    padding: const EdgeInsets.all(16),
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text(
                          station.name,
                          style: const TextStyle(
                              fontSize: 16, fontWeight: FontWeight.bold),
                        ),
                        const SizedBox(height: 4),
                        Text(
                          station.address,
                          style: const TextStyle(fontSize: 14),
                        ),
                      ],
                    ),
                  ),
                );
              },
            ),
          ),
          Padding(
            padding: const EdgeInsets.symmetric(vertical: 8, horizontal: 16),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                ElevatedButton(
                  onPressed: currentPage > 0 ? _goToPreviousPage : null,
                  child: const Text('Précédent'),
                ),
                Text('Page ${currentPage + 1} / $totalPages'),
                ElevatedButton(
                  onPressed:
                      currentPage < totalPages - 1 ? _goToNextPage : null,
                  child: const Text('Suivant'),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}
