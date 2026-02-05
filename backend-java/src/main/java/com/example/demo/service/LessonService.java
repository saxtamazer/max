package com.example.demo.service;

import com.example.demo.dao.entities.AuditoriumModel;
import com.example.demo.dao.entities.EducatorModel;
import com.example.demo.dao.entities.LessonModel;
import com.example.demo.dao.lesson.EvenFilter;
import com.example.demo.dao.lesson.LessonRepository;
import com.example.demo.service.converter.modeltodto.EducatorModelToEducatorDTOConverter;
import com.example.demo.service.converter.modeltodto.LessonModelToLessonDTOConverter;
import com.example.demo.service.converter.modeltodto.SubjectModelToSubjectDTOConverter;
import com.example.demo.service.dto.AdvancedLessonDTO;
import com.example.demo.service.dto.AuditoriumDTO;
import com.example.demo.service.dto.EducatorDTO;
import com.example.demo.service.dto.LessonDTO;
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
    EducatorModelToEducatorDTOConverter educatorConverter;
    SubjectModelToSubjectDTOConverter subjectConverter;
    StudentGroupService studentGroupService;
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
                .findAllLessonByEvenFilter(evenFilter)
                .stream()
                .map(converter::convert)
                .toList();
    }

    public List<AdvancedLessonDTO> getAllAdvancedLessonsByEven(EvenFilter evenFilter) {
        return this.getAllLessonsByEven(evenFilter)
                .stream()
                .map(lesson -> {
                    return new AdvancedLessonDTO(
                            lesson.getId(),
                            studentGroupService.getStudentGroup(lesson.getGroupId()),
                            subjectService.getSubjectById(lesson.getSubjectId()),
                            (EducatorDTO[]) Arrays.stream(lesson.getEducators())
                                    .mapToObj(educatorService::getEducatorById)
                                    .toArray(),
                            (AuditoriumDTO[]) Arrays.stream(lesson.getAuditoriums())
                                    .mapToObj(auditoriumService::getAuditoriumById)
                                    .toArray(),
                            timeslotService.getTimeslotById(lesson.getTimeslotId())
                        );
                    }
                )
                .toList();
    }
}
