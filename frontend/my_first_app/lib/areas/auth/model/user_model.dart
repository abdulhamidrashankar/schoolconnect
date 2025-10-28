class User {
  final String id;
  final String name;
  final String email;
  final String role;
  final String token; // optional: if API returns auth token

  User({
    required this.id,
    required this.name,
    required this.email,
    required this.role,
    required this.token,
  });

  /// Factory constructor to create User from API JSON
  factory User.fromJson(Map<String, dynamic> json) {
    return User(
      id: json['id']?.toString() ?? '',
      name: json['name'] ?? '',
      email: json['email'] ?? '',
      role: json['role'] ?? '',
      token: json['token'] ?? '',
    );
  }

  /// Convert User object to JSON (for local storage or API calls)
  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'name': name,
      'email': email,
      'role': role,
      'token': token,
    };
  }
}
