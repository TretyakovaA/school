package ru.hogwarts.school.service;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.component.RecordMapper;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.records.FacultyRecord;
import ru.hogwarts.school.records.StudentRecord;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final RecordMapper recordMapper;
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository, RecordMapper recordMapper) {
        this.studentRepository = studentRepository;
        this.recordMapper = recordMapper;
    }



    public StudentRecord addStudent(StudentRecord studentRecord) {
Student student = recordMapper.toEntity(studentRecord);
        return recordMapper.toRecord(studentRepository.save(student));

    }

    public StudentRecord findStudent(long id) {
        Student foundStudent = studentRepository.findById(id).orElseThrow(()-> new StudentNotFoundException(id));
        System.out.println(foundStudent);
        return recordMapper.toRecord(foundStudent);
    }

    public StudentRecord editStudent(long id, StudentRecord studentRecord) {
        Student oldStudent = studentRepository.findById(id).orElseThrow(()-> new StudentNotFoundException(id));
        oldStudent.setName(studentRecord.getName());
        oldStudent.setAge(studentRecord.getAge());

        return recordMapper.toRecord(studentRepository.save(oldStudent));
    }

    public StudentRecord deleteStudent(long id) {
        Student student = studentRepository.findById(id).orElseThrow(()-> new StudentNotFoundException(id));
        studentRepository.delete(student);
        return recordMapper.toRecord(student);
    }
    public Collection<StudentRecord> findByAge(int age) {
        return studentRepository.findByAge (age).stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }

    public Collection <StudentRecord> findByAgeBetween (int min, int max){
        System.out.println("Минимальный возраст = " + min + ", Максимальный возраст =" + max);
        return studentRepository.findByAgeBetween(min, max).stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());

    }
    public FacultyRecord getFaculty (long id){
        return findStudent(id).getFaculty();
    }


    public long getCountOfStudents() {
        return studentRepository.findCountOfStudents();
    }

    public double countAverageAgeOfStudents() {
        return studentRepository.countAverageAgeOfStudents();
    }

    public Collection<Student> findFiveLastStudents() {
        return  studentRepository.findFiveLastStudents();
    }
}
