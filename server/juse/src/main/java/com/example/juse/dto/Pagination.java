package com.example.juse.dto;

import com.example.juse.helper.filterings.FilterOptions;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pagination {

    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private FilterOptions filterOptions;

    public static Pagination of(Page page, FilterOptions filterOptions) {
        return Pagination.builder()
                .page(page.getNumber() + 1)
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .filterOptions(filterOptions)
                .build();
    }
}
