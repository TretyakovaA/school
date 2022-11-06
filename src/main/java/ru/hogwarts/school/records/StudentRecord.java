package ru.hogwarts.school.records;

import javax.validation.constraints.NotBlank;

public class StudentRecord {
    private Long id;
    @NotBlank (message = "Имя студента должно быть заполнено")
    private String name;
    @NotBlank (message = "Возраст должен быть заполнен")
    private Integer age;
    private FacultyRecord faculty;

    public StudentRecord() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public FacultyRecord getFaculty() {
        return faculty;
    }

    public void setFaculty(FacultyRecord faculty) {
        this.faculty = faculty;
    }
}
