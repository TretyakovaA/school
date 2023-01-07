package ru.hogwarts.school.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.component.RecordMapper;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.records.StudentRecord;
import ru.hogwarts.school.repository.StudentRepository;

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
private RecordMapper recordMapper;
    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(studentController).isNotNull();

    }
    @Test
    void getStudentInfo() {
        Student student = new Student("Имя", 18);
        student = studentRepository.save(student);
        System.out.println(student);
        Long id = student.getId();

        StudentRecord studentRecord = recordMapper.toRecord(student);
        System.out.println(studentRecord.getName() + " "+ studentRecord.getAge());

        StudentRecord result = restTemplate.getForObject("http://localhost:" + port + "/student/{id}", StudentRecord.class, id);
        System.out.println(result.getName()+ " "+ result.getAge());
        Assertions.
        assertThat(result.getName()).isEqualTo("Имя");
        Assertions.
                assertThat(result.getAge()).isEqualTo(18);


    }

    @Test
    void createStudent() {
    }

    @Test
    void editStudent() {
    }

    @Test
    void deleteStudent() {
    }

    @Test
    void findByAge() {
    }

    @Test
    void findByAgeBetween() {
    }

    @Test
    void getFaculty() {
    }
}