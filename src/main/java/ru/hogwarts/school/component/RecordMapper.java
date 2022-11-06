package ru.hogwarts.school.component;

import org.springframework.stereotype.Component;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.records.FacultyRecord;
import ru.hogwarts.school.records.StudentRecord;

@Component
public class RecordMapper {
    public FacultyRecord toRecord (Faculty faculty){
        FacultyRecord facultyRecord = new FacultyRecord();
        facultyRecord.setId(faculty.getId());
        facultyRecord.setName(faculty.getName());
        facultyRecord.setColor(faculty.getColor());
        return facultyRecord;
    }
    public StudentRecord toRecord (Student student){
        StudentRecord studentRecord = new StudentRecord();
        studentRecord.setId(student.getId());
        studentRecord.setName(student.getName());
        studentRecord.setAge(student.getAge());

        return studentRecord;
    }
    public Faculty toEntity (FacultyRecord facultyRecord){
        Faculty faculty = new Faculty();
        faculty.setId(facultyRecord.getId());
        faculty.setName(facultyRecord.getName());
        faculty.setColor(facultyRecord.getColor());
        return faculty;
    }
    public Student toEntity (StudentRecord studentRecord){
        Student student = new Student();
        student.setId(studentRecord.getId());
        student.setName(studentRecord.getName());
        student.setAge(studentRecord.getAge());
        return student;
    }

}
