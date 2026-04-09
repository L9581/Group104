package model;

/**
 * Represents one TA application to one job.
 */
public class Application {
    private final String applicationId;
    private final String jobId;
    private final String applicantName;
    private final String note;
    private final ApplicationStatus status;

    public Application(String applicationId, String jobId, String applicantName, String note,
            ApplicationStatus status) {
        this.applicationId = applicationId;
        this.jobId = jobId;
        this.applicantName = applicantName;
        this.note = note;
        this.status = status;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public String getJobId() {
        return jobId;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public String getNote() {
        return note;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public Application withStatus(ApplicationStatus newStatus) {
        return new Application(applicationId, jobId, applicantName, note, newStatus);
    }
}
