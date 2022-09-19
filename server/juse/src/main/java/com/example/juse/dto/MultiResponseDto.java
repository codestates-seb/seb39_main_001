package com.example.juse.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class MultiResponseDto<T> {

    private final List<T> data;
    private final Pagination pagination;

}
