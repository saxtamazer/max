package com.example.demo.service;

import com.example.demo.configuration.ConfigProperties;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ScheduleClientService {
    ConfigProperties configProperties;
    WebClient webClient;

    public void sendFile() {
        String filePath = configProperties.getScheduleStorage() + "shedule.xlsx";
        File file = new File(filePath);
        FileSystemResource excel = new FileSystemResource(file);

        webClient.post()
                .uri("py/v1/parser")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(
                        BodyInserters.fromMultipartData("file", excel)
                )
                .retrieve()
                .bodyToMono(String.class)
                .subscribe();
    }

    public Mono<Void> saveFile(FilePart filePart) {
        String nameJson = filePart.filename();
        configProperties.setNameJsonFile(nameJson);
        Path path = Paths.get(configProperties.getScheduleStorage() + configProperties.getNameJsonFile());
        return filePart.transferTo(path);
    }
}
