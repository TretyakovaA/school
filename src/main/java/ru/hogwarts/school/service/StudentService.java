package ru.hogwarts.school.service;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }



    public Student addStudent(Student student) {

        return studentRepository.save(student);
        ;
    }

    public Student findStudent(long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Student editStudent(long id, Student student) {
        if (studentRepository.existById(id)) {
            return null;
        }
        return studentRepository.save(student);
    }

    public Faculty deleteFaculty(long id) {
        return studentRepository.deleteById(id);
    }
    public Collection<Student> findByAge(int age) {
        return studentRepository.findAllByAge (age);
    }



}
