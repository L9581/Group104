import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVStorage {
    private static final String JOB_FILE = "jobs.csv";

    // 写入职位数据到 CSV
    public static void saveJob(Job job) {
        // true 表示追加写入 (append mode)
        try (FileWriter fw = new FileWriter(JOB_FILE, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            
            out.println(job.toCSV());
            System.out.println("职位已成功保存到文件: " + JOB_FILE);
            
        } catch (IOException e) {
            System.err.println("写入文件时出错: " + e.getMessage());
        }
    }

    // 从 CSV 读取所有职位数据
    public static List<Job> loadJobs() {
        List<Job> jobs = new ArrayList<>();
        File file = new File(JOB_FILE);
        
        if (!file.exists()) {
            return jobs; // 如果文件不存在，返回空列表
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 3) {
                    jobs.add(new Job(values[0], values[1], values[2]));
                }
            }
        } catch (IOException e) {
            System.err.println("读取文件时出错: " + e.getMessage());
        }
        return jobs;
    }
}