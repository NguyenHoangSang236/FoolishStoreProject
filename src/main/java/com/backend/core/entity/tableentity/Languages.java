package com.backend.core.entity.tableentity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "languages")
public class Languages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    int id;

    @Column(name = "language_code")
    String languageCode;

    @Column(name = "name")
    String name;

    @Column(name = "image_local_storage_path")
    String imageLocalStoragePath;


    public Languages(int id, String languageCode, String name, String imageLocalStoragePath) {
        this.id = id;
        this.languageCode = languageCode;
        this.name = name;
        this.imageLocalStoragePath = imageLocalStoragePath;
    }

    public Languages() {}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Languages languages = (Languages) o;
        return id == languages.id && Objects.equals(languageCode, languages.languageCode) && Objects.equals(name, languages.name) && Objects.equals(imageLocalStoragePath, languages.imageLocalStoragePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, languageCode, name, imageLocalStoragePath);
    }

    @Override
    public String toString() {
        return "Languages{" +
                "id=" + id +
                ", languageCode='" + languageCode + '\'' +
                ", name='" + name + '\'' +
                ", imageLocalStoragePath='" + imageLocalStoragePath + '\'' +
                '}';
    }
}
