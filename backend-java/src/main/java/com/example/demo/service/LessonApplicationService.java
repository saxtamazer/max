package com.example.demo.service;

import com.example.demo.api.json.LessonRequest;
import com.example.demo.dao.AuditoriumRepository;
import com.example.demo.dao.EducatorRepository;
import com.example.demo.dao.lesson.LessonRepository;
import com.example.demo.service.repositoryservice.*;
import com.example.demo.service.dto.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class LessonApplicationService {
    EducatorRepository educatorRepository;
    AuditoriumRepository auditoriumRepository;
    LessonRepository lessonRepository;
    TypeLessonService typeLessonService;
    StudentGroupService studentGroupService;
    TimeslotService timeslotService;
    SubjectService subjectService;
    EducatorService educatorService;
    AuditoriumService auditoriumService;
    LessonService lessonService;

    @Transactional
    public void save(LessonRequest lesson) {
        TypeLessonDTO typeLesson = typeLessonService.getTypeLessonByName(lesson.getType())
                .orElseThrow(
                        () -> new EntityNotFoundException(String.format(
                                "Type lesson with name <%s> not found",
                                lesson.getType()
                        )));

        StudentGroupDTO group = studentGroupService.getOrCreate(lesson.getGroup());

        LocalTime startTime = handleStartTime(lesson.getStartTime());
        TimeslotDTO timeslot = timeslotService.getTimeslotByDayAndStartTimeAndEven(
                lesson.getDayOfWeek(), startTime, lesson.isEven()
        ).orElseThrow(
                () -> new EntityNotFoundException(String.format(
                        "Timeslot at <%s> in <%s> isEven(%b) week not found",
                        lesson.getDayOfWeek(), startTime.toString(), lesson.isEven()
                )));

        SubjectDTO subject = subjectService.getOrCreate(lesson.getSubject(), typeLesson.getId());
        List<EducatorDTO> educators = extractEducator(lesson.getTeachers());
        List<AuditoriumDTO> auditoriums = extractAuditorium(lesson.getRooms());
        if (!lessonRepository.existsByGroupIdAndTimeslotId(group.getId(), timeslot.getId())) {
            lessonService.save(
                    group.getId(),
                    subject.getId(),
                    educators.stream()
                            .map(x -> educatorRepository.findById(x.getId())
                                    .orElseThrow(
                                            () -> new EntityNotFoundException(
                                                    String.format("Educator <%d> by name <%s> not found",
                                                            x.getId(), x.getFullName())
                                            )
                                    )
                            )
                            .toList(),
                    auditoriums.stream()
                            .map(x -> auditoriumRepository.findById(x.getId())
                                    .orElseThrow(
                                            () -> new EntityNotFoundException(
                                                    String.format("Auditorium <%d> in block <%s> with ident <%s> not found",
                                                            x.getId(), x.getBlock(), x.getIdent())
                                            )
                                    )
                            )
                            .toList(),
                    timeslot.getId()
            );
        }
    }

    private List<EducatorDTO> extractEducator(String[] fullNameEducators) {
        Set<EducatorDTO> educators = new HashSet<>();
        for (String fullName : fullNameEducators) {
            EducatorDTO educator = educatorService.getOrCreateByFullName(fullName);
            educators.add(educator);
        }
        return educators.stream().toList();
    }

    private List<AuditoriumDTO> extractAuditorium(String[] nameAuditoriums) {
        Set<AuditoriumDTO> auditoriums = new HashSet<>();
        for (String name : nameAuditoriums) {
            int delimiterPosition = name.indexOf('-');
            AuditoriumDTO auditorium;
            if (delimiterPosition != -1) {
                auditorium = auditoriumService.getOrCreate(
                        name.substring(0, delimiterPosition), name.substring(delimiterPosition + 1)
                );
            } else {
                auditorium = auditoriumService.getOrCreate(name, null);
            }
            auditoriums.add(auditorium);
        }
        return auditoriums.stream().toList();
    }

    private LocalTime handleStartTime(String jsonStartTime) {
        String[] splitTime = jsonStartTime.split("\\.");
        return LocalTime.of(Integer.parseInt(splitTime[0]), Integer.parseInt(splitTime[1]));
    }
}
