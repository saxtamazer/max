package com.example.demo.api;

import com.example.demo.service.ScheduleWebClientService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/parser")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@CrossOrigin(origins = "http://localhost:8082")
public class ParserController {
    ScheduleWebClientService scheduleWebClientService;

    @GetMapping("test")
    public void test() {
        scheduleWebClientService.sendFile();
    }

    @PostMapping(value = "read", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void read(@RequestPart("file") FilePart file) {
        System.out.println(2);
    }
}
