package com.wuhz.class_manager.controller;

import com.wuhz.class_manager.entity.Teacher;
import com.wuhz.class_manager.repository.TeacherRepository;
import com.wuhz.class_manager.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private TeacherRepository teacherRepository;

    @PostMapping
    public Map<String, Object> login(@RequestParam String teacherID, @RequestParam String teacher_password) {
        Teacher teacher = teacherRepository.findByTeacherID(teacherID);
        Map<String, Object> result = new HashMap<>();
        if (teacher == null) {
            result.put("msg", "用户不存在");
            result.put("success", false);
            return result;
        }
        if (teacher.getTeacherPassword().equals(teacher_password)) {
            String token = JwtUtil.generateToken(teacherID);
            result.put("msg", "登录成功");
            result.put("success", true);
            result.put("token", token);
            System.out.println(token);
            return result;
        } else {
            result.put("msg", "密码错误");
            result.put("success", false);
            return result;
        }
    }
}