package com.backend.core.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class OnlinePaymentReceiverDTO {
    @JsonProperty("content")
    String content;

    @JsonProperty("receiverAccount")
    String receiverAccount;

    @JsonProperty("receiverName")
    String receiverName;

    @JsonProperty("bankName")
    String bankName;

    @JsonProperty("moneyAmount")
    double moneyAmount;


    public OnlinePaymentReceiverDTO(String content, String receiverAccount, String receiverName, String bankName, double moneyAmount) {
        this.content = content;
        this.receiverAccount = receiverAccount;
        this.receiverName = receiverName;
        this.bankName = bankName;
        this.moneyAmount = moneyAmount;
    }

    public OnlinePaymentReceiverDTO() {}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OnlinePaymentReceiverDTO that = (OnlinePaymentReceiverDTO) o;
        return Double.compare(that.moneyAmount, moneyAmount) == 0 && Objects.equals(content, that.content) && Objects.equals(receiverAccount, that.receiverAccount) && Objects.equals(receiverName, that.receiverName) && Objects.equals(bankName, that.bankName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, receiverAccount, receiverName, bankName, moneyAmount);
    }

    @Override
    public String toString() {
        return "OnlinePaymentReceiverDTO{" +
                "content='" + content + '\'' +
                ", receiverAccount='" + receiverAccount + '\'' +
                ", receiverName='" + receiverName + '\'' +
                ", bankName='" + bankName + '\'' +
                ", moneyAmount=" + moneyAmount +
                '}';
    }
}
