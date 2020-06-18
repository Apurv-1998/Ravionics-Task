import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		String username = request.getParameter("uname");
		String password = request.getParameter("pass");
		
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			Class.forName("com.mysql.jdbc.Driver");
			
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/ravionics_task","root","");
			
			PreparedStatement mainps = con.prepareStatement("select * from logintest where username = ?");
			
			mainps.setString(1, username);
			
			ResultSet rs = mainps.executeQuery();
			
			if(rs.next()) {
				response.sendRedirect("nameError.html");
			}else {
				PreparedStatement ps = con.prepareStatement("insert into logintest (username,password) values (?,?)");
				
				
				ps.setString(1, username);
				ps.setString(2, password);
				
				int i = ps.executeUpdate();
				System.out.println(i);
				
				if(i>0) {
					System.out.println("Registration Successful");
					response.sendRedirect("login.html");
				}else {
					System.out.println("Entering the else part");
					pw.println("Error");
				}
			}
			
		}catch(Exception e) {System.out.println("Entering Catch Block "+e);}
	}

}
