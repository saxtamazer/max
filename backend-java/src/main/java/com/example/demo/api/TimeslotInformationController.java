package com.example.demo.api;

import com.example.demo.api.json.TimeResponse;
import com.example.demo.service.repositoryservice.TimeslotService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("https://localhost:3000")
@RequestMapping("api/v1/timeslot")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class TimeslotInformationController {
    TimeslotService timeslotService;

    @GetMapping(value = "time")
    public List<TimeResponse> getTimeInfo() {
        return timeslotService.getPairTimeInfo();
    }
}
