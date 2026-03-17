package com.example.demo.api;

import com.example.demo.service.ScheduleManager;
import com.example.demo.service.repositoryservice.StudentGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@RestController
@RequestMapping("api/v1/schedule")
@RequiredArgsConstructor
@CrossOrigin(origins = "https://localhost:3000")
public class ScheduleController {
    private final ScheduleManager scheduleManager;
    private final StudentGroupService studentGroupService;

    @GetMapping("/group")
    public List<String> getGroups() {
        return studentGroupService.getGroups();
    }

    @GetMapping("/view")
    public String view() {
        ObjectMapper om = new ObjectMapper();

        return om.writeValueAsString(scheduleManager.getScheduleByThisWeek());
    }

    @GetMapping("/view/{group}")
    public String view(@PathVariable("group") String groupName) {
        ObjectMapper om = new ObjectMapper();

        return om.writeValueAsString(scheduleManager.getScheduleByGroup(groupName));
    }
}
