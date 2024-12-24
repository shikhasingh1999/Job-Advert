package com.example.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Entity(name = "jobs")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Job extends BaseEntity {

    private String name;
    private String description;
    private String imageId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
    private List<Advert> adverts;

    @ElementCollection
    private List<String> keys = Collections.emptyList();
}
