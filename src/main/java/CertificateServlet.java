
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import javax.crypto.SecretKey;

@WebServlet("/CertificateServlet")
public class CertificateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private CertificateDAO certificateDAO;
	    private SecretKey key;

	    @Override
	    public void init() throws ServletException {
	        certificateDAO = new CertificateDAO();
	        try {
	            key = AESUtil.generateKey();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	 @Override
	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        String studentId = request.getParameter("studentId");
	        response.setContentType("text/plain");
	        PrintWriter out = response.getWriter();

	        try {
	            String encryptedData = certificateDAO.getEncryptedCertificate(studentId);
	            if (encryptedData != null) {
	                String decryptedData = AESUtil.decrypt(encryptedData, key);
	                out.println(decryptedData);
	            } else {
	                out.println("No data found for student ID: " + studentId);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            out.println("Error fetching data for student ID: " + studentId);
	        }
	    }

	 @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        String studentId = request.getParameter("studentId");
	        String certificateData = request.getParameter("certificateData");
	        response.setContentType("text/plain");
	        PrintWriter out = response.getWriter();

	        try {
	            String encryptedData = AESUtil.encrypt(certificateData, key);
	            certificateDAO.saveEncryptedCertificate(studentId, encryptedData);
	            out.println("Certificate data saved for student ID: " + studentId);
	        } catch (Exception e) {
	            e.printStackTrace();
	            out.println("Error saving certificate data for student ID: " + studentId);
	        }
	    }

}
