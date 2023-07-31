package com.backend.core.entities.dto.google;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
public class GoogleDriveFoldersDTO implements Serializable {
    private String id;
    private String name;
    private String link;
}

