package com.example.juse.helper.filterings;

import com.example.juse.board.entity.Board;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ParameterUtils {

    public static List<String> toCollection(String str) {
        return Arrays.stream(str.split(",")).map(String::trim).map(String::toLowerCase).collect(Collectors.toList());
    }

    public static List<String> setDefaultTag() {
        return Arrays.stream(QueryParameters.Tags.values()).map(Enum::toString).map(String::toLowerCase).collect(Collectors.toList());
    }

    public static List<String> setDefaultPeriod() {
        return List.of("short", "1", "2", "3", "4", "5", "6", "long");

    }

}
