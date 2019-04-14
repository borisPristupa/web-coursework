package com.ifmo.web.coursework.data.utils;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;

@Component
public class FilterUtils {
    public final <T> Predicate<T> nameFilter(String pattern, Function<T, String>... nameResolvers) {
        return o ->
                pattern.trim().isEmpty() ||
                        Arrays.stream(nameResolvers)
                                .filter(resolver -> null != resolver.apply(o))
                                .anyMatch(
                                        resolver -> Arrays.stream(pattern.trim().toLowerCase().split(" "))
                                                .anyMatch(resolver.apply(o).toLowerCase()::startsWith));
    }
}
