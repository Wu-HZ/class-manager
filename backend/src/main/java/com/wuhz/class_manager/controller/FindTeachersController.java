package com.wuhz.class_manager.controller;

import com.wuhz.class_manager.entity.Teacher;
import com.wuhz.class_manager.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;

@RestController
@RequestMapping("/api/teachers")
public class FindTeachersController {

    @Autowired
    private TeacherRepository teacherRepository;

    @GetMapping("/list")
    public List<Map<String, String>> getAllTeachers() {
        List<Teacher> teachers = teacherRepository.findAll();
        // 只返回teacher_id和teacher_name
        return teachers.stream()
                .map(t -> Map.of(
                        "teacher_id", t.getTeacherID(),
                        "teacher_name", t.getTeacherName()
                ))
                .collect(Collectors.toList());
    }
}