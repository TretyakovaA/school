package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.component.RecordMapper;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.records.FacultyRecord;
import ru.hogwarts.school.records.StudentRecord;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service

public class FacultyService {
    Logger logger = LoggerFactory.getLogger(FacultyService.class);
    private final FacultyRepository facultyRepository;
    private final RecordMapper recordMapper;

    public FacultyService(FacultyRepository facultyRepository, RecordMapper recordMapper) {
        this.facultyRepository = facultyRepository;
        this.recordMapper = recordMapper;
    }

    public FacultyRecord addFaculty(FacultyRecord facultyRecord) {
Faculty faculty = recordMapper.toEntity(facultyRecord);
logger.info("Факультет добавлен: " + faculty.getName()+ ", "+ faculty.getColor());
        return recordMapper.toRecord(facultyRepository.save(faculty));
    }

    public FacultyRecord findFaculty(long id) {
logger.info("Факультет с id "+ id+" найден");
        return recordMapper.toRecord(facultyRepository.findById(id).orElseThrow(()-> {
            logger.info("Факультет с id "+ id+" не найден");
            new FacultyNotFoundException(id);
            return null;
        }));
    }

    public FacultyRecord editFaculty(long id, FacultyRecord facultyRecord) {
        Faculty oldFaculty = facultyRepository.findById(id).orElseThrow(()-> new FacultyNotFoundException(id));
        oldFaculty.setName(facultyRecord.getName());
        oldFaculty.setColor(facultyRecord.getColor());
        logger.info("Факультет с id "+ id+" изменен");
        return recordMapper.toRecord(facultyRepository.save(oldFaculty));
    }

    public FacultyRecord deleteFaculty(long id) {
        Faculty faculty = facultyRepository.findById(id).orElseThrow(()-> new FacultyNotFoundException(id));
        facultyRepository.delete(faculty);
        logger.info("Факультет с id "+ id+" удален");
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

    public String getFacultyLogestName() {
        return (facultyRepository
                .findAll()
                .stream()
                .max(Comparator.comparingInt(f-> f.getName().length()))
                .orElseThrow()).getName();

    }
}
