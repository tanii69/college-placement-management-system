package com.tanisha.placement.controller;

import com.tanisha.placement.entity.Interview;
import com.tanisha.placement.entity.Notification;
import com.tanisha.placement.repository.InterviewRepository;
import com.tanisha.placement.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interviews")
@CrossOrigin
public class InterviewController {

    @Autowired
    private InterviewRepository repo;

    @Autowired
    private NotificationRepository notificationRepo;

    /* ================= CREATE ================= */
    @PostMapping
    public Interview schedule(@RequestBody Interview interview){

        // Normalize mode
        interview.setMode(interview.getMode().toUpperCase());

        if (interview.getLink() == null || interview.getLink().trim().isEmpty()) {
            throw new RuntimeException("Link is required!");
        }

        interview.setStatus("SCHEDULED");

        Interview saved = repo.save(interview);

        // 🔔 Notification
        Notification n = new Notification();
        n.setStudentId(saved.getStudentId());

        String msg = "Interview on " + saved.getInterviewDate();

        if ("ONLINE".equals(saved.getMode())) {
            msg += " (Online)";
        } else {
            msg += " (Offline)";
        }

        n.setMessage(msg);
        notificationRepo.save(n);

        return saved;
    }

    /* ================= GET ALL ================= */
    @GetMapping
    public List<Interview> getAll(){
        return repo.findAll();
    }

    /* ================= DELETE ================= */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        repo.deleteById(id);
    }

    /* ================= UPDATE STATUS ================= */
    @PutMapping("/{id}/status")
    public Interview updateStatus(@PathVariable Long id, @RequestParam String status){

        Interview interview = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Interview not found"));

        interview.setStatus(status);
        return repo.save(interview);
    }

    /* ================= UPDATE INTERVIEW (🔥 MAIN FIX) ================= */
    @PutMapping("/{id}")
    public Interview updateInterview(@PathVariable Long id,
                                     @RequestBody Interview updated){

        Interview interview = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Interview not found"));

        // Update fields
        interview.setStudentId(updated.getStudentId());
        interview.setCompanyId(updated.getCompanyId());
        interview.setInterviewDate(updated.getInterviewDate());

        // Normalize mode
        String mode = updated.getMode().toUpperCase();
        interview.setMode(mode);

        if (updated.getLink() == null || updated.getLink().trim().isEmpty()) {
            throw new RuntimeException("Link is required!");
        }

        interview.setLink(updated.getLink());

        return repo.save(interview);
    }
}