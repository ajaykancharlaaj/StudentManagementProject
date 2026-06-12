package com.sms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sms.entity.Marks;
import com.sms.entity.Student;
import com.sms.repository.MarksRepository;
import com.sms.repository.StudentRepository;
import java.util.List;

@Service
public class MarksService {

    @Autowired
    private MarksRepository marksRepository;

    @Autowired
    private StudentRepository studentRepository;

    public Marks addMarks(Long studentId, String subject, Double marksObtained, Double maxMarks) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));
        Marks marks = new Marks();
        marks.setStudent(student);
        marks.setSubject(subject);
        marks.setMarksObtained(marksObtained);
        marks.setMaxMarks(maxMarks);
        return marksRepository.save(marks);
    }

    public Marks updateMarks(Long marksId, Double marksObtained, Double maxMarks) {
        Marks existing = marksRepository.findById(marksId)
                .orElseThrow(() -> new RuntimeException("Marks record not found with id: " + marksId));
        existing.setMarksObtained(marksObtained);
        existing.setMaxMarks(maxMarks);
        return marksRepository.save(existing);
    }

    public List<Marks> getAllMarks() {
        return marksRepository.findAll();
    }

    public List<Marks> getMarksByStudent(Long studentId) {
        return marksRepository.findByStudentId(studentId);
    }
}
