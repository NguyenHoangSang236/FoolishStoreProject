package com.backend.core.entity.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
public class GoogleUploadFileDTO {
    private MultipartFile multipartFile;

    private String path;

    private boolean isShared;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GoogleUploadFileDTO that = (GoogleUploadFileDTO) o;
        return isShared == that.isShared && Objects.equals(multipartFile, that.multipartFile) && Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(multipartFile, path, isShared);
    }

    @Override
    public String toString() {
        return "GoogleUploadFileDTO{" +
                "multipartFile=" + multipartFile +
                ", path='" + path + '\'' +
                ", isShared=" + isShared +
                '}';
    }
}
