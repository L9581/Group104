package storage;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import model.Job;
import model.JobStatus;

/**
 * Persists job records and their open/closed state in CSV.
 */
public class JobRepository {
    private static final String HEADER = "jobId,jobName,postedBy,status";

    private final Path filePath;

    public JobRepository(Path filePath) {
        this.filePath = filePath;
    }

    public void save(Job job) {
        StorageSupport.ensureFileWithHeader(filePath, HEADER);
        StorageSupport.appendLine(filePath, toCsvLine(job));
    }

    public Optional<Job> findById(String jobId) {
        return findAll().stream()
                .filter(job -> job.getJobId().equals(jobId))
                .findFirst();
    }

    public List<Job> findAll() {
        StorageSupport.ensureFileWithHeader(filePath, HEADER);
        List<Job> jobs = new ArrayList<>();
        for (String line : StorageSupport.readDataLines(filePath)) {
            List<String> values = CsvUtils.parseLine(line);
            if (values.size() < 4) {
                continue;
            }
            jobs.add(new Job(
                    values.get(0),
                    values.get(1),
                    values.get(2),
                    JobStatus.valueOf(values.get(3))));
        }
        return jobs;
    }

    public List<Job> findByStatus(JobStatus status) {
        return findAll().stream()
                .filter(job -> job.getStatus() == status)
                .collect(Collectors.toList());
    }

    public List<Job> findByPostedBy(String postedBy) {
        return findAll().stream()
                .filter(job -> job.getPostedBy().equals(postedBy))
                .collect(Collectors.toList());
    }

    public void updateStatus(String jobId, JobStatus newStatus) {
        List<Job> jobs = new ArrayList<>();
        boolean found = false;

        for (Job job : findAll()) {
            if (job.getJobId().equals(jobId)) {
                jobs.add(job.withStatus(newStatus));
                found = true;
            } else {
                jobs.add(job);
            }
        }

        if (!found) {
            throw new IllegalArgumentException("Job not found.");
        }

        rewriteAll(jobs);
    }

    private void rewriteAll(List<Job> jobs) {
        List<String> lines = new ArrayList<>();
        lines.add(HEADER);
        for (Job job : jobs) {
            lines.add(toCsvLine(job));
        }
        StorageSupport.writeAllLines(filePath, lines);
    }

    private String toCsvLine(Job job) {
        return CsvUtils.toCsvLine(
                job.getJobId(),
                job.getJobName(),
                job.getPostedBy(),
                job.getStatus().name());
    }
}
