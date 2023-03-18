package com.backend.core.entity.interfaces;

import org.springframework.stereotype.Component;

@Component
public interface FilterRequest {
    public abstract Object getFilter();

    public abstract String getKey();

    public abstract void setFilter(Object filter);

    public abstract void setKey(String key);
}
