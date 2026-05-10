package com.tanisha.placement.controller;

import com.tanisha.placement.entity.Company;
import com.tanisha.placement.entity.Student;
import com.tanisha.placement.repository.CompanyRepository;
import com.tanisha.placement.repository.StudentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyRepository companyRepository;
    private final StudentRepository studentRepository;

    public CompanyController(CompanyRepository companyRepository,
                             StudentRepository studentRepository) {
        this.companyRepository = companyRepository;
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @PostMapping
    public Company addCompany(@RequestBody Company company) {
        return companyRepository.save(company);
    }

    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable Long id) {
        companyRepository.deleteById(id);
    }

    @GetMapping("/{id}/eligible-students")
    public List<Student> getEligibleStudents(@PathVariable Long id) {

        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        List<String> allowedBranches =
                Arrays.stream(company.getEligibleBranches().split(","))
                        .map(String::trim)
                        .map(String::toLowerCase)
                        .toList();

        return studentRepository.findAll()
                .stream()
                .filter(student ->
                        student.getCgpa() >= company.getEligibilityCgpa()
                                &&
                                allowedBranches.contains(student.getBranch().toLowerCase())
                )
                .toList();
    }

}
