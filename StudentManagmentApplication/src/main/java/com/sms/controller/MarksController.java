package com.sms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sms.entity.Marks;
import com.sms.service.MarksService;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/marks")
public class MarksController {

    @Autowired
    private MarksService marksService;

    // Faculty: Add Marks
    @PostMapping
    public ResponseEntity<?> addMarks(@RequestBody Map<String, Object> request) {
        Long studentId = Long.valueOf(request.get("studentId").toString());
        String subject = request.get("subject").toString();
        Double marksObtained = Double.valueOf(request.get("marksObtained").toString());
        Double maxMarks = Double.valueOf(request.get("maxMarks").toString());
        Marks marks = marksService.addMarks(studentId, subject, marksObtained, maxMarks);
        return ResponseEntity.ok(marks);
    }

    // Faculty: Update Marks
    @PutMapping("/{id}")
    public ResponseEntity<?> updateMarks(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        Double marksObtained = Double.valueOf(request.get("marksObtained").toString());
        Double maxMarks = Double.valueOf(request.get("maxMarks").toString());
        Marks marks = marksService.updateMarks(id, marksObtained, maxMarks);
        return ResponseEntity.ok(marks);
    }

    // Faculty: View All Marks
    @GetMapping
    public ResponseEntity<List<Marks>> getAllMarks() {
        return ResponseEntity.ok(marksService.getAllMarks());
    }

    // Student/Faculty: View Results (Marks of specific student)
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Marks>> getMarksByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(marksService.getMarksByStudent(studentId));
    }
}
