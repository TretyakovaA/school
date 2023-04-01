package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.records.FacultyRecord;
import ru.hogwarts.school.records.StudentRecord;
import ru.hogwarts.school.service.StudentService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.OptionalDouble;

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

    @GetMapping(path = "/byagebetween", params = {"minAge", "maxAge"})
    public Collection<StudentRecord> findByAgeBetween(@RequestParam int minAge, @RequestParam int maxAge) {
        System.out.println("Минимальный возраст = " + minAge + ", Максимальный возраст =" + maxAge);
        return studentService.findByAgeBetween(minAge, maxAge);
    }

    @GetMapping("/{id}/faculty")
    public FacultyRecord getFaculty(@PathVariable long id) {
        return studentService.getFaculty(id);
    }

    @GetMapping ("/count")
    public long getCountOfStudents (){
        return studentService.getCountOfStudents();
    }
    @GetMapping ("/averageage")
    public double countAverageAgeOfStudents(){
        return studentService.countAverageAgeOfStudents();
    }
    @GetMapping ("/lastfivestudents")
    public  Collection <Student> findFiveLastStudents (){
        return studentService.findFiveLastStudents();
    }

/*  Добавить эндпоинт для получения всех имен всех студентов, чье имя начинается с буквы А.
//    В ответе должен находиться отсортированный в алфавитном порядке список с именами в верхнем регистре.
//    Для получения всех студентов из базы использовать метод репозитория - findAll().*/

    @GetMapping ("/namesstartwitha")
    public  Collection <StudentRecord> getNamesOfAllStudents (){
        return studentService.getNamesOfAllStudents();
    }
    @GetMapping ("/averageage2")
    public OptionalDouble getAverageAgeOfStudents() {
        return studentService.getAverageAgeOfStudents();
    }
    @GetMapping ("/printnames")
    public void printStudentsNames() throws InterruptedException {
         studentService.printStudentsNames();
    }
    @GetMapping ("/printnames2")
    public void printStudentsNames2() throws InterruptedException {
        studentService.printStudentsNames2();
    }


}
