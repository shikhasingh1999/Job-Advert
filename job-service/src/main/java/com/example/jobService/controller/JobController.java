package com.example.jobService.controller;

import com.example.jobService.dto.JobDto;
import com.example.jobService.model.job.JobCreateRequest;
import com.example.jobService.model.job.JobUpdateRequest;
import com.example.jobService.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/job-service/job")
@RequiredArgsConstructor
public class JobController {

    private final ModelMapper modelMapper;
    private final JobService jobService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<JobDto> createJob (@Valid @RequestPart JobCreateRequest jobCreateRequest,
                                             @RequestPart(required = false)MultipartFile file) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(modelMapper.map(jobService.createJob(jobCreateRequest, file), JobDto.class));
    }

    @GetMapping("/getJobsThatFitYourNeeds/{needs}")
    public ResponseEntity<List<JobDto>> getJobsThatFitYourNeeds (@PathVariable String needs) {
        return ResponseEntity.ok(jobService.getJobsThatFitYourNeeds(needs).stream()
                .map(job -> modelMapper.map(job, JobDto.class)).collect(Collectors.toList()));
    }

    @GetMapping("/getAll")
    ResponseEntity<List<JobDto>> getAll() {
        return ResponseEntity.ok(jobService.getAll().stream()
                .map(job -> modelMapper.map(job, JobDto.class)).toList());
    }

    @GetMapping("/getJobById/{id}")
    ResponseEntity<JobDto> getJobById(@PathVariable String id) {
        return ResponseEntity.ok(modelMapper.map(jobService.getJobById(id), JobDto.class));
    }

    @GetMapping("/getJobsByCategoryId/{id}")
    ResponseEntity<List<JobDto>> getJobsByCategoryId(@PathVariable String id) {
        return ResponseEntity.ok(jobService.getJobsByCategoryId(id).stream()
                .map(job -> modelMapper.map(job, JobDto.class)).toList());
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<JobDto> updateJob(@Valid @RequestPart JobUpdateRequest request,
                                     @RequestPart(required = false) MultipartFile file) {
        return ResponseEntity.ok(modelMapper.map(jobService.updateJob(request, file), JobDto.class));
    }

    @DeleteMapping("/deleteJobById/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Void> deleteJobById(@PathVariable String id) {
        jobService.deleteJobById(id);
        return ResponseEntity.ok().build();
    }




}
