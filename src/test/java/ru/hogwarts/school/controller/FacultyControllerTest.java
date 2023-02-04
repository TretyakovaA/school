package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
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
import ru.hogwarts.school.records.FacultyRecord;
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
    @Autowired
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
    void editFaculty() {
    }

    @Test
    void deleteFaculty() {
    }

    @Test
    void findFacultiesByColor() {
    }

    @Test
    void findByNameOrColor() {
    }

    @Test
    void getStudent() {
    }
}