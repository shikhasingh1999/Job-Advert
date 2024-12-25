package com.example.common.repository;

import com.example.common.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, String> {
    List<Job> getJobsByKeysContainsIgnoreCase(String key);

    List<Job> getJobsByCategoryId(String id);
}
