package com.example.demo.service.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuditoriumDTO {
    int id;
    String block;
    String ident;

    public AuditoriumDTO(String block, String ident) {
        this.block = block;
        this.ident = ident;
    }
}
