package ru.hogwarts.school.service;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.component.RecordMapper;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.records.FacultyRecord;
import ru.hogwarts.school.records.StudentRecord;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.stream.Collectors;

@Service
public class StudentService {
    Logger logger = LoggerFactory.getLogger(StudentService.class);
    private final RecordMapper recordMapper;
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository, RecordMapper recordMapper) {
        this.studentRepository = studentRepository;
        this.recordMapper = recordMapper;
    }



    public StudentRecord addStudent(StudentRecord studentRecord) {
Student student = recordMapper.toEntity(studentRecord);
        logger.info("Студент добавлен: " + student.getName());
        return recordMapper.toRecord(studentRepository.save(student));

    }

    public StudentRecord findStudent(long id) {
        Student foundStudent = studentRepository.findById(id).orElseThrow(()-> {
            logger.info("Студент с id "+ id+" не найден");
                new StudentNotFoundException(id);
        return null;
        });
        logger.info("Студент с id "+ id+" найден");
        System.out.println(foundStudent);
        return recordMapper.toRecord(foundStudent);
    }

    public StudentRecord editStudent(long id, StudentRecord studentRecord) {
        Student oldStudent = studentRepository.findById(id).orElseThrow(()-> new StudentNotFoundException(id));
        oldStudent.setName(studentRecord.getName());
        oldStudent.setAge(studentRecord.getAge());
        logger.info("Студент с id "+ id+" изменен");
        return recordMapper.toRecord(studentRepository.save(oldStudent));
    }

    public StudentRecord deleteStudent(long id) {
        Student student = studentRepository.findById(id).orElseThrow(()-> new StudentNotFoundException(id));
        studentRepository.delete(student);
        logger.info("Студент с id "+ id+" удален");
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
/*  Добавить эндпоинт для получения всех имен всех студентов, чье имя начинается с буквы А.
//    В ответе должен находиться отсортированный в алфавитном порядке список с именами в верхнем регистре.
//    Для получения всех студентов из базы использовать метод репозитория - findAll().*/

    public Collection<StudentRecord> getNamesOfAllStudents() {
        return studentRepository
                .findAll()
                .stream()
                .filter(st -> st.getName().startsWith("А") || st.getName().startsWith("а"))
                .sorted((st1, st2)->st1.getName().compareToIgnoreCase(st2.getName()))
                //.map(st-> st.getName())
                //.forEach(st -> st.toUpperCase())

                .map(st -> {st.setName(st.getName().toUpperCase());
                    return st;})
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());

    }


    public OptionalDouble getAverageAgeOfStudents() {
        return studentRepository
                .findAll()
                .stream()
                .mapToInt(st -> st.getAge())
                .average();
    }
}
