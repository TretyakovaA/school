package ru.hogwarts.school.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.records.StudentRecord;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findByAge(int age);
    Collection <Student> findByAgeBetween (int min, int max);

    @Query (value = "SELECT COUNT(*) FROM students", nativeQuery = true)
    long findCountOfStudents ();
    @Query (value = "SELECT AVG(age) from students", nativeQuery = true)
    double countAverageAgeOfStudents ();
    @Query (value = "SELECT * FROM students ORDER BY id DESC limit 5", nativeQuery = true)
    Collection <Student> findFiveLastStudents ();
}

