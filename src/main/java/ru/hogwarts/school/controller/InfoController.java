package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.records.FacultyRecord;
import ru.hogwarts.school.service.InfoService;

@RestController
public class InfoController {
    public InfoController(InfoService infoService) {
        this.infoService = infoService;
    }

    private final InfoService infoService;

    @GetMapping("/getPort")
    public int getPort () {
        return infoService.getPort ();
    }

    @GetMapping("/parallel")
    public int getParallelSum () {
        return infoService.getParallelSum ();
    }
}
