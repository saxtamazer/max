package com.example.demo.service;

import com.example.demo.dao.TypeLessonRepository;
import com.example.demo.service.converter.modeltodto.TypeLessonModelToTypeLessonDTOConverter;
import com.example.demo.service.dto.TypeLessonDTO;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class TypeLessonService {
    TypeLessonRepository repository;
    TypeLessonModelToTypeLessonDTOConverter converter;

    public Optional<TypeLessonDTO> getTypeLessonByName(String name) {
        return repository.findByName(name).map(converter::convert);
    }
}
