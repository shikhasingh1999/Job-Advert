package com.example.jobService.service;

import com.example.common.entity.Offer;
import com.example.jobService.model.offer.MakeAnOfferRequest;
import com.example.jobService.model.offer.OfferUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public interface OfferService {
    Offer makeAnOffer(MakeAnOfferRequest request);

    Offer getOfferById(String id);

    List<Offer> getOffersByUserId(String id);

    List<Offer> getOffersByAdvertId(String id);

    Offer updateOfferById(OfferUpdateRequest request);

    void deleteOfferById(String id);
}
