package com.tanisha.placement.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.tanisha.placement.entity.Student;
import com.tanisha.placement.repository.StudentRepository;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.Authentication;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/student")
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // ================================
    // ✅ UPLOAD RESUME
    // ================================
    @PostMapping("/uploadResume/{id}")
    public ResponseEntity<String> uploadResume(@PathVariable Long id,
                                               @RequestParam("file") MultipartFile file) {
        try {

            // ✅ Only allow PDF
            if (!file.getContentType().equals("application/pdf")) {
                return ResponseEntity.badRequest().body("Only PDF files allowed");
            }

            // ✅ Create folder if not exists
            String uploadDir = "C:/uploads/";
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // ✅ Safe file name
            String fileName = id + "_" + file.getOriginalFilename().replaceAll("\\s+", "_");
            String filePath = uploadDir + fileName;

            // ✅ Save file
            file.transferTo(new File(filePath));

            // ✅ Save path in DB
            Student student = studentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Student not found"));

            student.setResumePath(filePath);
            studentRepository.save(student);

            return ResponseEntity.ok("Resume uploaded successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Upload failed: " + e.getMessage());
        }
    }

    // ================================
    // ✅ VIEW RESUME (PDF OPEN)
    // ================================
    @GetMapping("/resume/{id}")
    public ResponseEntity<Resource> viewResume(@PathVariable Long id) throws IOException {

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // ❗ Handle no resume
        if (student.getResumePath() == null) {
            throw new RuntimeException("Resume not uploaded");
        }

        Path path = Paths.get(student.getResumePath());
        Resource resource = new UrlResource(path.toUri());

        // ❗ Handle missing file
        if (!resource.exists()) {
            throw new RuntimeException("File not found");
        }

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    // ================================
    // ✅ GET LOGGED-IN STUDENT
    // ================================
    @GetMapping("/me")
    public Student getLoggedInStudent(Authentication authentication) {

        String email = authentication.getName();

        return studentRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    // ================================
    // ✅ GET ALL STUDENTS
    // ================================
    @GetMapping
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // ================================
    // ✅ COUNT STUDENTS
    // ================================
    @GetMapping("/count")
    public long getStudentCount() {
        return studentRepository.count();
    }

    // ================================
    // ✅ ADD STUDENT
    // ================================
    @PostMapping
    public Student addStudent(@RequestBody Student student) {

        if (student.getName() == null || student.getName().trim().isEmpty()) {
            throw new RuntimeException("Name is required!");
        }

        if (student.getEmail() == null || student.getEmail().trim().isEmpty()) {
            throw new RuntimeException("Email is required!");
        }

        if (studentRepository.findByEmail(student.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists!");
        }

        if (student.getPassword() == null || student.getPassword().isEmpty()) {
            throw new RuntimeException("Password is required!");
        }

        student.setRole("ROLE_STUDENT");

        return studentRepository.save(student);
    }

    // ================================
    // ✅ UPDATE STUDENT
    // ================================
    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable Long id,
                                 @RequestBody Student updatedStudent) {

        return studentRepository.findById(id)
                .map(student -> {
                    student.setName(updatedStudent.getName());
                    student.setEmail(updatedStudent.getEmail());
                    student.setBranch(updatedStudent.getBranch());
                    student.setCgpa(updatedStudent.getCgpa());

                    // 🔥 ADD THIS
                    if(updatedStudent.getPassword() != null && !updatedStudent.getPassword().isEmpty()) {
                        student.setPassword(updatedStudent.getPassword());
                    }
                    return studentRepository.save(student);
                })
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    // ================================
    // ✅ DELETE STUDENT
    // ================================
    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentRepository.deleteById(id);
    }


    @GetMapping("/analytics")
    public Map<String, Object> getAnalytics() {

        List<Student> students = studentRepository.findAll();

        long total = students.size();
        long placed = students.stream().filter(Student::isPlaced).count();
        long notPlaced = total - placed;

        double avgCgpa = students.stream()
                .mapToDouble(Student::getCgpa)
                .average()
                .orElse(0);

        Map<String, Long> branchWise = students.stream()
                .collect(Collectors.groupingBy(
                        Student::getBranch,
                        Collectors.counting()
                ));

        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("placed", placed);
        data.put("notPlaced", notPlaced);
        data.put("avgCgpa", avgCgpa);
        data.put("branchWise", branchWise);

        return data;
    }

    @GetMapping("/branch-analytics")
    public Map<String, Long> getBranchAnalytics(){

        List<Student> students = studentRepository.findAll();

        return students.stream()
                .collect(Collectors.groupingBy(
                        Student::getBranch,
                        Collectors.counting()
                ));
    }

    @GetMapping("/placed-branch-analytics")
    public Map<String, Long> getPlacedBranchAnalytics() {

        List<Student> students = studentRepository.findAll();

        Map<String, Long> branchMap = new HashMap<>();

        for (Student s : students) {

            // 🔥 CHECK FROM APPLICATION TABLE
            boolean isPlaced = s.getApplications().stream()
                    .anyMatch(app -> "SELECTED".equalsIgnoreCase(app.getStatus()));

            if (isPlaced) {

                String branch = s.getBranch();

                if (branch == null || branch.trim().isEmpty()) {
                    branch = "Unknown";
                }

                branchMap.put(branch, branchMap.getOrDefault(branch, 0L) + 1);
            }
        }

        return branchMap;
    }
}