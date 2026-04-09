package storage;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import model.Application;
import model.ApplicationStatus;

/**
 * Persists application records to CSV and reloads them when needed.
 */
public class ApplicationRepository {
    private static final String HEADER = "applicationId,jobId,applicantName,note,status";

    private final Path filePath;

    public ApplicationRepository(Path filePath) {
        this.filePath = filePath;
    }

    public void save(Application application) {
        StorageSupport.ensureFileWithHeader(filePath, HEADER);
        StorageSupport.appendLine(filePath, toCsvLine(application));
    }

    public List<Application> findByJobId(String jobId) {
        return findAll().stream()
                .filter(application -> application.getJobId().equals(jobId))
                .collect(Collectors.toList());
    }

    public List<Application> findByApplicantName(String applicantName) {
        return findAll().stream()
                .filter(application -> application.getApplicantName().equals(applicantName))
                .collect(Collectors.toList());
    }

    public void replaceApplicationsForJob(String jobId, List<Application> replacementApplications) {
        List<Application> retained = findAll().stream()
                .filter(application -> !application.getJobId().equals(jobId))
                .collect(Collectors.toCollection(ArrayList::new));
        retained.addAll(replacementApplications);
        rewriteAll(retained);
    }

    private List<Application> findAll() {
        StorageSupport.ensureFileWithHeader(filePath, HEADER);
        List<Application> applications = new ArrayList<>();
        for (String line : StorageSupport.readDataLines(filePath)) {
            List<String> values = CsvUtils.parseLine(line);
            if (values.size() < 5) {
                continue;
            }
            applications.add(new Application(
                    values.get(0),
                    values.get(1),
                    values.get(2),
                    values.get(3),
                    ApplicationStatus.valueOf(values.get(4))));
        }
        return applications;
    }

    private void rewriteAll(List<Application> applications) {
        List<String> lines = new ArrayList<>();
        lines.add(HEADER);
        for (Application application : applications) {
            lines.add(toCsvLine(application));
        }
        StorageSupport.writeAllLines(filePath, lines);
    }

    private String toCsvLine(Application application) {
        return CsvUtils.toCsvLine(
                application.getApplicationId(),
                application.getJobId(),
                application.getApplicantName(),
                application.getNote(),
                application.getStatus().name());
    }
}
