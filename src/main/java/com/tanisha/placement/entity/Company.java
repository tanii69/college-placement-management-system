package com.tanisha.placement.entity;

import jakarta.persistence.*;

@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;
    private String role;
    private String location;
    private double packageAmount;
    private double eligibilityCgpa;

    private String eligibleBranches;



    // Getters & Setters

    public String getEligibleBranches() {
        return eligibleBranches;
    }

    public void setEligibleBranches(String eligibleBranches) {
        this.eligibleBranches = eligibleBranches;
    }


    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getCompanyName() { return companyName; }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRole() { return role; }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLocation(){
        return location;
    }
    public void setLocation(String location){
        this.location = location;
    }

    public double getPackageAmount() { return packageAmount; }

    public void setPackageAmount(double packageAmount) {
        this.packageAmount = packageAmount;
    }

    public double getEligibilityCgpa() { return eligibilityCgpa; }

    public void setEligibilityCgpa(double eligibilityCgpa) {
        this.eligibilityCgpa = eligibilityCgpa;
    }
}
