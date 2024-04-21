package com.backend.core.entity.account.gateway;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountFilterDTO implements Serializable {
    @JsonProperty("status")
    String status;

    @JsonProperty("city")
    String city;

    @JsonProperty("address")
    String address;

    @JsonProperty("country")
    String country;

    @JsonProperty("phoneNumber")
    String phoneNumber;

    @JsonProperty("email")
    String email;

    @JsonProperty("name")
    String name;

    @JsonProperty("userName")
    String userName;
}
