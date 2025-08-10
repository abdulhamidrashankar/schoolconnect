import 'package:my_first_app/config/config_service.dart';

class UrlUtils {
  static final config = ConfigService().config;

  static String buildLoginUrl() {
    final scheme = config?['httpScheme'] ?? 'http';
    final host = config?['backendHost'] ?? 'localhost';
    final port = config?['backendPort']?.toString() ?? '80';
    final baseApi = config?['apiBasePath'] ?? '';
    final loginPath = config?['loginPath'] ?? '/login';
    return '$scheme://$host:$port$baseApi$loginPath';
  }

  static String buildLogoutUrl() {
    final scheme = config?['httpScheme'] ?? 'http';
    final host = config?['backendHost'] ?? 'localhost';
    final port = config?['backendPort']?.toString() ?? '80';
    final baseApi = config?['apiBasePath'] ?? '';
    final logoutPath = config?['logoutPath'] ?? '/logout';
    return '$scheme://$host:$port$baseApi$logoutPath';
  }
}
