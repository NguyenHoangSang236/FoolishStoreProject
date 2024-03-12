package com.backend.core.entity.api;

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

    public PaginationDTO() {
    }
}
