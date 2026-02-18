package com.schoolar.lynx.utils;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MapperUtil {
    private static final ModelMapper modelMapper = new ModelMapper();
    public static <O, D> D parseObject(O origin, Class<D> destination) {
        return modelMapper.map(origin, destination);
    }
    public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination) {
        return origin.stream()
                .map(element -> modelMapper.map(element, destination))
                .collect(Collectors.toList());
    }
}