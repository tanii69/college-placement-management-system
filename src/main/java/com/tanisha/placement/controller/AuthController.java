package com.tanisha.placement.controller;

import com.tanisha.placement.entity.Student;
import com.tanisha.placement.repository.StudentRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(StudentRepository studentRepository,
                          PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public Student register(@RequestBody Student student) {

        if (studentRepository.findByEmail(student.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists!");
        }

        student.setPassword(passwordEncoder.encode(student.getPassword()));
        student.setRole("ROLE_STUDENT");
        student.setPasswordChanged(false);

        return studentRepository.save(student);
    }

    @PostMapping("/login")
    public Object login(@RequestBody Student request){

        Student student = studentRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email"));

        // ✅ password check (encrypted)
        if(!passwordEncoder.matches(request.getPassword(), student.getPassword())){
            throw new RuntimeException("Invalid password");
        }

        // ✅ FIRST LOGIN CHECK (THIS IS WHAT YOU WANTED)
        if(!student.isPasswordChanged()){
            return "CHANGE_PASSWORD_REQUIRED";
        }

        return student;
    }

    @PutMapping("/change-password")
    public String changePassword(@RequestParam Long studentId,
                                 @RequestParam String newPassword){

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        student.setPassword(passwordEncoder.encode(newPassword));
        student.setPasswordChanged(true);

        studentRepository.save(student);

        return "Password updated!";
    }
}