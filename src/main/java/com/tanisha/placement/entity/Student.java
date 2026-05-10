package com.tanisha.placement.entity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Entity
@Table(name = "students")

public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    private String email;
    private String branch;
    private Double cgpa;
    @Column(nullable = false)
    private String password;
    private boolean passwordChanged = false;
    private String role;
    private String resumePath;
    @Column(nullable = false)
    private boolean placed = false;

    @OneToMany(mappedBy = "student")
    @JsonIgnore
    private List<Application> applications;

    public List<Application> getApplications() {
        return applications;
    }

    public boolean isPlaced() {
        return placed;
    }

    public void setPlaced(boolean placed) {
        this.placed = placed;
    }

    public Long getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getBranch(){
        return branch;
    }

    public void setBranch(String branch){
        this.branch = branch;
    }

    public Double getCgpa(){
        return cgpa;
    }

    public void setCgpa(Double cgpa){
        this.cgpa = cgpa;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public boolean isPasswordChanged() { return passwordChanged; }
    public void setPasswordChanged(boolean passwordChanged) { this.passwordChanged = passwordChanged; }

    public String getRole(){
        return role;
    }

    public void setRole(String role){
        this.role = role;
    }

    public String getResumePath(){
        return resumePath;
    }
    public void setResumePath(String resumePath){
        this.resumePath = resumePath;
    }
}
