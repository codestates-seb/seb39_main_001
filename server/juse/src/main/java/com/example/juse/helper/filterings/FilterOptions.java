package com.example.juse.helper.filterings;

import com.example.juse.board.entity.Board;
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

    private Board.Type type;
    private Board.Status status;
    private List<String> tag;
    private List<String> period;

    public static FilterOptions of(String type, String tag, String period, String status) {

        return FilterOptions.builder()
                .type(setType(type))
                .status(setStatus(status))
                .period(setPeriod(period))
                .tag(setTag(tag))
                .build();


    }

    private static Board.Type setType(String type) {

        if (type != null) {
            return Board.Type.valueOf(type.toUpperCase());
        }

        return null;
    }

    private static Board.Status setStatus(String status) {

        if (status != null) {
            return Board.Status.valueOf(status.toUpperCase());
        }

        return null;
    }

    private static List<String> setTag(String tag) {

        if (tag != null) {
            return ParameterUtils.toCollection(tag);
        }

        return null;
    }

    private static List<String> setPeriod(String period) {

        if (period != null) {
            return ParameterUtils.toCollection(period);
        }

        return null;

    }
    

}
