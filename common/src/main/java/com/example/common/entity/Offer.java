package com.example.common.entity;

import com.example.common.enums.OfferStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "offers")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Offer extends BaseEntity {
    private String userId;
    private int offeredPrice;

    @Enumerated(EnumType.STRING)
    private OfferStatus status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "advert_id")
    private Advert advert;
}
