package com.example.jobService.service.impl;

import com.example.common.entity.Advert;
import com.example.common.entity.Job;
import com.example.common.entity.User;
import com.example.common.enums.AdvertStatus;
import com.example.common.enums.Advertiser;
import com.example.common.repository.AdvertRepository;
import com.example.jobService.client.FileStorageClient;
import com.example.jobService.client.UserServiceClient;
import com.example.jobService.dto.UserDto;
import com.example.jobService.model.advert.AdvertCreateRequest;
import com.example.jobService.model.advert.AdvertUpdateRequest;
import com.example.jobService.service.AdvertService;
import com.example.jobService.service.JobService;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdvertServiceImpl implements AdvertService {

    private final AdvertRepository advertRepository;
    private final FileStorageClient fileStorageClient;
    private final JobService jobService;
    private final UserServiceClient userServiceClient;
    private final ModelMapper modelMapper;

    @Override
    public Advert createAdvert(AdvertCreateRequest request, MultipartFile file) {
        String userId = getUserById(request.getUserId()).getId();
        Job job = jobService.getJobById(request.getJobId());

        String imageId = null;

        if (file != null) {
            imageId = fileStorageClient.uploadImageToFileSystem(file).getBody();
        }

        Advert advertToSave = Advert.builder()
                .userId(userId)
                .job(job)
                .name(request.getName())
                .advertiser(request.getAdvertiser())
                .deliveryTime(request.getDeliveryTime())
                .description(request.getDescription())
                .price(request.getPrice())
                .status(AdvertStatus.OPEN)
                .imageId(imageId)
                .build();

        return advertRepository.save(advertToSave);
    }

    @Override
    public List<Advert> getAll() {
        return advertRepository.findAll();
    }

    @Override
    public Advert getAdvertById(String id) {
        return findAdvertById(id);
    }

    @Override
    public List<Advert> getAdvertsByUserId(String id, Advertiser advertiser) {
        String userId = getUserById(id).getId();
        return advertRepository.getAdvertsByUserIdAndAdvertiser(userId, advertiser);
    }

    @Override
    public Advert updateAdvertById(AdvertUpdateRequest request, MultipartFile file) {
        Advert advertToUpdate = findAdvertById(request.getId());
        modelMapper.map(request, advertToUpdate);

        if (file != null) {
            String imageId = fileStorageClient.uploadImageToFileSystem(file).getBody();
            if (imageId != null) {
                fileStorageClient.deleteImageFromFileSystem(advertToUpdate.getImageId());
                advertToUpdate.setImageId(imageId);
            }
        }

        return advertRepository.save(advertToUpdate);
    }

    @Override
    public void deleteAdvertById(String id) {
        advertRepository.deleteById(id);
    }

    private Advert findAdvertById(String id) {
        return advertRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Advert not found"));
    }

    private UserDto getUserById(String id) {
        return Optional.ofNullable(userServiceClient.getUserById(id).getBody())
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public boolean authorizeCheck(String id, String principal) {
        return getUserById(getAdvertById(id).getUserId()).getUsername().equals(principal);
    }
}
