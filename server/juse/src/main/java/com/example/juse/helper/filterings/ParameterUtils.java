package com.example.juse.helper.filterings;

import java.util.Arrays;
import java.util.Collection;

public class ParameterUtils {

    public static Collection<String> toCollection(String str) {
        Arrays.stream(str.split(","))
    }

}
