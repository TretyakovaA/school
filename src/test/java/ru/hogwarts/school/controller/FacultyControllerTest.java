package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.component.RecordMapper;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.records.FacultyRecord;
import ru.hogwarts.school.records.StudentRecord;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = FacultyController.class)
class FacultyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private FacultyService facultyService;

    //@InjectMocks
    //private FacultyController facultyController;

    @Autowired
    private ObjectMapper objectMapper;
    @SpyBean
    private RecordMapper recordMapper;

    @Test
    void getFacultyInfo() {
    }

    public Faculty resultFaculty() {
        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setName("Med");
        faculty.setColor("Blue");

        return faculty;
    }

    @Test
    void createFaculty() throws Exception {
        long id = 1L;
        String name = "Med";
        String color = "Blue";

        String jsonResult = objectMapper.writeValueAsString(recordMapper.toRecord(resultFaculty()));

        Faculty addedFaculty = new Faculty();
        addedFaculty.setId(id);
        addedFaculty.setName(name);
        addedFaculty.setColor(color);

        FacultyRecord newFaculty = new FacultyRecord();
        newFaculty.setName(name);
        newFaculty.setColor(color);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(addedFaculty);
        when(facultyRepository.findById(any(long.class))).thenReturn(Optional.of(addedFaculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty") //посылаем
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newFaculty))
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResult));
    }

    @Test
    void editFaculty() throws Exception {
        long id = 1L;
        String name = "Med";
        String color = "Blue";

        String jsonResult = objectMapper.writeValueAsString(recordMapper.toRecord(resultFaculty()));

        Faculty changedFaculty = new Faculty();
        changedFaculty.setId(id);
        changedFaculty.setName(name);
        changedFaculty.setColor(color);

        FacultyRecord changesToFaculty = new FacultyRecord();
        changesToFaculty.setName("Med");
        changesToFaculty.setColor("Blue");

        when(facultyRepository.save(any(Faculty.class))).thenReturn(changedFaculty);
        when(facultyRepository.findById(any(long.class))).thenReturn(Optional.of(changedFaculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(changesToFaculty))
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResult));

    }

    @Test
    void deleteFaculty() throws Exception {
        long id = 1L;
        String name = "Med";
        String color = "Blue";

        String jsonResult = objectMapper.writeValueAsString(recordMapper.toRecord(resultFaculty()));

        Faculty deletedFaculty = new Faculty();
        deletedFaculty.setId(id);
        deletedFaculty.setName(name);
        deletedFaculty.setColor(color);

        when(facultyRepository.findById(any(long.class))).thenReturn(Optional.of(deletedFaculty));
        doNothing().when(facultyRepository).deleteById(id);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResult));

    }

    @Test
    void findFacultiesByColor() throws Exception {
        long id = 1L;
        String name = "Med";
        String color = "Blue";

        List<FacultyRecord> facultyListRecord =  List.of(recordMapper.toRecord(resultFaculty()));
        String jsonResult = objectMapper.writeValueAsString(facultyListRecord);

        Faculty foundFaculty = new Faculty();
        foundFaculty.setId(id);
        foundFaculty.setName(name);
        foundFaculty.setColor(color);


        when(facultyRepository.findByColor(any(String.class))).thenReturn(List.of(foundFaculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/find?color="+ color)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResult));
    }

    @Test
    void findByNameOrColor() throws Exception {
        long id = 1L;
        String name = "Med";
        String color = "Blue";

        List<FacultyRecord> facultyListRecord =  List.of(recordMapper.toRecord(resultFaculty()));
        String jsonResult = objectMapper.writeValueAsString(facultyListRecord);

        Faculty foundFaculty = new Faculty();
        foundFaculty.setId(id);
        foundFaculty.setName(name);
        foundFaculty.setColor(color);


        when(facultyRepository.findByNameOrColorIgnoreCase (any(String.class),any(String.class))).thenReturn(List.of(foundFaculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/findby?nameOrColor="+ color)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResult));
    }

    @Test
    void getStudents() throws Exception {
        long id = 1L;
        String name = "Med";
        String color = "Blue";

        Faculty foundFaculty = new Faculty();
        foundFaculty.setId(id);
        foundFaculty.setName(name);
        foundFaculty.setColor(color);
        Student student1 = new Student("Настя", 5, foundFaculty);
        Student student2 = new Student("Вова", 15, foundFaculty);
        List <Student> students = List.of (student1, student2);
        foundFaculty.setStudents(students);

        List<StudentRecord> studentListRecord =  List.of(recordMapper.toRecord(student1), recordMapper.toRecord(student2));
        String jsonResult = objectMapper.writeValueAsString(studentListRecord);

        when(facultyRepository.findById(any(long.class))).thenReturn(Optional.of(foundFaculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/"+id +"/students")
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResult));

    }
}