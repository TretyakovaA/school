package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping(path = "/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getFacultyInfo(@PathVariable long id) {
        Faculty faculty = facultyService.findFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody @Valid Faculty faculty) {
        return facultyService.addFaculty(faculty);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Faculty> editFaculty(@PathVariable Faculty faculty, @RequestBody @Valid long id) {
        Faculty foundFaculty = facultyService.editFaculty(id, faculty);
        if (foundFaculty == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(faculty);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFaculty(@PathVariable long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }


    @GetMapping
    public ResponseEntity<Collection<Faculty>> findFacultiesByColor (@RequestParam(required = false) String color) {
        if (color != null && !color.isBlank()) {
            return ResponseEntity.ok(facultyService.findByColor(color));
        }
        return ResponseEntity.ok(Collections.EMPTY_LIST);
    }
}
