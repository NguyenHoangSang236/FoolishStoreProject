package com.backend.core.entity.notification.gateway;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationFilterDTO implements Serializable {
    @JsonProperty("startDate")
    Date startDate;

    @JsonProperty("endDate")
    Date endDate;
}
