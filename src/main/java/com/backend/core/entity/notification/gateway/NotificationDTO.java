package com.backend.core.entity.notification.gateway;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationDTO implements Serializable {
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
