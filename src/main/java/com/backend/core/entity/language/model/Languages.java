package com.backend.core.entity.language.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Objects;

@Entity
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
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

    @Column(name = "flag_image")
    String flagImage;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Languages languages = (Languages) o;
        return id == languages.id && Objects.equals(languageCode, languages.languageCode) && Objects.equals(name, languages.name) && Objects.equals(flagImage, languages.flagImage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, languageCode, name, flagImage);
    }

    @Override
    public String toString() {
        return "Languages{" +
                "id=" + id +
                ", languageCode='" + languageCode + '\'' +
                ", name='" + name + '\'' +
                ", flagImage='" + flagImage + '\'' +
                '}';
    }
}
