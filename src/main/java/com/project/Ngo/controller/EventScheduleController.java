package com.project.Ngo.controller;

import com.project.Ngo.model.EventSchedule;
import com.project.Ngo.service.EventScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/EventSchedule")
public class EventScheduleController {
    @Autowired
    private final EventScheduleService eventScheduleService;

    public EventScheduleController(EventScheduleService eventScheduleService){
        this.eventScheduleService = eventScheduleService;
    }

    @GetMapping
    public List<EventSchedule> getAllEventSchedules() {
        return eventScheduleService.getAlleventSchedule();
    }

    @PostMapping
    public EventSchedule saveEventSchedule(@RequestBody EventSchedule eventSchedule) {
        return eventScheduleService.saveEventSchedule(eventSchedule);
    }
}
