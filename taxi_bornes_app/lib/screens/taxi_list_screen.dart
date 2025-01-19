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
  final SearchController _searchController = SearchController();
  List<Station> stations = [];
  int currentPage = 0;
  int totalPages = 1;
  bool isLoading = false;

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
      final fetchedStations = await ApiService.fetchStations(
        page: currentPage,
        size: 5,
        query: query,
      );

      setState(() {
        stations = fetchedStations;
        totalPages = 10; // Ajustez cette valeur en fonction de l'API
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

  void _goToNextPage() {
    if (currentPage < totalPages - 1) {
      setState(() {
        currentPage++;
      });
      _fetchStations();
    }
  }

  void _goToPreviousPage() {
    if (currentPage > 0) {
      setState(() {
        currentPage--;
      });
      _fetchStations();
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: CustomScrollView(
          slivers: <Widget>[
            SliverAppBar(
              floating: true,
              title: SearchAnchor.bar(
                searchController: _searchController,
                barLeading: Icon(
                  Icons.search,
                  color: Colors.blue, // Icône de recherche en bleu
                ),
                barTrailing: [
                  IconButton(
                    icon: Icon(
                      Icons.close,
                      color: Colors.blue, // Icône de fermeture en bleu
                    ),
                    onPressed: () {
                      _searchController.clear();
                    },
                  ),
                ],
                barHintText: 'Rechercher une station...',
                barTextStyle: WidgetStateProperty.all(
                  TextStyle(color: Colors.blue.shade900, fontSize: 16),
                ),
                barShape: WidgetStateProperty.all(
                  RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(10),
                  ),
                ),
                barElevation: WidgetStateProperty.all(4), // Ombre pour la barre
                barOverlayColor: WidgetStateProperty.all(
                  Colors.blue.withOpacity(0.1),
                ),
                onChanged: (query) {
                  _fetchStations(query: query);
                },
                suggestionsBuilder:
                    (BuildContext context, SearchController controller) {
                  final query = controller.text;

                  if (query.isEmpty) {
                    return const [
                      ListTile(
                        titleAlignment: ListTileTitleAlignment.center,
                        title: Text('Aucun résultat'),
                      ),
                    ];
                  }

                  final filteredStations = stations
                      .where((station) => station.name
                          .toLowerCase()
                          .contains(query.toLowerCase()))
                      .toList();

                  return filteredStations.map((station) {
                    return ListTile(
                      tileColor: Colors.white,
                      title: Text(
                        station.name,
                        style: const TextStyle(color: Colors.black),
                      ),
                      onTap: () {
                        Navigator.push(
                          context,
                          MaterialPageRoute(
                            builder: (context) =>
                                StationDetailScreen(station: station),
                          ),
                        );
                      },
                    );
                  }).toList();
                },
              ),
            ),
            SliverToBoxAdapter(
              child: isLoading
                  ? const Center(child: CircularProgressIndicator())
                  : ListView.builder(
                      physics: const NeverScrollableScrollPhysics(),
                      shrinkWrap: true,
                      itemCount: stations.length,
                      itemBuilder: (context, index) {
                        final station = stations[index];
                        return Card(
                          elevation: 2,
                          margin: const EdgeInsets.symmetric(
                              vertical: 8, horizontal: 16),
                          shape: RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(12),
                          ),
                          child: ListTile(
                            leading: Icon(
                              Icons.local_taxi,
                              color: Colors.blue,
                              size: 32,
                            ),
                            title: Text(
                              station.name,
                              style:
                                  const TextStyle(fontWeight: FontWeight.bold),
                            ),
                            subtitle: Text(station.address),
                            trailing: Icon(
                              Icons.arrow_forward_ios,
                              color: Colors.grey,
                              size: 16,
                            ),
                            onTap: () {
                              Navigator.push(
                                context,
                                MaterialPageRoute(
                                  builder: (context) =>
                                      StationDetailScreen(station: station),
                                ),
                              );
                            },
                          ),
                        );
                      },
                    ),
            ),
            SliverToBoxAdapter(
              child: Padding(
                padding:
                    const EdgeInsets.symmetric(vertical: 8, horizontal: 16),
                child: SingleChildScrollView(
                  scrollDirection: Axis.horizontal,
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    crossAxisAlignment: CrossAxisAlignment.center,
                    children: [
                      ElevatedButton.icon(
                        onPressed: currentPage > 0 ? _goToPreviousPage : null,
                        icon: const Icon(
                          Icons.arrow_back,
                          color: Colors.white,
                        ),
                        label: const Text(
                          'Précédent',
                          style: TextStyle(color: Colors.white),
                        ),
                        style: ElevatedButton.styleFrom(
                          backgroundColor: Colors.blue,
                          shape: RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(10),
                          ),
                        ),
                      ),
                      SizedBox(
                        width: 10.0,
                      ),
                      Text(
                        'Page ${currentPage + 1} / $totalPages',
                        style: TextStyle(
                            color: Colors.blueAccent,
                            fontWeight: FontWeight.bold),
                      ),
                      SizedBox(
                        width: 10.0,
                      ),
                      ElevatedButton.icon(
                        onPressed:
                            currentPage < totalPages - 1 ? _goToNextPage : null,
                        icon: const Icon(
                          Icons.arrow_forward,
                          color: Colors.white,
                        ),
                        label: const Text(
                          'Suivant',
                          style: TextStyle(color: Colors.white),
                        ),
                        style: ElevatedButton.styleFrom(
                          backgroundColor: Colors.blue,
                          shape: RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(10),
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
