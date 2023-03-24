package com.backend.core.entity.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Component;

@Component
public interface FilterRequest {
    public abstract Object getFilter();

    public abstract String getKey();

    public abstract void setFilter(Object filter) throws JsonProcessingException;

    public abstract void setKey(String key);
}
