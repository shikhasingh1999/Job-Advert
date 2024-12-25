package com.example.jobService.service.impl;

import com.example.common.entity.Advert;
import com.example.common.entity.Offer;
import com.example.common.enums.OfferStatus;
import com.example.common.repository.OfferRepository;
import com.example.jobService.client.UserServiceClient;
import com.example.jobService.dto.UserDto;
import com.example.jobService.exception.NotFoundException;
import com.example.jobService.model.notification.SendNotificationRequest;
import com.example.jobService.model.offer.MakeAnOfferRequest;
import com.example.jobService.model.offer.OfferUpdateRequest;
import com.example.jobService.service.AdvertService;
import com.example.jobService.service.OfferService;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final AdvertService advertService;
    private final UserServiceClient userServiceclient;
    private final ModelMapper modelMapper;
    private final KafkaTemplate<String, SendNotificationRequest> kafkaTemplate;
    private final NewTopic topic;

    @Override
    public Offer makeAnOffer(MakeAnOfferRequest request) {
        String userId = getUserById(request.getUserId()).getId();
        Advert advert = advertService.getAdvertById(request.getAdvertId());
        Offer offerToSave = Offer.builder()
                .userId(userId)
                .advert(advert)
                .offeredPrice(request.getOfferedPrice())
                .status(OfferStatus.OPEN)
                .build();
        offerRepository.save(offerToSave);

        SendNotificationRequest sendNotificationRequest = SendNotificationRequest.builder()
                .userId(advert.getUserId())
                .offerId(offerToSave.getId())
                .message("You have received an offer for your advertising.")
                .build();

        // publish kafka message
        kafkaTemplate.send(topic.name(), sendNotificationRequest);

        return offerToSave;
    }

    @Override
    public Offer getOfferById(String id) {
        return findOfferById(id);
    }

    @Override
    public List<Offer> getOffersByUserId(String id) {
        String userId = getUserById(id).getId();
        return offerRepository.getOffersByUserId(userId);
    }

    @Override
    public List<Offer> getOffersByAdvertId(String id) {
        Advert advert = advertService.getAdvertById(id);
        return offerRepository.getOffersByAdvertId(advert.getId());
    }

    @Override
    public Offer updateOfferById(OfferUpdateRequest request) {
        Offer offerToUpdate = findOfferById(request.getId());
        modelMapper.map(request, offerToUpdate);
        return offerRepository.save(offerToUpdate);
    }

    @Override
    public void deleteOfferById(String id) {
        offerRepository.deleteById(id);
    }

    private Offer findOfferById(String id) {
        return offerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Offer not found"));
    }

    public UserDto getUserById(String id) {
        return Optional.ofNullable(userServiceclient.getUserById(id).getBody())
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    public boolean authorizeCheck(String id, String principal) {
        return getUserById(getOfferById(id).getUserId()).getUsername().equals(principal);
    }
}
