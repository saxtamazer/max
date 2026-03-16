package com.example.demo.api;

import com.example.demo.service.ScheduleClientService;
import com.example.demo.service.ScheduleManager;
import com.example.demo.utils.DataGenerator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/parser")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@CrossOrigin(origins = {"http://localhost:8082", "https://localhost:3000"})
public class ParserController {
    ScheduleClientService scheduleClientService;
    ScheduleManager scheduleManager;
    DataGenerator dataGenerator;

    @PostMapping(value = "excel",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<ResponseEntity<String>> acceptExcel(@RequestPart("file") FilePart file) {
        String fileName = dataGenerator.createExcelFileName(file, "scheduleOrigin");

        return Flux.just(scheduleClientService.saveFile(file, fileName))
                .then(Mono.fromRunnable(() -> scheduleClientService.sendScheduleFile(fileName)))
                .then(Mono.just(ResponseEntity.ok(file.filename())));
    }

    @PostMapping(value = "read")
    public Mono<ResponseEntity<String>> read(@RequestPart("file") Flux<FilePart> files) {
        return files
                .flatMap(file -> scheduleClientService.saveFile(file, dataGenerator.createExcelFileName(file, "schedule")))
                .then(Mono.fromRunnable(scheduleManager::writeSchedule))
                .then(Mono.just(ResponseEntity.ok().body("Files successfully saved")));
    }
}
