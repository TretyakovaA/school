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
        Student foundStudent = studentRepository.findById(id).orElseThrow(() -> {
            logger.info("Студент с id " + id + " не найден");
            new StudentNotFoundException(id);
            return null;
        });
        logger.info("Студент с id " + id + " найден");
        System.out.println(foundStudent);
        return recordMapper.toRecord(foundStudent);
    }

    public StudentRecord editStudent(long id, StudentRecord studentRecord) {
        Student oldStudent = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
        oldStudent.setName(studentRecord.getName());
        oldStudent.setAge(studentRecord.getAge());
        logger.info("Студент с id " + id + " изменен");
        return recordMapper.toRecord(studentRepository.save(oldStudent));
    }

    public StudentRecord deleteStudent(long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
        studentRepository.delete(student);
        logger.info("Студент с id " + id + " удален");
        return recordMapper.toRecord(student);
    }

    public Collection<StudentRecord> findByAge(int age) {
        return studentRepository.findByAge(age).stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }

    public Collection<StudentRecord> findByAgeBetween(int min, int max) {
        System.out.println("Минимальный возраст = " + min + ", Максимальный возраст =" + max);
        return studentRepository.findByAgeBetween(min, max).stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());

    }

    public FacultyRecord getFaculty(long id) {
        return findStudent(id).getFaculty();
    }


    public long getCountOfStudents() {
        return studentRepository.findCountOfStudents();
    }

    public double countAverageAgeOfStudents() {
        return studentRepository.countAverageAgeOfStudents();
    }

    public Collection<Student> findFiveLastStudents() {
        return studentRepository.findFiveLastStudents();
    }
/*  Добавить эндпоинт для получения всех имен всех студентов, чье имя начинается с буквы А.
//    В ответе должен находиться отсортированный в алфавитном порядке список с именами в верхнем регистре.
//    Для получения всех студентов из базы использовать метод репозитория - findAll().*/

    public Collection<StudentRecord> getNamesOfAllStudents() {
        return studentRepository
                .findAll()
                .stream()
                .filter(st -> st.getName().startsWith("А") || st.getName().startsWith("а"))
                .sorted((st1, st2) -> st1.getName().compareToIgnoreCase(st2.getName()))
                //.map(st-> st.getName())
                //.forEach(st -> st.toUpperCase())

                .map(st -> {
                    st.setName(st.getName().toUpperCase());
                    return st;
                })
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

    public void printStudentsNames() throws InterruptedException {
        List<Student> allStudents = studentRepository.findAll();
        System.out.println(allStudents.get(0).getName()+ 0);
        Thread.sleep(500);
        System.out.println(allStudents.get(1).getName()+ 1);

        new Thread(() -> {
            System.out.println(allStudents.get(2).getName()+ 2);
            try {
               Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(allStudents.get(3).getName()+ 3);
        }).start();
        new Thread(() -> {
            System.out.println(allStudents.get(4).getName()+ 4);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(allStudents.get(5).getName()+ 5);
        }).start();
    }

    public void  printName(String name, int number) {
        System.out.println(name+ number);
    }

    public void printStudentsNames2() throws InterruptedException {
        List<Student> allStudents = studentRepository.findAll();
        printName(allStudents.get(0).getName(), 0);
        Thread.sleep(500);
        printName(allStudents.get(1).getName(), 1);

         new Thread(() -> {
             synchronized (allStudents) {
                 printName(allStudents.get(2).getName(), 2);
                 try {
                     Thread.sleep(500);
                 } catch (InterruptedException e) {
                     throw new RuntimeException(e);
                 }
                 printName(allStudents.get(3).getName(), 3);
             }
        }).start();
        new Thread(() -> {
            synchronized (allStudents) {
                printName(allStudents.get(4).getName(), 4);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                printName(allStudents.get(5).getName(), 5);
            }
        }).start();
    }
}
