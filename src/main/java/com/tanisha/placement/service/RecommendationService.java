//package com.tanisha.placement.service;
//
//import com.tanisha.placement.model.Job;
//import com.tanisha.placement.entity.Student;
//import com.tanisha.placement.repository.JobRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//
//@Service
//public class RecommendationService {
//
//    @Autowired
//    private JobRepository jobRepository;
//
//    public List<Job> recommendJobs(Student student) {
//
//        List<Job> allJobs = jobRepository.findAll();
//        List<Job> recommendedJobs = new ArrayList<>();
//
//        for (Job job : allJobs) {
//
//            int score = 0;
//
//            // 🎯 Branch match
//            if (student.getBranch().equalsIgnoreCase(job.getBranch())) {
//                score += 50;
//            }
//
//            // 🎯 CGPA match
//            if (student.getCgpa() >= job.getRequiredCgpa()) {
//                score += 30;
//            }
//
//            // 🎯 High CGPA bonus
//            if (student.getCgpa() > 8) {
//                score += 20;
//            }
//
//            // 🎯 Final decision
//            if (score >= 60) {
//                recommendedJobs.add(job);
//            }
//        }
//
//        return recommendedJobs;
//    }
//}