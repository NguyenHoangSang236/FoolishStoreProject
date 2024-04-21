package com.backend.core.entity.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class ListRequestDTO implements Serializable {
    @Nullable
    @JsonProperty("objectList")
    List<Object> objectList;

    @Nullable
    @JsonProperty("integerArray")
    int[] integerArray;

    @Nullable
    @JsonProperty("doubleArray")
    double[] doubleArray;

    @Nullable
    @JsonProperty("floatArray")
    float[] floatArray;


    public ListRequestDTO() {
    }


    @Override
    public String toString() {
        return "ListRequestDTO{" +
                "objectList=" + objectList +
                ", integerArray=" + Arrays.toString(integerArray) +
                ", doubleArray=" + Arrays.toString(doubleArray) +
                ", floatArray=" + Arrays.toString(floatArray) +
                '}';
    }
}
