package app;

import java.nio.file.Path;
import java.nio.file.Paths;

import model.User;
import service.ApplicationService;
import service.AuthService;
import service.JobService;
import service.RuntimeDataResetService;
import storage.ApplicationRepository;
import storage.JobRepository;
import storage.UserRepository;

/**
 * Wires repositories and services together and stores the current logged-in user.
 */
public class AppContext {
    private final AuthService authService;
    private final JobService jobService;
    private final ApplicationService applicationService;
    private final RuntimeDataResetService runtimeDataResetService;
    private User currentUser;

    public AppContext() {
        Path dataDirectory = Paths.get("codes", "data");
        UserRepository userRepository = new UserRepository(dataDirectory.resolve("users.csv"));
        JobRepository jobRepository = new JobRepository(dataDirectory.resolve("jobs.csv"));
        ApplicationRepository applicationRepository =
                new ApplicationRepository(dataDirectory.resolve("applications.csv"));

        this.authService = new AuthService(userRepository);
        this.jobService = new JobService(jobRepository);
        this.applicationService = new ApplicationService(applicationRepository, jobRepository);
        this.runtimeDataResetService = new RuntimeDataResetService(dataDirectory);
    }

    public AuthService getAuthService() {
        return authService;
    }

    public JobService getJobService() {
        return jobService;
    }

    public ApplicationService getApplicationService() {
        return applicationService;
    }

    public RuntimeDataResetService getRuntimeDataResetService() {
        return runtimeDataResetService;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
