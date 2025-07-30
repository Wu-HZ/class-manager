// lib/services/api_service.dart

import 'package:http/http.dart' as http;
import 'dart:convert'; // 用于 base64 编码

class ApiService {
  static const String baseUrl = "http://192.168.137.1:8080/api"; // Android 模拟器地址

  // 定义认证凭据
  static const String username = "fixeduser"; // 替换为你 application.yml 中设置的用户名
  static const String password = "xFqHWFRSyNU9Ywspa9Qb"; // 替换为你 application.yml 中设置的密码

  // 生成 Basic 认证头
  static Map<String, String> get _authHeaders {
    final String basicAuth = 'Basic ' + base64Encode(utf8.encode('$username:$password'));
    return {
      'Content-Type': 'application/json',
      'Authorization': basicAuth,
    };
  }

  Future<String> getHello() async {
    try {
      final response = await http.get(
        Uri.parse('$baseUrl/hello'),
        headers: _authHeaders, // 添加认证头
      );

      if (response.statusCode == 200) {
        return response.body;
      } else {
        // 如果不是 200，打印详细错误信息
        print('HTTP 状态码: ${response.statusCode}');
        print('响应体: ${response.body}');
        throw Exception('Failed to load hello message: ${response.statusCode}');
      }
    } catch (e) {
      print('连接服务器失败: $e');
      throw Exception('Failed to connect to the server: $e');
    }
  }

  // ... 其他方法 (例如 postData)
}
