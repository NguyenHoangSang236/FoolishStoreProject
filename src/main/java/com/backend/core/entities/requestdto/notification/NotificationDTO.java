package com.backend.core.entities.requestdto.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {
    @JsonProperty("title")
    String title;

    @JsonProperty("body")
    String body;

    @Nullable
    @JsonProperty("topic")
    String topic;

    @Nullable
    @JsonProperty("deviceFcmTokenList")
    List<String> deviceFcmTokenList;

    @JsonProperty("data")
    Map<String, String> data;
}
