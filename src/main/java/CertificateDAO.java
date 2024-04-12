import java.sql.*;

public class CertificateDAO {
    private Connection connection;

    public CertificateDAO() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/education","root","root");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveEncryptedCertificate(String studentId, String encryptedData) {
        try {
            String query = "INSERT INTO certificates (student_id, encrypted_data) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, studentId);
            statement.setString(2, encryptedData);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getEncryptedCertificate(String studentId) {
        try {
            String query = "SELECT encrypted_data FROM certificates WHERE student_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, studentId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getString("encrypted_data");
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
