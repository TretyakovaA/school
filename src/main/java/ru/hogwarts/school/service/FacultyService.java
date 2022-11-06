package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.component.RecordMapper;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.records.FacultyRecord;
import ru.hogwarts.school.records.StudentRecord;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service

public class FacultyService {
    private final FacultyRepository facultyRepository;
    private final RecordMapper recordMapper;

    public FacultyService(FacultyRepository facultyRepository, RecordMapper recordMapper) {
        this.facultyRepository = facultyRepository;
        this.recordMapper = recordMapper;
    }

    public FacultyRecord addFaculty(FacultyRecord facultyRecord) {
Faculty faculty = recordMapper.toEntity(facultyRecord);
        return recordMapper.toRecord(facultyRepository.save(faculty));
    }

    public FacultyRecord findFaculty(long id) {

        return recordMapper.toRecord(facultyRepository.findById(id).orElseThrow(()-> new FacultyNotFoundException(id)));
    }

    public FacultyRecord editFaculty(long id, FacultyRecord facultyRecord) {
        Faculty oldFaculty = facultyRepository.findById(id).orElseThrow(()-> new FacultyNotFoundException(id));
        oldFaculty.setName(facultyRecord.getName());
        oldFaculty.setColor(facultyRecord.getColor());

        return recordMapper.toRecord(facultyRepository.save(oldFaculty));
    }

    public FacultyRecord deleteFaculty(long id) {
        Faculty faculty = facultyRepository.findById(id).orElseThrow(()-> new FacultyNotFoundException(id));
        facultyRepository.delete(faculty);
        return recordMapper.toRecord(faculty);
    }

    public Collection<FacultyRecord> findByColor(String color) {
        return facultyRepository.findByColor (color).stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }
    public Collection<FacultyRecord> findByNameOrColor(String nameOrcolor) {
        return facultyRepository.findByNameOrColorIgnoreCase (nameOrcolor, nameOrcolor).stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }
    public Collection <StudentRecord> getStudent (long id){
        return facultyRepository.findById(id)
                .map(Faculty::getStudents)
                .map(students ->students.stream()
                        .map(recordMapper::toRecord)
                        .collect(Collectors.toList()))
                .orElseThrow(()-> new FacultyNotFoundException(id));
    }
}
