package com.example.common.repository;

import com.example.common.entity.Advert;
import com.example.common.enums.Advertiser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvertRepository extends JpaRepository<Advert, String> {
    List<Advert> getAdvertsByUserIdAndAdvertiser(String id, Advertiser advertiser);
}
