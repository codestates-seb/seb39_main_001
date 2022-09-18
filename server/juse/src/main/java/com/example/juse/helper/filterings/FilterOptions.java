package com.example.juse.helper.filterings;

import lombok.*;

@Getter
@AllArgsConstructor
@Builder
public class FilterOptions {

    private final String type;
    private final String tag;
    private final String period;
    private final String status;

    public static FilterOptions of(String type, String tag, String period, String status) {
        return FilterOptions.builder()
                .type(type)
                .tag(tag)
                .period(period)
                .status(status)
                .build();
    }

}
