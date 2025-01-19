class Station {
  final String id;
  final String name;
  final String insee;
  final String address;
  final String emplacements;
  final String status;
  final double latitude;
  final double longitude;

  Station({
    required this.id,
    required this.name,
    required this.insee,
    required this.address,
    required this.emplacements,
    required this.status,
    required this.latitude,
    required this.longitude,
  });

  factory Station.fromJson(Map<String, dynamic> json) {
    return Station(
      id: json['id'],
      name: json['name'],
      insee: json['insee'],
      address: json['address'],
      emplacements: json['emplacements'],
      status: json['status'],
      latitude: json['latitude'],
      longitude: json['longitude'],
    );
  }
}
