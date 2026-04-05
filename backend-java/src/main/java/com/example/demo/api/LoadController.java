package com.example.demo.api;

import com.example.demo.api.json.LoadAuditoriumResponse;
import com.example.demo.api.json.LoadEducatorResponse;
import com.example.demo.api.json.LoadStudentGroupResponse;
import com.example.demo.service.ScheduleLoadService;
import com.example.demo.service.repositoryservice.AuditoriumService;
import com.example.demo.service.repositoryservice.EducatorService;
import com.example.demo.service.repositoryservice.StudentGroupService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("https://localhost:3000")
@RequestMapping("api/v1/load")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class LoadController {
    AuditoriumService auditoriumService;
    EducatorService educatorService;
    StudentGroupService studentGroupService;
    ScheduleLoadService scheduleLoadService;

    @GetMapping(value = "auditorium")
    public List<String> getAllAuditorium() {
        return auditoriumService.getUniqueAuditoriumNames();
    }

    @GetMapping(value = "auditorium/{name}")
    public List<LoadAuditoriumResponse> getAuditoriumsLoad(@PathVariable("name") String name) {
        List<LoadAuditoriumResponse> response = scheduleLoadService.getAuditorium(name);
        return response;
    }

    @GetMapping(value = "educator")
    public List<String> getAllEducator() {
        return educatorService.getUniqueEducatorFullNames();
    }

    @GetMapping(value = "educator/{name}")
    public List<LoadEducatorResponse> getEducatorLoad(@PathVariable("name") String fullName) {
        List<LoadEducatorResponse> response = scheduleLoadService.getEducator(fullName);
        return response;
    }

    @GetMapping(value = "group")
    public List<String> getAllStudentGroups() {
        return studentGroupService.getAllStudentGroupName();
    }

    @GetMapping(value = "group/{name}")
    public List<LoadStudentGroupResponse> getStudentGroupLoad(@PathVariable("name") String name) {
        List<LoadStudentGroupResponse> result = scheduleLoadService.getStudentGroup(name);
        return result;
    }
}

