package model;

/**
 * Minimal application status set used by the current workflow.
 */
public enum ApplicationStatus {
    PENDING("Pending"),
    HIRED("Accepted"),
    REJECTED("Rejected");

    private final String displayText;

    ApplicationStatus(String displayText) {
        this.displayText = displayText;
    }

    public String toDisplayText() {
        return displayText;
    }
}
