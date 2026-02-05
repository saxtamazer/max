package com.example.demo.api;

import com.example.demo.service.ScheduleClientService;
import com.example.demo.service.ScheduleManager;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/parser")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@CrossOrigin(origins = "http://localhost:8082")
public class ParserController {
    ScheduleClientService scheduleClientService;
    ScheduleManager scheduleManager;

    @GetMapping("test")
    public void test() {
        scheduleClientService.sendFile();
    }

    @PostMapping(value = "read")
    public Mono<ResponseEntity<String>> read(@RequestPart("file") Flux<FilePart> files) {
        return files
                .flatMap(scheduleClientService::saveFile)
                .then(Mono.fromRunnable(scheduleManager::writeSchedule))
                .then(Mono.just(ResponseEntity.ok().body("Files successfully saved")));
    }
}
