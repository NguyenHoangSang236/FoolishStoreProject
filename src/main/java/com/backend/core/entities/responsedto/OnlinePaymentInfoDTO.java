package com.backend.core.entities.responsedto;

import com.backend.core.entities.tableentity.OnlinePaymentAccount;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OnlinePaymentInfoDTO {
    @JsonProperty("content")
    String content;

    @JsonProperty("moneyAmount")
    double moneyAmount;

    @JsonProperty("receiverInfo")
    OnlinePaymentAccount onlinePaymentAccount;
}
