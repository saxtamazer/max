package com.example.demo.service.converter.dtotoresponse;

import com.example.demo.api.json.LoadEducatorResponse;
import com.example.demo.service.dto.LoadEducatorDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class LoadEducatorDTOToResponseConverter implements Converter<LoadEducatorDTO, LoadEducatorResponse> {
    @Override
    public LoadEducatorResponse convert(LoadEducatorDTO source) {
        return source == null ?
                null
                :
                new LoadEducatorResponse(
                        source.isEven(),
                        source.getDayOfWeek(),
                        source.getPairNumber(),
                        source.getPairType(),
                        source.getPairName(),
                        Arrays.stream(source.getGroups().split(";")).toList(),
                        Arrays.stream(source.getAuditoriums().split(";")).toList()
                );
    }
}
