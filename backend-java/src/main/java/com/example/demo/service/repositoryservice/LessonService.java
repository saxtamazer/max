package com.example.demo.service.repositoryservice;

import com.example.demo.dao.entities.AuditoriumModel;
import com.example.demo.dao.entities.EducatorModel;
import com.example.demo.dao.entities.LessonModel;
import com.example.demo.utils.filter.EvenFilter;
import com.example.demo.dao.lesson.LessonRepository;
import com.example.demo.service.converter.modeltodto.LessonModelToLessonDTOConverter;
import com.example.demo.service.dto.*;
import com.example.demo.utils.filter.Filter;
import com.example.demo.utils.filter.GroupFilter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class LessonService {
    LessonRepository lessonRepository;
    LessonModelToLessonDTOConverter converter;
    StudentGroupService studentGroupService;
    TypeLessonService typeLessonService;
    SubjectService subjectService;
    EducatorService educatorService;
    AuditoriumService auditoriumService;
    TimeslotService timeslotService;

    public LessonDTO save(int groupId,
                          int subjectId,
                          List<EducatorModel> educators,
                          List<AuditoriumModel> auditoriums,
                          int timeslotId) {
        LessonModel lessonModel = new LessonModel();
        lessonModel.setGroupId(groupId);
        lessonModel.setSubjectId(subjectId);
        lessonModel.setTimeslotId(timeslotId);
        educators.forEach(lessonModel::addEducator);
        auditoriums.forEach(lessonModel::addAuditorium);
        return converter.convert(lessonRepository.save(lessonModel));
    }

    public List<LessonDTO> getAllLessons() {
        return lessonRepository
                .findAll()
                .stream()
                .map(converter::convert)
                .toList();
    }

    public List<LessonDTO> getAllLessonsByEven(EvenFilter evenFilter) {
        return lessonRepository
                .findAllByEvenFilter(evenFilter)
                .stream()
                .map(converter::convert)
                .toList();
    }

    public List<LessonDTO> getAllLessonByGroup(GroupFilter groupFilter) {
        return lessonRepository.findAllByGroupFilter(groupFilter)
                .stream()
                .map(converter::convert)
                .toList();
    }

    public List<AdvancedLessonDTO> getAllAdvancedLessonsByFilter(EvenFilter filter) {
        List<LessonDTO> lessons = this.getAllLessonsByEven(filter);
        return advancedLesson(lessons);
    }

    public List<AdvancedLessonDTO> getAllAdvancedLessonsByFilter(GroupFilter filter) {
        List<LessonDTO> lessons = this.getAllLessonByGroup(filter);
        return advancedLesson(lessons);
    }

    private List<AdvancedLessonDTO> advancedLesson(List<LessonDTO> lessons) {
        return lessons
                .stream()
                .map(lesson -> {
                            return new AdvancedLessonDTO(
                                    lesson.getId(),
                                    studentGroupService.getStudentGroup(lesson.getGroupId()),
                                    subjectService.getSubjectById(lesson.getSubjectId()),
                                    educatorService.getAllEducatorByIds(
                                            Arrays.stream(lesson.getEducatorIds())
                                                    .boxed()
                                                    .toList()
                                    ).toArray(EducatorDTO[]::new),
                                    auditoriumService.getAllAuditoriumByIds(
                                            Arrays.stream(lesson.getAuditoriumIds())
                                                    .boxed()
                                                    .toList()
                                    ).toArray(AuditoriumDTO[]::new),
                                    timeslotService.getTimeslotById(lesson.getTimeslotId())
                            );
                        }
                )
                .toList();
    }
}
