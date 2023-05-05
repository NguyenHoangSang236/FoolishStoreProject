package com.backend.core.entity.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class GoogleTranslateDTO {
    String text;
    String sourceLanguageCode;

    public GoogleTranslateDTO(String text, String sourceLanguageCode) {
        this.text = text;
        this.sourceLanguageCode = sourceLanguageCode;
    }

    public GoogleTranslateDTO() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GoogleTranslateDTO that = (GoogleTranslateDTO) o;
        return Objects.equals(text, that.text) && Objects.equals(sourceLanguageCode, that.sourceLanguageCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, sourceLanguageCode);
    }

    @Override
    public String toString() {
        return "GoogleTranslateDTO{" +
                "text='" + text + '\'' +
                ", sourceLanguageCode='" + sourceLanguageCode + '\'' +
                '}';
    }
}
