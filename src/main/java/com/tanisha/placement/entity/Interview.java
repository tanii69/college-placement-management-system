package com.tanisha.placement.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long studentId;
    private Long companyId;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime interviewDate;

    private String mode;   // ONLINE / OFFLINE
    private String link;   // universal (meet OR maps)
    private String status;

    /* ✅ AUTO FIX BEFORE SAVE */
    @PrePersist
    @PreUpdate
    public void prepareData() {

        // Normalize mode (VERY IMPORTANT)
        if (mode != null) {
            mode = mode.toUpperCase();
        }

        // Default status
        if (status == null) {
            status = "SCHEDULED";
        }

        // Validation
        if (mode == null || (!mode.equals("ONLINE") && !mode.equals("OFFLINE"))) {
            throw new RuntimeException("Mode must be ONLINE or OFFLINE");
        }

        if (link == null || link.trim().isEmpty()) {
            throw new RuntimeException("Link is required");
        }
    }

    // ===== GETTERS =====
    public Long getId() { return id; }
    public Long getStudentId() { return studentId; }
    public Long getCompanyId() { return companyId; }
    public LocalDateTime getInterviewDate() { return interviewDate; }
    public String getMode() { return mode; }
    public String getLink() { return link; }
    public String getStatus() { return status; }

    // ===== SETTERS =====
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }
    public void setInterviewDate(LocalDateTime interviewDate) { this.interviewDate = interviewDate; }
    public void setMode(String mode) { this.mode = mode; }
    public void setLink(String link) { this.link = link; }
    public void setStatus(String status) { this.status = status; }
}