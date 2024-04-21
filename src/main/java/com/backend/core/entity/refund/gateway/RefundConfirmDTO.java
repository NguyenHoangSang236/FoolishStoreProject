package com.backend.core.entity.refund.gateway;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefundConfirmDTO implements Serializable {
    @JsonProperty("evidenceImage")
    MultipartFile evidenceImage;

    @JsonProperty("invoiceId")
    int invoiceId;
}
