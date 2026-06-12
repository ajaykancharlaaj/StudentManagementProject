package com.sms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sms.entity.Faculty;
import com.sms.repository.FacultyRepository;
import com.sms.repository.StudentRepository;
import com.sms.repository.AttendanceRepository;
import com.sms.repository.MarksRepository;
import java.util.HashMap;
import java.util.Map;

@Service
public class FacultyService {

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private MarksRepository marksRepository;

    public Faculty registerFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty login(String email, String password) {
        return facultyRepository.findByEmailAndPassword(email, password);
    }

    public Faculty getProfile(Long id) {
        return facultyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Faculty not found with id: " + id));
    }

    public Faculty updateProfile(Long id, Faculty updatedFaculty) {
        Faculty existing = getProfile(id);
        existing.setName(updatedFaculty.getName());
        existing.setEmail(updatedFaculty.getEmail());
        if (updatedFaculty.getPassword() != null && !updatedFaculty.getPassword().isEmpty()) {
            existing.setPassword(updatedFaculty.getPassword());
        }
        existing.setDepartment(updatedFaculty.getDepartment());
        return facultyRepository.save(existing);
    }

    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalStudents", studentRepository.count());
        stats.put("totalFaculty", facultyRepository.count());
        stats.put("totalAttendanceRecords", attendanceRepository.count());
        stats.put("totalMarksRecords", marksRepository.count());
        return stats;
    }
}
