package ru.hogwarts.school.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.records.StudentRecord;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findByAge(int age);
    Collection <Student> findByAgeBetween (int min, int max);
}
