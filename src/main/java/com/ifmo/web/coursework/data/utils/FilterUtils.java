package com.ifmo.web.coursework.data.utils;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;

@Component
public class FilterUtils {
    public <T> Predicate<T> nameFilter(String pattern, Function<T, String>... nameResolvers) {
        return o ->
                pattern.trim().isEmpty() ||
                        Arrays.stream(nameResolvers).anyMatch(
                                resolver -> Arrays.stream(pattern.trim().split(" "))
                                        .anyMatch(resolver.apply(o)::startsWith));
    }
}
