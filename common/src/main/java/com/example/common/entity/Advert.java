package com.example.common.entity;

import com.example.common.enums.AdvertStatus;
import com.example.common.enums.Advertiser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "adverts")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Advert extends BaseEntity {

    private String userId;
    private String name;
    private String description;
    private int deliveryTime;
    private int price;
    private String imageId;

    @Enumerated(EnumType.STRING)
    private AdvertStatus status;

    @Enumerated(EnumType.STRING)
    private Advertiser advertiser;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "job-id")
    private Job job;

    @OneToMany(mappedBy = "advert", cascade = CascadeType.ALL)
    private List<Offer> offers;


}
