package com.example.demo.service.converter.dtotoresponse;

import com.example.demo.api.json.LoadStudentGroupResponse;
import com.example.demo.service.dto.LoadStudentGroupDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class LoadStudentGroupDTOToResponseConverter implements Converter<LoadStudentGroupDTO, LoadStudentGroupResponse> {
    @Override
    public LoadStudentGroupResponse convert(LoadStudentGroupDTO source) {
        return source == null ?
                null
                :
                new LoadStudentGroupResponse(
                        source.isEven(),
                        source.getDayOfWeek(),
                        source.getPairNumber(),
                        source.getPairName(),
                        source.getPairType(),
                        source.getAuditoriums() == null ? null : Arrays.stream(source.getAuditoriums().split(";")).toList()
                );
    }
}
