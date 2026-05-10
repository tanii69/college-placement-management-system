package com.tanisha.placement.service;

import com.tanisha.placement.entity.Student;
import com.tanisha.placement.repository.StudentRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.List;

import java.util.Collections;
@Service
public class CustomUserDetailsService implements UserDetailsService{
    private final StudentRepository studentRepository;

    public CustomUserDetailsService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new User(
                student.getEmail(),
                student.getPassword(),
                List.of(new SimpleGrantedAuthority(student.getRole()))
        );
    }
}
