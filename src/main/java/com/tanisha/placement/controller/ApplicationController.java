package com.tanisha.placement.controller;
import com.tanisha.placement.entity.*;
import com.tanisha.placement.repository.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/applications")
public class ApplicationController {
    private final ApplicationRepository applicationRepository;
    private final StudentRepository studentRepository;
    private final CompanyRepository companyRepository;

    public ApplicationController(ApplicationRepository applicationRepository,
                                 StudentRepository studentRepository,
                                 CompanyRepository companyRepository){
        this.applicationRepository = applicationRepository;
        this.studentRepository = studentRepository;
        this.companyRepository = companyRepository;
    }

    @PostMapping("/apply")
    public Application apply(@RequestParam Long studentId,
                             @RequestParam Long companyId){

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        // 🔥 Prevent duplicate application
        if(applicationRepository.existsByStudentIdAndCompanyId(studentId, companyId)){
            throw new RuntimeException("You already applied to this company!");
        }

        // 🔥 Check eligibility
        if(student.getCgpa() < company.getEligibilityCgpa()){
            throw new RuntimeException("CGPA not eligible!");
        }

        Application application = new Application();
        application.setStudent(student);
        application.setCompany(company);
        application.setStatus("APPLIED");

        return applicationRepository.save(application);
    }


    @GetMapping
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }
    @GetMapping("/student/{id}")
    public List<Application> getApplicationsByStudent(@PathVariable Long id) {
        return applicationRepository.findAll()
                .stream()
                .filter(app -> app.getStudent().getId().equals(id))
                .toList();
    }
    @GetMapping("/company/{id}")
    public List<Application> getApplicationsByCompany(@PathVariable Long id) {
        return applicationRepository.findAll()
                .stream()
                .filter(app -> app.getCompany().getId().equals(id))
                .toList();
    }
    @PutMapping("/{id}/status")
    public Application updateStatus(@PathVariable Long id,
                                    @RequestParam String status) {

        Application app = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        app.setStatus(status.toUpperCase());

        return applicationRepository.save(app);
    }

}
