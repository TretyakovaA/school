package ru.hogwarts.school.controller;

import java.util.List;

import org.apache.coyote.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.component.RecordMapper;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.records.FacultyRecord;
import ru.hogwarts.school.records.StudentRecord;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private FacultyRepository facultyRepository;
    @Autowired
    private RecordMapper recordMapper;

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(studentController).isNotNull();

    }

    @Test
    void getStudentInfo() {
        Faculty faculty = new Faculty("Имя факультета", "Цвет факультета");
        faculty = facultyRepository.save(faculty);
        Student student = new Student("Имя", 18, faculty);
        student = studentRepository.save(student);
        System.out.println(student);
        Long id = student.getId();

        StudentRecord studentRecord = recordMapper.toRecord(student);
        System.out.println(studentRecord.getName() + " " + studentRecord.getAge());

        StudentRecord result = restTemplate.getForObject("http://localhost:" + port + "/student/{id}", StudentRecord.class, id);
        System.out.println(result.getName() + " " + result.getAge());
        Assertions.
                assertThat(result.getName()).isEqualTo("Имя");
        Assertions.
                assertThat(result.getAge()).isEqualTo(18);

        studentRepository.delete(student);
        facultyRepository.delete(faculty);
    }

    @Test
    void createStudent() {
        Faculty faculty = new Faculty("Имя факультета", "Цвет факультета");
        faculty = facultyRepository.save(faculty);

        Student student = new Student("Имя", 18, faculty);
        StudentRecord studentRecord = recordMapper.toRecord(student);

        ResponseEntity<StudentRecord> response = restTemplate.postForEntity("http://localhost:" + port + "/student", studentRecord, StudentRecord.class);
        Assertions.
                assertThat(response.getBody().getName()).isEqualTo("Имя");
        Assertions.
                assertThat(response.getBody().getAge()).isEqualTo(18);

        studentRepository.delete(studentRepository.findById(response.getBody().getId()).orElseThrow());
        facultyRepository.delete(faculty);
    }

    @Test
    void editStudent() {
        Faculty faculty = new Faculty("Имя факультета", "Цвет факультета");
        faculty = facultyRepository.save(faculty);
        Student student = new Student("Имя", 18, faculty);
        student = studentRepository.save(student);
        System.out.println(student);
        Long id = student.getId();

        StudentRecord studentRecord = recordMapper.toRecord(student);
        System.out.println(studentRecord.getName() + " " + studentRecord.getAge());

        StudentRecord result = restTemplate.getForObject("http://localhost:" + port + "/student/{id}", StudentRecord.class, id);
        System.out.println(result.getName() + " " + result.getAge());
        Assertions.
                assertThat(result.getName()).isEqualTo("Имя");
        Assertions.
                assertThat(result.getAge()).isEqualTo(18);

        studentRepository.delete(student);
        facultyRepository.delete(faculty);
    }

    @Test
    void deleteStudent() {
        Faculty faculty = new Faculty("Имя факультета", "Цвет факультета");
        faculty = facultyRepository.save(faculty);
        Student student = new Student("Имя", 18, faculty);
        student = studentRepository.save(student);
        System.out.println(student);
        long id = student.getId();

        StudentRecord studentRecord = recordMapper.toRecord(student);
        System.out.println(studentRecord.getName() + " " + studentRecord.getAge());

        ResponseEntity<StudentRecord> response = restTemplate.exchange("http://localhost:" + port + "/student/{id}", HttpMethod.DELETE,
                null, StudentRecord.class, id);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        facultyRepository.delete(faculty);
    }

    @Test
    void findByAge() {
        Faculty faculty = new Faculty("Имя факультета", "Цвет факультета");
        faculty = facultyRepository.save(faculty);
        Student student = new Student("Ира", 5, faculty);
        Student student1 = new Student("Настя", 5, faculty);
        student = studentRepository.save(student);
        student1 = studentRepository.save(student1);
        System.out.println(student);
        long id = student.getId();

        StudentRecord studentRecord = recordMapper.toRecord(student);
        System.out.println(studentRecord.getName() + " " + studentRecord.getAge());

        ResponseEntity<List<StudentRecord>> response = restTemplate.exchange("http://localhost:" + port + "/student/byage?age={age}",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<StudentRecord>>() {
                }, 5);

        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().size()).isEqualTo(2);

        Assertions.
                assertThat(response.getBody().get(0).getName()).isEqualTo("Ира");
        Assertions.
                assertThat(response.getBody().get(0).getAge()).isEqualTo(5);

        Assertions.
                assertThat(response.getBody().get(1).getName()).isEqualTo("Настя");
        Assertions.
                assertThat(response.getBody().get(1).getAge()).isEqualTo(5);

        studentRepository.delete(student);
        studentRepository.delete(student1);
        facultyRepository.delete(faculty);
    }

    @Test
    void findByAgeBetween() {
        Faculty faculty = new Faculty("Имя факультета", "Цвет факультета");
        faculty = facultyRepository.save(faculty);
        Student student = new Student("Ира", 5, faculty);
        Student student1 = new Student("Настя", 5, faculty);
        Student student2 = new Student("Вова", 15, faculty);
        student = studentRepository.save(student);
        student1 = studentRepository.save(student1);
        student2 = studentRepository.save(student2);


        ResponseEntity<List<StudentRecord>> response = restTemplate.exchange("http://localhost:" + port
                        + "/student/byagebetween?minAge={minAge}&maxAge={maxAge}",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<StudentRecord>>() {
                }, 0, 10);

        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().size()).isEqualTo(2);

        Assertions.
                assertThat(response.getBody().get(0).getName()).isEqualTo("Ира");
        Assertions.
                assertThat(response.getBody().get(0).getAge()).isEqualTo(5);

        Assertions.
                assertThat(response.getBody().get(1).getName()).isEqualTo("Настя");
        Assertions.
                assertThat(response.getBody().get(1).getAge()).isEqualTo(5);

        studentRepository.delete(student);
        studentRepository.delete(student1);
        studentRepository.delete(student2);
        facultyRepository.delete(faculty);
    }

    @Test
    void getFaculty() {
        Faculty faculty = new Faculty("Имя факультета", "Цвет факультета");
        faculty = facultyRepository.save(faculty);
        Student student = new Student("Имя", 18, faculty);
        student = studentRepository.save(student);
        System.out.println(student);
        Long id = student.getId();


        FacultyRecord result = restTemplate.getForObject("http://localhost:" + port + "/student/{id}/faculty", FacultyRecord.class, id);
        ///System.out.println(result.getName() + " " + result.getColor());
        Assertions.
                assertThat(result.getName()).isEqualTo("Имя факультета");
        Assertions.
                assertThat(result.getColor()).isEqualTo("Цвет факультета");

        studentRepository.delete(student);
        facultyRepository.delete(faculty);
    }
}