package com.backend.core.entity.interfaces;

import com.backend.core.entity.dto.PaginationDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Component;

@Component
public interface FilterRequest {
    public abstract Object getFilter();

    public abstract PaginationDTO getPagination();

    public abstract void setFilter(Object filter) throws JsonProcessingException;

    public abstract void setPagination(PaginationDTO paginationDTO);
}
