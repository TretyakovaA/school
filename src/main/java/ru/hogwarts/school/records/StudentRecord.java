package ru.hogwarts.school.records;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class StudentRecord {
    private long id;
    @NotBlank (message = "Имя студента должно быть заполнено")
    private String name;
    @NotNull(message = "Возраст должен быть заполнен")
    private int age;
    private FacultyRecord faculty;

    public StudentRecord() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public FacultyRecord getFaculty() {
        return faculty;
    }

    public void setFaculty(FacultyRecord faculty) {
        this.faculty = faculty;
    }
}
