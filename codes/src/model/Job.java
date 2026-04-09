package model;

/**
 * Represents a posted TA job.
 */
public class Job {
    private final String jobId;
    private final String jobName;
    private final String postedBy;
    private final JobStatus status;

    public Job(String jobId, String jobName, String postedBy, JobStatus status) {
        this.jobId = jobId;
        this.jobName = jobName;
        this.postedBy = postedBy;
        this.status = status;
    }

    public String getJobId() {
        return jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public JobStatus getStatus() {
        return status;
    }

    public Job withStatus(JobStatus newStatus) {
        return new Job(jobId, jobName, postedBy, newStatus);
    }
}
