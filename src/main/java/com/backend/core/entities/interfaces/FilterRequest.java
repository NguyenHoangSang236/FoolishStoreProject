package com.backend.core.entities.interfaces;

import com.backend.core.entities.requestdto.PaginationDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Component;

@Component
public interface FilterRequest {
    public abstract Object getFilter();

    public abstract PaginationDTO getPagination();

    public abstract void setFilter(Object filter) throws JsonProcessingException;

    public abstract void setPagination(PaginationDTO paginationDTO);
}
