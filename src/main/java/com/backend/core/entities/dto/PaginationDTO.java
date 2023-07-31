package com.backend.core.entities.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationDTO {
    int page;
    int limit;
    String type;

    public PaginationDTO(int page, int limit, String type) {
        this.page = page;
        this.limit = limit;
        this.type = type;
    }

    public PaginationDTO() {}

    @Override
    public String toString() {
        return "PaginationDTO{" +
                "page=" + page +
                ", limit=" + limit +
                ", type=" + type +
                '}';
    }
}
