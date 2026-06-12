package com.sms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sms.entity.Attendance;
import com.sms.service.AttendanceService;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    // Faculty: Mark Attendance
    @PostMapping
    public ResponseEntity<?> markAttendance(@RequestBody Map<String, Object> request) {
        Long studentId = Long.valueOf(request.get("studentId").toString());
        String status = request.get("status").toString();
        LocalDate date = LocalDate.now();
        if (request.containsKey("date")) {
            date = LocalDate.parse(request.get("date").toString());
        }
        Attendance attendance = attendanceService.markAttendance(studentId, date, status);
        return ResponseEntity.ok(attendance);
    }

    // Faculty: Update Attendance
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAttendance(@PathVariable Long id, @RequestBody Map<String, String> request) {
        String status = request.get("status");
        Attendance attendance = attendanceService.updateAttendance(id, status);
        return ResponseEntity.ok(attendance);
    }

    // Faculty: View All Attendance
    @GetMapping
    public ResponseEntity<List<Attendance>> getAllAttendance() {
        return ResponseEntity.ok(attendanceService.getAllAttendance());
    }

    // Student/Faculty: View Attendance of specific student
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Attendance>> getAttendanceByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(attendanceService.getAttendanceByStudent(studentId));
    }
}
