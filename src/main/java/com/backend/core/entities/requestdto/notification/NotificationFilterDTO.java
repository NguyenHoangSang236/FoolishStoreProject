package com.backend.core.entities.requestdto.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationFilterDTO {
    @JsonProperty("startDate")
    Date startDate;

    @JsonProperty("endDate")
    Date endDate;
}
