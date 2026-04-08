public class Job {
    private String jobId;
    private String title;
    private String department;

    public Job(String jobId, String title, String department) {
        this.jobId = jobId;
        this.title = title;
        this.department = department;
    }

    // 将对象转换为 CSV 格式的一行数据
    public String toCSV() {
        return jobId + "," + title + "," + department;
    }

    // Getters
    public String getJobId() { return jobId; }
    public String getTitle() { return title; }
    public String getDepartment() { return department; }
    
    @Override
    public String toString() {
        return "Job ID: " + jobId + " | Title: " + title + " | Dept: " + department;
    }
}
