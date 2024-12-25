package com.example.jobService.service;

import com.example.common.entity.Job;
import com.example.jobService.model.job.JobCreateRequest;
import com.example.jobService.model.job.JobUpdateRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface JobService {

    Job createJob(JobCreateRequest jobCreateRequest, MultipartFile file);

    List<Job> getJobsThatFitYourNeeds(String needs);

    List<Job> getAll();

    Job getJobById(String id);

    List<Job> getJobsByCategoryId(String id);

    Job updateJob(JobUpdateRequest request, MultipartFile file);

    void deleteJobById(String id);
}
