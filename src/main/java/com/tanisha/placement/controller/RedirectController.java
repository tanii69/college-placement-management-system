package com.tanisha.placement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;

@Controller
public class RedirectController {
    @GetMapping("/redirect")
    public String redirectAfterLogin(Authentication authentication) {

        String role = authentication.getAuthorities()
                .iterator()
                .next()
                .getAuthority();

        if (role.equals("ROLE_ADMIN")) {
            return "redirect:/admin/admin.html";
        } else {
            return "redirect:/student/student-dashboard.html";
        }
    }

}
