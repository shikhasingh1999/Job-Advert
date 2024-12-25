package com.example.jobService.service.impl;

import com.example.common.entity.Category;
import com.example.common.entity.Job;
import com.example.common.repository.JobRepository;
import com.example.jobService.client.FileStorageClient;
import com.example.jobService.model.job.JobCreateRequest;
import com.example.jobService.model.job.JobUpdateRequest;
import com.example.jobService.service.CategoryService;
import com.example.jobService.service.JobService;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final CategoryService categoryService;
    private final FileStorageClient fileStorageClient;
    private final ModelMapper modelMapper;
    @Override
    public Job createJob(JobCreateRequest jobCreateRequest, MultipartFile file) {
        Category category = categoryService.getCategoryById(jobCreateRequest.getCategoryId());

        String imageId = null;

        if (file != null) {
            imageId = fileStorageClient.uploadImageToFileSystem(file).getBody();
        }

        return jobRepository.save(Job.builder()
                        .name(jobCreateRequest.getName())
                        .description(jobCreateRequest.getDescription())
                        .category(category)
                        .keys(Optional.of(List.of(jobCreateRequest.getKeys()))
                                .orElse(new ArrayList<>()))
                        .imageId(imageId)
                        .build());
    }

    @Override
    public List<Job> getJobsThatFitYourNeeds(String needs) {
        String [] keys = needs.replaceAll("\"", "").split(" ");

        HashMap<String, Integer> hashMap = new HashMap<>();

        Arrays.stream(keys).forEach(key ->
                jobRepository.getJobsByKeysContainsIgnoreCase(key)
                        .forEach(job -> {
                            if (hashMap.containsKey(job.getId())) {
                                int count = hashMap.get(job.getId());
                                hashMap.put(job.getId(), count + 1);
                            }
                            else {
                                hashMap.put(job.getId(), 1);
                            }
                        }));

        return hashMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(entry -> findJobById(entry.getKey()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Job> getAll() {
        return jobRepository.findAll();
    }

    @Override
    public Job getJobById(String id) {
        return findJobById(id);
    }

    @Override
    public List<Job> getJobsByCategoryId(String id) {
        return jobRepository.getJobsByCategoryId(id);
    }

    @Override
    public Job updateJob(JobUpdateRequest request, MultipartFile file) {
        Job jobToUpdate = findJobById(request.getCategoryId());

        modelMapper.map(request, jobToUpdate);

        if (file != null) {
            String imageId = fileStorageClient.uploadImageToFileSystem(file).getBody();
            if (imageId != null) {
                fileStorageClient.deleteImageFromFileSystem(jobToUpdate.getImageId());
                jobToUpdate.setImageId(imageId);
            }
        }

        return jobRepository.save(jobToUpdate);
    }

    @Override
    public void deleteJobById(String id) {
        jobRepository.deleteById(id);
    }

    private Job findJobById(String id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Job not found"));
    }
}
