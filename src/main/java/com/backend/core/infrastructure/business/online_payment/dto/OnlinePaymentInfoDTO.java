package com.backend.core.infrastructure.business.online_payment.dto;

import com.backend.core.entity.invoice.model.OnlinePaymentAccount;
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
