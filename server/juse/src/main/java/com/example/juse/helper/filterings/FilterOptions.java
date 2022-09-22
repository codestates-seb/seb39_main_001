package com.example.juse.helper.filterings;

import com.example.juse.board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class FilterOptions {

    private List<Board.Type> type;
    private List<Board.Status> status;
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

    private static List<Board.Type> setType(String type) {

        if (type == null) {
            return Arrays.stream(Board.Type.values()).collect(Collectors.toList());
        }

        return List.of(Board.Type.valueOf(type.toUpperCase()));
    }

    private static List<Board.Status> setStatus(String status) {
        if (status == null) {
            return Arrays.stream(Board.Status.values()).collect(Collectors.toList());
        }
        return List.of(Board.Status.valueOf(status.toUpperCase()));
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
