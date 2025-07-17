import java.sql.*;

public class StudentMarklistApp {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/studentdb"; // Your DB name
        String user = "root"; // DB user
        String password = "yourpassword"; // DB password

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println(" Connected to database.");

            Statement stmt = conn.createStatement();

            // Create table if not exists
            String createTable = "CREATE TABLE IF NOT EXISTS marklist (" +
                    "roll_no INT PRIMARY KEY, " +
                    "name VARCHAR(100), " +
                    "math INT, " +
                    "science INT, " +
                    "english INT, " +
                    "total INT, " +
                    "average FLOAT)";
            stmt.executeUpdate(createTable);
            System.out.println(" Table 'marklist' is ready.");

            // Insert student record
            int roll = 101;
            String name = "John Doe";
            int math = 90, science = 80, english = 85;
            int total = math + science + english;
            float average = total / 3.0f;

            String insertSQL = "INSERT INTO marklist (roll_no, name, math, science, english, total, average) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(insertSQL);
            ps.setInt(1, roll);
            ps.setString(2, name);
            ps.setInt(3, math);
            ps.setInt(4, science);
            ps.setInt(5, english);
            ps.setInt(6, total);
            ps.setFloat(7, average);

            ps.executeUpdate();
            System.out.println(" Student marklist inserted.");

            // Retrieve and display records
            ResultSet rs = stmt.executeQuery("SELECT * FROM marklist");

            System.out.println("\n📋 STUDENT MARKLIST:");
            System.out.println("Roll | Name       | Math | Sci | Eng | Total | Avg");
            System.out.println("--------------------------------------------------");
            while (rs.next()) {
                System.out.printf("%4d | %-10s | %4d | %3d | %3d | %5d | %.2f\n",
                        rs.getInt("roll_no"),
                        rs.getString("name"),
                        rs.getInt("math"),
                        rs.getInt("science"),
                        rs.getInt("english"),
                        rs.getInt("total"),
                        rs.getFloat("average"));
            }

            // Close resources
            rs.close();
            ps.close();
            stmt.close();
            conn.close();
            System.out.println(" Done and disconnected.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
