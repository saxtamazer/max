package com.example.demo.service.converter.dtotoresponse;

import com.example.demo.api.json.LoadAuditoriumResponse;
import com.example.demo.service.dto.LoadAuditroiumDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class LoadAuditoriumDTOToResponseConverter implements Converter<LoadAuditroiumDTO, LoadAuditoriumResponse> {
    @Override
    public LoadAuditoriumResponse convert(LoadAuditroiumDTO source) {
        return source == null ?
                null
                :
                new LoadAuditoriumResponse(
                        source.isEven(),
                        source.getDayOfWeek(),
                        source.getPairNumber(),
                        Arrays.stream(source.getGroups().split(";")).toList()
                );
    }
}
