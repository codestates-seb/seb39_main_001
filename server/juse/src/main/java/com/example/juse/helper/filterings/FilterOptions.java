package com.example.juse.helper.filterings;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class FilterOptions {

    private List<String> type;
    private List<String> status;
    private List<String> tag;
    private List<String> period;

    public static FilterOptions of(String type, String tag, String period, String status) {

        return FilterOptions.builder()
                .type(setType(type))
                .status(setStatus(status))
                .tag(setTag(tag))
                .period(setPeriod(period))
                .build();

    }

    private static List<String> setType(String type) {

        if (type == null) {
            return ParameterUtils.setDefaultType();
        }

        return List.of(type);
    }

    private static List<String> setStatus(String status) {
        if (status == null) {
            return ParameterUtils.setDefaultStatus();
        }

        return List.of(status);
    }

    private static List<String> setTag(String tag) {
        if (tag == null) {
            return ParameterUtils.setDefaultTag();
        }

        return ParameterUtils.toCollection(tag);
    }

    private static List<String> setPeriod(String period) {
        if (period == null) {
            return ParameterUtils.setDefaultPeriod();
        }

        return ParameterUtils.toCollection(period);
    }
    

}
