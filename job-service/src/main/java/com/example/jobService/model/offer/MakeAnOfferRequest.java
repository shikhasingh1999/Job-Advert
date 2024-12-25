package com.example.jobService.model.offer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MakeAnOfferRequest {

    @NotBlank(message = "User id is required")
    private String userId;

    @NotBlank(message = "Advert id is required")
    private String advertId;

    @NotNull(message = "Offered price is required")
    private int offeredPrice;
}