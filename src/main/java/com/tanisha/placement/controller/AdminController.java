package com.tanisha.placement.controller;

import com.tanisha.placement.entity.Student;
import com.tanisha.placement.repository.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private StudentRepository studentRepository;

    // 🔹 Get all students
    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // 🔹 Delete student
    @DeleteMapping("/deleteStudent/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentRepository.deleteById(id);
        return "Student deleted successfully!";
    }

    // 🔹 Download Resume
    @GetMapping("/downloadResume/{id}")
    public ResponseEntity<InputStreamResource> downloadResume(@PathVariable Long id) throws Exception {

        Student student = studentRepository.findById(id).orElseThrow();

        File file = new File(student.getResumePath());
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=" + file.getName())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}