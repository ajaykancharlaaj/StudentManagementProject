package com.sms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sms.entity.Attendance;
import com.sms.entity.Student;
import com.sms.repository.AttendanceRepository;
import com.sms.repository.StudentRepository;
import java.time.LocalDate;
import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private StudentRepository studentRepository;

    public Attendance markAttendance(Long studentId, LocalDate date, String status) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));
        
        Attendance existing = attendanceRepository.findByStudentIdAndDate(studentId, date);
        if (existing != null) {
            existing.setStatus(status);
            return attendanceRepository.save(existing);
        } else {
            Attendance attendance = new Attendance();
            attendance.setStudent(student);
            attendance.setDate(date);
            attendance.setStatus(status);
            return attendanceRepository.save(attendance);
        }
    }

    public Attendance updateAttendance(Long attendanceId, String status) {
        Attendance existing = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new RuntimeException("Attendance record not found with id: " + attendanceId));
        existing.setStatus(status);
        return attendanceRepository.save(existing);
    }

    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }

    public List<Attendance> getAttendanceByStudent(Long studentId) {
        return attendanceRepository.findByStudentId(studentId);
    }
}
