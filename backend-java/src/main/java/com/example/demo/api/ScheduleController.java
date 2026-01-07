package com.example.demo.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/schedule")
public class ScheduleController {
    @GetMapping("/view")
    public String view() {
        return null;
    }
}
