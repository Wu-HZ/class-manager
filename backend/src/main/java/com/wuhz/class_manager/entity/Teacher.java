package com.wuhz.class_manager.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Teacher {
    
    @Id // 确保有主键注解
    @Column(name = "teacher_id")
    private String teacherID; // 使用实际主键字段
    
    @Column(name = "teacher_password")
    private String teacherPassword; // 密码字段
    
    @Column(name = "teacher_name")
    private String teacherName; // 教师姓名字段

    // 必须有无参构造
    public Teacher() {}
    
    // getters和setters
    public String getTeacherID() {
        return teacherID;
    }
    
    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }

    public String getTeacherPassword() { // 正确实现
        return teacherPassword;
    }

    public void setTeacherPassword(String teacherPassword) {
        this.teacherPassword = teacherPassword;
    }

    public String getTeacherName() {
        return teacherName;
    }
    
    // ... 其他字段
}