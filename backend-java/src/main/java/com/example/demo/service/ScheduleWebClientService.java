package com.example.demo.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.File;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ScheduleWebClientService {
    WebClient webClient;

    public void sendFile() {
        String filePath = "src/main/resources/shedule.xlsx";
        File file = new File(filePath);
        FileSystemResource excel = new FileSystemResource(file);

        System.out.println(1);

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
}
