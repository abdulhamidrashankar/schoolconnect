class ConfigService {
  static final ConfigService _instance = ConfigService._internal();
  factory ConfigService() => _instance;
  ConfigService._internal();

  Map<String, dynamic>? config;

  void setConfig(Map<String, dynamic> cfg) {
    config = cfg;
  }
}