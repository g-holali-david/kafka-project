import 'dart:convert';

import 'package:http/http.dart' as http;

import '../models/station.dart';

class ApiService {
  static const String baseUrl = "http://192.168.0.12:8080/api/stations";

  static Future<List<Station>> fetchStations({
    required int page,
    required int size,
    String? query,
  }) async {
    // Construire l'URL avec les paramètres
    final url = Uri.parse(
      '$baseUrl/paginated?page=$page&size=$size&sortBy=name&sortDir=asc${query != null && query.isNotEmpty ? '&search=$query' : ''}',
    );

    final response = await http.get(url);

    if (response.statusCode == 200) {
      final data = json.decode(utf8.decode(response.bodyBytes));
      final stations = (data['content'] as List)
          .map((json) => Station.fromJson(json))
          .toList();
      return stations;
    } else {
      throw Exception("Failed to load stations");
    }
  }
}
