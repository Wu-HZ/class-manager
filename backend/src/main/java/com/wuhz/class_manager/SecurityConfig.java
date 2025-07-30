// backend/src/main/java/com/wuhz.class_manager/SecurityConfig.java
// 请将 com.wuhz.class_manager 替换为你的实际包名

package com.wuhz.class_manager; // 替换为你的实际包名

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer; // 导入这个类用于 disable() 方法

@Configuration // 标记这是一个配置类
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 禁用 CSRF (跨站请求伪造) 保护。对于前后端分离的API，通常需要禁用。
            // 如果不禁用，前端发送的POST/PUT/DELETE请求可能会被阻止。
            .csrf(AbstractHttpConfigurer::disable)
            // 配置请求授权规则
            .authorizeHttpRequests(authorize -> authorize
                // 允许所有 /api/** 路径下的请求，无需认证
                .requestMatchers("/api/**").permitAll()
                // 其他所有请求都需要认证 (如果你有其他受保护的端点)
                .anyRequest().authenticated()
            );
            // 如果你之前有配置 httpBasic() 或 formLogin()，可以根据需要保留或移除
            // .httpBasic(withDefaults()); // 启用 HTTP Basic 认证 (如果需要)
            // .formLogin(withDefaults()); // 启用表单登录 (如果需要)

        return http.build();
    }
}
