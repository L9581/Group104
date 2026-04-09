package service;

import java.util.List;
import java.util.UUID;

import model.Job;
import model.JobStatus;
import storage.JobRepository;

/**
 * Owns job creation and the basic job listing queries used by the UI.
 */
public class JobService {
    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public Job createJob(String rawJobName, String postedBy) {
        String jobName = rawJobName == null ? "" : rawJobName.trim();
        if (jobName.isEmpty()) {
            throw new IllegalArgumentException("Job name cannot be empty.");
        }

        Job job = new Job(UUID.randomUUID().toString(), jobName, postedBy, JobStatus.OPEN);
        jobRepository.save(job);
        return job;
    }

    public List<Job> listVisibleJobs() {
        return jobRepository.findByStatus(JobStatus.OPEN);
    }

    public List<Job> listJobsPostedBy(String postedBy) {
        return jobRepository.findByPostedBy(postedBy);
    }
}
