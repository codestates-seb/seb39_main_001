package com.example.juse.helper.filterings;

import lombok.*;

@Getter
@AllArgsConstructor
@Builder
public class FilterOptions {

    private final String type;
    private final String tag;
    private final String period;

    public static FilterOptions of(String type, String tag, String period) {
        return FilterOptions.builder()
                .type(type)
                .tag(tag)
                .period(period)
                .build();
    }

}
