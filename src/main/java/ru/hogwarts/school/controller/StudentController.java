package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.records.FacultyRecord;
import ru.hogwarts.school.records.StudentRecord;
import ru.hogwarts.school.service.StudentService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping(path = "/student")

public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentRecord> getStudentInfo(@PathVariable long id) {
        StudentRecord studentRecord = studentService.findStudent(id);
        System.out.println(studentRecord.getId());
        return ResponseEntity.ok(studentRecord);
    }

    @PostMapping
    public StudentRecord createStudent(@RequestBody @Valid StudentRecord studentRecord) {
        return studentService.addStudent(studentRecord);
    }

    @PutMapping("/{id}")
    public StudentRecord editStudent(@PathVariable long id, @RequestBody @Valid StudentRecord studentRecord) {
        return studentService.editStudent(id, studentRecord);
    }

    @DeleteMapping("/{id}")
    public StudentRecord deleteStudent(@PathVariable long id) {
        return studentService.deleteStudent(id);
    }

    @GetMapping(path = "/byage", params = "age")
    public Collection<StudentRecord> findByAge(@RequestParam int age) {
        return studentService.findByAge(age);
    }

    @GetMapping(path = "/byagebetween", params = {"minAge, maxAge"})
    public Collection<StudentRecord> findByAgeBetween(@RequestParam int minAge, @RequestParam int maxAge) {
        System.out.println("Минимальный возраст = " + minAge + ", Максимальный возраст =" + maxAge);
        return studentService.findByAgeBetween(minAge, maxAge);
    }

    @GetMapping("/{id}/faculty")
    public FacultyRecord getFaculty(@PathVariable Long id) {
        return studentService.getFaculty(id);
    }

}
