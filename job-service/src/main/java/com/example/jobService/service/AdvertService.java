package com.example.jobService.service;

import com.example.common.entity.Advert;
import com.example.common.enums.Advertiser;
import com.example.jobService.model.advert.AdvertCreateRequest;
import com.example.jobService.model.advert.AdvertUpdateRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface AdvertService {
    Advert createAdvert(AdvertCreateRequest request, MultipartFile file);

    List<Advert> getAll();

    Advert getAdvertById(String id);

    List<Advert> getAdvertsByUserId(String id, Advertiser advertiser);

    Advert updateAdvertById(AdvertUpdateRequest request, MultipartFile file);

    void deleteAdvertById(String id);

    boolean authorizeCheck(String id, String principal);
}
