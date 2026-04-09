package service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import model.Application;
import model.ApplicationStatus;
import model.Job;
import model.JobStatus;
import storage.ApplicationRepository;
import storage.JobRepository;

/**
 * Owns application rules such as apply-once and closing a job after hiring.
 */
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;

    public ApplicationService(ApplicationRepository applicationRepository, JobRepository jobRepository) {
        this.applicationRepository = applicationRepository;
        this.jobRepository = jobRepository;
    }

    public void applyToJob(String jobId, String applicantName) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new IllegalArgumentException("Job not found."));

        if (job.getStatus() != JobStatus.OPEN) {
            throw new IllegalArgumentException("This job is no longer available.");
        }

        boolean alreadyApplied = applicationRepository.findByJobId(jobId).stream()
                .anyMatch(application -> application.getApplicantName().equals(applicantName));
        if (alreadyApplied) {
            throw new IllegalArgumentException("You have already applied for this job.");
        }

        Application application = new Application(
                UUID.randomUUID().toString(),
                jobId,
                applicantName,
                "",
                ApplicationStatus.PENDING);
        applicationRepository.save(application);
    }

    public List<Application> listApplicationsForJob(String jobId) {
        return applicationRepository.findByJobId(jobId);
    }

    public List<Application> listApplicationsForApplicant(String applicantName) {
        return applicationRepository.findByApplicantName(applicantName);
    }

    public void hireApplicant(String jobId, String applicantName) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new IllegalArgumentException("Job not found."));

        if (job.getStatus() != JobStatus.OPEN) {
            throw new IllegalArgumentException("This job is already closed.");
        }

        List<Application> applications = applicationRepository.findByJobId(jobId);
        if (applications.isEmpty()) {
            throw new IllegalArgumentException("No applicants found for this job.");
        }

        boolean foundApplicant = false;
        List<Application> updatedApplications = new ArrayList<>();
        for (Application application : applications) {
            if (application.getApplicantName().equals(applicantName)) {
                updatedApplications.add(application.withStatus(ApplicationStatus.HIRED));
                foundApplicant = true;
            } else {
                updatedApplications.add(application.withStatus(ApplicationStatus.REJECTED));
            }
        }

        if (!foundApplicant) {
            throw new IllegalArgumentException("Selected applicant not found.");
        }

        applicationRepository.replaceApplicationsForJob(jobId, updatedApplications);
        jobRepository.updateStatus(jobId, JobStatus.CLOSED);
    }
}
