package ru.hogwarts.school.component;

import org.springframework.stereotype.Component;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.records.AvatarRecord;
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
        System.out.println("студент " + student.getId());
        studentRecord.setId(student.getId());
        System.out.println("студент рекорд " + studentRecord.getId());
        studentRecord.setName(student.getName());
        studentRecord.setAge(student.getAge());

        return studentRecord;
    }
    public AvatarRecord toRecord (Avatar avatar){
        AvatarRecord avatarRecord = new AvatarRecord();
        avatarRecord.setId(avatar.getId());
       avatarRecord.setData(avatar.getData());
       avatarRecord.setFilePath(avatar.getFilePath());
       avatarRecord.setStudent(avatar.getStudent());
       avatarRecord.setFileSize(avatar.getFileSize());
       avatarRecord.setMediaType(avatar.getMediaType());
        return avatarRecord;
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
    public Avatar toEntity (AvatarRecord avatarRecord){
        Avatar avatar = new Avatar();
        avatar.setId(avatarRecord.getId());
        avatar.setData(avatarRecord.getData());
        avatar.setFilePath(avatarRecord.getFilePath());
        avatar.setFileSize(avatarRecord.getFileSize());
        avatar.setStudent(avatarRecord.getStudent());
        avatar.setMediaType(avatarRecord.getMediaType());
        return avatar;
    }

}
