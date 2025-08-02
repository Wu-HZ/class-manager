package com.wuhz.class_manager.repository;

import com.wuhz.class_manager.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, String> {
    // 使用大写的ID匹配实体属性
    Teacher findByTeacherID(String teacherID);
}