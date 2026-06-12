package com.sms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sms.entity.Student;
import com.sms.service.StudentService;
import java.util.List;
import java.util.Map;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    // --- LEGACY ENDPOINTS (to support existing frontends/tests) ---

    @PostMapping("/adduser")
    public ResponseEntity<?> addStudentLegacy(@RequestBody Student student) {
        studentService.addStudent(student);
        // Replicating original behavior: BAD_REQUEST (400) with string body
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User Inserted SuccessFully");
    }

    @GetMapping("/allusers")
    public ResponseEntity<String> getAllUsersLegacy() {
        studentService.getAllStudents();
        // Replicating original behavior: ACCEPTED (202) with string body
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Got THe list");
    }

    @DeleteMapping("/delete/{id}")
    public void deleteByIdLegacy(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }

    @PutMapping("/update/{id}")
    public Student updateUserLegacy(@PathVariable Long id, @RequestBody Student updatedStudent) {
        return studentService.updateStudent(id, updatedStudent);
    }

    @GetMapping("/find/{email}")
    public Student getByEmailLegacy(@RequestParam(value = "email", required = false) String email, @PathVariable(value = "email", required = false) String pathEmail) {
        // Supporting both request param or path variable due to original code mismatch
        String searchEmail = (email != null) ? email : pathEmail;
        return studentService.getStudentByEmail(searchEmail);
    }

    @GetMapping("/getuser/{id}")
    public Student getUserByIDLegacy(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginAppLegacy(@RequestBody Student student) {
        Student authenticated = studentService.login(student.getEmail(), student.getPassword());
        if (authenticated == null) {
            // Replicating original BAD_GATEWAY behavior
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("user Not Found");
        } else {
            // Replicating original BAD_GATEWAY behavior
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("User Login Success");
        }
    }

 

    @PostMapping("/api/students")
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.addStudent(student));
    }

    @GetMapping("/api/students")
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/api/students/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @PutMapping("/api/students/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        return ResponseEntity.ok(studentService.updateStudent(id, student));
    }

    @DeleteMapping("/api/students/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/api/student/login")
    public ResponseEntity<?> loginStudent(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");
        Student student = studentService.login(email, password);
        if (student == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Email or Password");
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping("/api/student/dashboard/stats/{id}")
    public ResponseEntity<?> getStudentDashboardStats(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentDashboardStats(id));
    }

    @GetMapping("/api/student/profile/{id}")
    public ResponseEntity<Student> getProfile(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @PutMapping("/api/student/profile/{id}")
    public ResponseEntity<Student> updateProfile(@PathVariable Long id, @RequestBody Student student) {
        return ResponseEntity.ok(studentService.updateStudent(id, student));
    }
}
