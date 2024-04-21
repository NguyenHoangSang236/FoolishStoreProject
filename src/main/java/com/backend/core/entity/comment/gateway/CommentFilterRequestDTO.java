package com.backend.core.entity.comment.gateway;

import com.backend.core.entity.FilterRequest;
import com.backend.core.entity.api.PaginationDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.LinkedHashMap;

@NoArgsConstructor
@AllArgsConstructor
public class CommentFilterRequestDTO implements FilterRequest, Serializable {
    @JsonProperty("filter")
    CommentRequestDTO filter;

    @JsonProperty("pagination")
    PaginationDTO pagination;


    @Override
    public Object getFilter() {
        return this.filter;
    }

    @Override
    public void setFilter(Object filter) throws JsonProcessingException {
        LinkedHashMap<String, Object> filterMap = (LinkedHashMap<String, Object>) filter;
        ObjectMapper objectMapper = new ObjectMapper();
        this.filter = objectMapper.convertValue(filterMap, CommentRequestDTO.class);
    }

    @Override
    public PaginationDTO getPagination() {
        return this.pagination;
    }

    @Override
    public void setPagination(PaginationDTO pagination) {
        this.pagination = pagination;
    }


    @Override
    public String toString() {
        return "CommentFilterRequestDTO{" +
                "filter=" + filter +
                ", pagination=" + pagination +
                '}';
    }
}
