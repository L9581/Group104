import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class DataManager {
    private static final String FILE_PATH = "jobs.csv"; // 也可以按需改为 txt

    // 保存职位信息到本地文本文件
    public static boolean saveJob(String title, String dept, String desc, String skills, String hours, String rate) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            // 将字段用逗号分隔，并处理可能的换行符
            String cleanDesc = desc.replace("\n", " ").replace(",", "，");
            String record = String.join(",", title, dept, cleanDesc, skills, hours, rate);
            
            writer.write(record);
            writer.newLine();
            return true;
        } catch (IOException e) {
            System.err.println("文件写入失败: " + e.getMessage());
            return false;
        }
    }
}