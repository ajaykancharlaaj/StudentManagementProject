package com.sms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sms.entity.Faculty;
import com.sms.service.FacultyService;
import java.util.Map;

@RestController
@RequestMapping("/api/faculty")
public class FacultyController {

    @Autowired
    private FacultyService facultyService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");
        Faculty faculty = facultyService.login(email, password);
        if (faculty == null) {
            return ResponseEntity.status(401).body("Invalid Email or Password");
        }
        return ResponseEntity.ok(faculty);
    }

    @PostMapping("/register")
    public ResponseEntity<Faculty> register(@RequestBody Faculty faculty) {
        return ResponseEntity.ok(facultyService.registerFaculty(faculty));
    }

    @GetMapping("/dashboard/stats")
    public ResponseEntity<?> getDashboardStats() {
        return ResponseEntity.ok(facultyService.getDashboardStats());
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<Faculty> getProfile(@PathVariable Long id) {
        return ResponseEntity.ok(facultyService.getProfile(id));
    }

    @PutMapping("/profile/{id}")
    public ResponseEntity<Faculty> updateProfile(@PathVariable Long id, @RequestBody Faculty faculty) {
        return ResponseEntity.ok(facultyService.updateProfile(id, faculty));
    }
}
