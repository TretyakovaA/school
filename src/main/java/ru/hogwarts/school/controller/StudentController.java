package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
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
    @GetMapping ("/{id}")
    public ResponseEntity <Student> getStudentInfo (@PathVariable long id){
        Student student = studentService.findStudent(id);
        if (student == null){
            return ResponseEntity.notFound().build();
        } return ResponseEntity.ok(student);
    }
    @PostMapping
    public  Student createStudent (@RequestBody @Valid Student student){
        return studentService.addStudent(student);
    }
    @PutMapping ("/{id}")
    public ResponseEntity <Student> editStudent (@RequestBody @Valid Student student, @PathVariable long id){
        Student foundStudent = studentService.editStudent(id, student);
        if (foundStudent == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } return ResponseEntity.ok(student);
    }
    @DeleteMapping ("/{id}")
    public ResponseEntity <Void> deleteStudent (@PathVariable long id){
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping
    public ResponseEntity <Collection<Student>> findByAge (@RequestParam(required = false) int age){
        if (age>0){
            return ResponseEntity.ok(studentService.findByAge(age));
        } return ResponseEntity.ok (Collections.emptyList());

    }

}
