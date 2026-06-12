package com.sms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sms.entity.Student;
import com.sms.entity.Attendance;
import com.sms.entity.Marks;
import com.sms.repository.StudentRepository;
import com.sms.repository.AttendanceRepository;
import com.sms.repository.MarksRepository;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private MarksRepository marksRepository;

    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
    }

    public Student getStudentByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

    public Student updateStudent(Long id, Student updatedStudent) {
        Student existing = getStudentById(id);
        existing.setName(updatedStudent.getName());
        existing.setEmail(updatedStudent.getEmail());
        if (updatedStudent.getPassword() != null && !updatedStudent.getPassword().isEmpty()) {
            existing.setPassword(updatedStudent.getPassword());
        }
        existing.setRollNumber(updatedStudent.getRollNumber());
        existing.setClassName(updatedStudent.getClassName());
        return studentRepository.save(existing);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public Student login(String email, String password) {
        return studentRepository.findByEmailAndPassword(email, password);
    }

    public Map<String, Object> getStudentDashboardStats(Long studentId) {
        Map<String, Object> stats = new HashMap<>();
        List<Attendance> attendanceList = attendanceRepository.findByStudentId(studentId);
        List<Marks> marksList = marksRepository.findByStudentId(studentId);

        long totalAttendance = attendanceList.size();
        long presentCount = attendanceList.stream()
                .filter(a -> "Present".equalsIgnoreCase(a.getStatus()))
                .count();
        double attendancePercentage = totalAttendance > 0 ? (double) presentCount / totalAttendance * 100.0 : 0.0;

        double totalMarksObtained = marksList.stream().mapToDouble(Marks::getMarksObtained).sum();
        double totalMaxMarks = marksList.stream().mapToDouble(Marks::getMaxMarks).sum();
        double marksPercentage = totalMaxMarks > 0 ? (totalMarksObtained / totalMaxMarks) * 100.0 : 0.0;

        stats.put("attendancePercentage", attendancePercentage);
        stats.put("totalAttendanceRecords", totalAttendance);
        stats.put("presentRecords", presentCount);
        stats.put("marksPercentage", marksPercentage);
        stats.put("totalSubjects", marksList.size());

        return stats;
    }
}
