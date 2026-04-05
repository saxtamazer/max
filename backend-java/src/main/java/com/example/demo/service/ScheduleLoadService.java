package com.example.demo.service;

import com.example.demo.api.json.LoadAuditoriumResponse;
import com.example.demo.api.json.LoadEducatorResponse;
import com.example.demo.api.json.LoadStudentGroupResponse;
import com.example.demo.dao.ScheduleLoadRepository;
import com.example.demo.service.converter.dtotoresponse.LoadAuditoriumDTOToResponseConverter;
import com.example.demo.service.converter.dtotoresponse.LoadEducatorDTOToResponseConverter;
import com.example.demo.service.converter.dtotoresponse.LoadStudentGroupDTOToResponseConverter;
import com.example.demo.service.dto.LoadAuditroiumDTO;
import com.example.demo.service.dto.LoadEducatorDTO;
import com.example.demo.service.dto.LoadStudentGroupDTO;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ScheduleLoadService {
    ScheduleLoadRepository repository;
    LoadAuditoriumDTOToResponseConverter loadAuditoriumDTOToResponseConverter;
    LoadEducatorDTOToResponseConverter loadEducatorDTOToResponseConverter;
    LoadStudentGroupDTOToResponseConverter loadStudentGroupDTOToResponseConverter;

    public List<LoadAuditoriumResponse> getAuditorium(String name) {
        String[] splitName = name.split("-");

        List<LoadAuditroiumDTO> result = repository.getReportLoadAuditorium(splitName[0], splitName[1]);

        return result.stream()
                .map(loadAuditoriumDTOToResponseConverter::convert)
                .toList();
    }

    public List<LoadEducatorResponse> getEducator(String fullName) {
        List<LoadEducatorDTO> result = repository.getReportLoadEducator(fullName);

        return result.stream()
                .map(loadEducatorDTOToResponseConverter:: convert)
                .toList();
    }

    public List<LoadStudentGroupResponse> getStudentGroup(String name) {
        List<LoadStudentGroupDTO> result = repository.getReportLoadStudentGroup(name);

        return result.stream()
                .map(loadStudentGroupDTOToResponseConverter::convert)
                .toList();
    }
}
