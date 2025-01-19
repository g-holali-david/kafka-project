import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:taxi_bornes_app/model/station.dart';


class ApiService {
  static const String baseUrl = "http://localhost:8080/api/stations";

  static Future<List<Station>> fetchStations(int page, int size) async {
    final response = await http.get(Uri.parse(
        '$baseUrl/paginated?page=$page&size=$size&sortBy=name&sortDir=asc'));
    if (response.statusCode == 200) {
      final data = json.decode(response.body);
      final stations = (data['content'] as List)
          .map((json) => Station.fromJson(json))
          .toList();
      return stations;
    } else {
      throw Exception("Failed to load stations");
    }
  }
}
