//package com.tanisha.placement.controller;
//
//import com.tanisha.placement.model.Job;
//import com.tanisha.placement.entity.Student;
//import com.tanisha.placement.repository.StudentRepository;
//import com.tanisha.placement.service.RecommendationService;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/recommend")
//public class RecommendationController {
//
//    @Autowired
//    private RecommendationService recommendationService;
//
//    @Autowired
//    private StudentRepository studentRepository;
//
//    @GetMapping("/{studentId}")
//    public List<Job> getRecommendedJobs(@PathVariable Long studentId) {
//
//        Student student = studentRepository.findById(studentId).orElse(null);
//
//        return recommendationService.recommendJobs(student);
//    }
//}