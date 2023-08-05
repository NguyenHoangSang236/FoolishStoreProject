package com.backend.core.entities.requestdto.comment;

import com.backend.core.entities.interfaces.FilterRequest;
import com.backend.core.entities.requestdto.PaginationDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.LinkedHashMap;

public class CommentFilterRequestDTO implements FilterRequest {
    @JsonProperty("filter")
    CommentRequestDTO filter;

    @JsonProperty("pagination")
    PaginationDTO pagination;


    public CommentFilterRequestDTO() {}

    public CommentFilterRequestDTO(CommentRequestDTO filter, PaginationDTO pagination) {
        this.filter = filter;
        this.pagination = pagination;
    }



    @Override
    public Object getFilter() {
        return this.filter;
    }

    @Override
    public PaginationDTO getPagination() {
        return this.pagination;
    }

    @Override
    public void setFilter(Object filter) throws JsonProcessingException {
        LinkedHashMap<String, Object> filterMap = (LinkedHashMap<String, Object>) filter;
        ObjectMapper objectMapper = new ObjectMapper();
        this.filter = objectMapper.convertValue(filterMap, CommentRequestDTO.class);
    }

    @Override
    public void setPagination(PaginationDTO pagination) {
        this.pagination = pagination;
    }
}
