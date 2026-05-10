package com.tanisha.placement.controller;

import com.tanisha.placement.entity.Notification;
import com.tanisha.placement.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin
public class NotificationController {

    @Autowired
    private NotificationRepository repo;

    @GetMapping("/{studentId}")
    public List<Notification> getNotifications(@PathVariable Long studentId){
        return repo.findByStudentIdOrderByCreatedAtDesc(studentId);
    }

    @PutMapping("/read/{id}")
    public void markRead(@PathVariable Long id){
        Notification n = repo.findById(id).orElseThrow();
        n.setReadStatus(true);
        repo.save(n);
    }
}
