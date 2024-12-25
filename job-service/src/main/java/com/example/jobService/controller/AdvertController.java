package com.example.jobService.controller;

import com.example.common.enums.Advertiser;
import com.example.jobService.dto.AdvertDto;
import com.example.jobService.model.advert.AdvertCreateRequest;
import com.example.jobService.model.advert.AdvertUpdateRequest;
import com.example.jobService.service.AdvertService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/v1/job-service/advert")
@RequiredArgsConstructor
public class AdvertController {

    private final AdvertService advertService;
    private final ModelMapper modelMapper;

    @PostMapping("/create")
    public ResponseEntity<AdvertDto> createAdvert(@Valid @RequestPart AdvertCreateRequest request,
                                                  @RequestPart(required = false) MultipartFile file) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(modelMapper.map(advertService.createAdvert(request, file), AdvertDto.class));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<AdvertDto>> getAll() {
        return ResponseEntity.ok(advertService.getAll().stream()
                .map(advert -> modelMapper.map(advert, AdvertDto.class)).toList());
    }

    @GetMapping("/getAdvertById/{id}")
    public ResponseEntity<AdvertDto> getAdvertById(@PathVariable String id) {
        return ResponseEntity.ok(modelMapper.map(advertService.getAdvertById(id), AdvertDto.class));
    }

    @GetMapping("/getAdvertsByUserId/{id}")
    public ResponseEntity<List<AdvertDto>> getAdvertsByUserId(@PathVariable String id,
                                                              @RequestParam Advertiser advertiser) {
        return ResponseEntity.ok(advertService.getAdvertsByUserId(id, advertiser).stream()
                .map(advert -> modelMapper.map(advert, AdvertDto.class)).toList());
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN') or @advertService.authorizeCheck(#request.id, principal)")
    public ResponseEntity<AdvertDto> updateAdvertById(@Valid @RequestPart AdvertUpdateRequest request,
                                                      @RequestPart(required = false) MultipartFile file) {
        return ResponseEntity.ok(modelMapper.map(advertService.updateAdvertById(request, file), AdvertDto.class));
    }

    @DeleteMapping("/deleteAdvertById/{id}")
    @PreAuthorize("hasRole('ADMIN') or @advertService.authorizeCheck(#id, principal)")
    public ResponseEntity<Void> deleteAdvertById(@PathVariable String id) {
        advertService.deleteAdvertById(id);
        return ResponseEntity.ok().build();
    }
}
