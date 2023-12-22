package com.backend.core.entities.requestdto.refund;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefundConfirmDTO {
    @JsonProperty("evidenceImage")
    MultipartFile evidenceImage;

    @JsonProperty("invoiceId")
    int invoiceId;
}
