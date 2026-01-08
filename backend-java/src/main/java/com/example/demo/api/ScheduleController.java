package com.example.demo.api;

import com.example.demo.service.ScheduleManager;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("api/v1/schedule")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleManager scheduleManager;

    @GetMapping("/view")
    public String view() {
        ObjectMapper om = new ObjectMapper();

        return om.writeValueAsString(scheduleManager.getScheduleByThisWeek());
    }
}
