package com.backend.core.entity.google.gateway;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Getter
@Setter
@Data
public class GoogleUploadFileDTO {
    private MultipartFile multipartFile;

    private String path;

    private boolean isShared;

    @JsonCreator
    public GoogleUploadFileDTO(@JsonProperty("multipartFile") MultipartFile multipartFile,
                               @JsonProperty("path") String path,
                               @JsonProperty("isShared") boolean isShared) {
        this.multipartFile = multipartFile;
        this.path = path;
        this.isShared = isShared;
    }

    public GoogleUploadFileDTO() {
    }

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
