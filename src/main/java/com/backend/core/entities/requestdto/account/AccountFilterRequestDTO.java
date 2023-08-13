package com.backend.core.entities.requestdto.account;

import com.backend.core.entities.interfaces.FilterRequest;
import com.backend.core.entities.requestdto.PaginationDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class AccountFilterRequestDTO implements FilterRequest {
    @JsonProperty("filter")
    AccountFilterDTO filter;

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
        this.filter = objectMapper.convertValue(filterMap, AccountFilterDTO.class);
    }

    @Override
    public PaginationDTO getPagination() {
        return this.pagination;
    }

    @Override
    public void setPagination(PaginationDTO paginationDTO) {
        this.pagination = paginationDTO;
    }
}
