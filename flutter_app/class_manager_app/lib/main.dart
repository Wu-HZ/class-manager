    // lib/main.dart

    import 'package:flutter/material.dart';
    import 'package:class_manager_app/services/api_service.dart'; // 导入你刚刚创建的 ApiService

    void main() {
      runApp(const MyApp());
    }

    class MyApp extends StatelessWidget {
      const MyApp({super.key});

      @override
      Widget build(BuildContext context) {
        return MaterialApp(
          title: 'Class Manager App',
          theme: ThemeData(
            primarySwatch: Colors.blue,
          ),
          home: const HomePage(), // 设置主页为 HomePage
        );
      }
    }

    class HomePage extends StatefulWidget {
      const HomePage({super.key});

      @override
      State<HomePage> createState() => _HomePageState();
    }

    class _HomePageState extends State<HomePage> {
      late Future<String> _helloMessage; // 用于存储 Future 结果

      @override
      void initState() {
        super.initState();
        _helloMessage = ApiService().getHello(); // 在 initState 中调用 API
      }

      @override
      Widget build(BuildContext context) {
        return Scaffold(
          appBar: AppBar(
            title: const Text('Spring Boot 后端连接测试'),
          ),
          body: Center(
            child: FutureBuilder<String>(
              future: _helloMessage, // 监听 _helloMessage 这个 Future
              builder: (context, snapshot) {
                if (snapshot.connectionState == ConnectionState.waiting) {
                  // 数据还在加载中
                  return const CircularProgressIndicator();
                } else if (snapshot.hasError) {
                  // 发生错误
                  return Text('错误: ${snapshot.error}');
                } else if (snapshot.hasData) {
                  // 数据加载成功
                  return Text(
                    '后端响应: ${snapshot.data}',
                    style: const TextStyle(fontSize: 20),
                  );
                } else {
                  // 没有数据
                  return const Text('没有数据');
                }
              },
            ),
          ),
        );
      }
    }
    