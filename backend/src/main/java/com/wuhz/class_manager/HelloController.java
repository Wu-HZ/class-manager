package com.wuhz.class_manager; // 替换为你的实际包名

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // 标记这个类是一个 RESTful Web 服务控制器
@RequestMapping("/api/hello") // 定义这个控制器处理的根路径
public class HelloController {

    @GetMapping // 标记这个方法处理 HTTP GET 请求到 /api/hello
    public String sayHello() {
        return "Hello from Spring Boot"; // 返回字符串作为响应体
    }
}