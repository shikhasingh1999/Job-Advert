package com.example.common.repository;

import com.example.common.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, String> {

    List<Offer> getOffersByUserId(String id);

    List<Offer> getOffersByAdvertId(String id);
}
