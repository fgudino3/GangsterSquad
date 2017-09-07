package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Register")
public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RegisterController() {
		super();
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new ServletException(e);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/jsp/Register.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// get user name and password
		String user = request.getParameter("username");
		String email = request.getParameter("email");
		int cin = Integer.parseInt(request.getParameter("cin"));
		String pass = request.getParameter("password");
		String confirm = request.getParameter("confirm");
		
		if (!pass.equals(confirm)) {
			response.sendRedirect("Register");
			return;
		}

		// retrieve data from database
		Connection c = null;
		try {
			// connect
			String url = "jdbc:mysql://localhost/cs3220stu21?useSSL=false";
			String username = "cs3220stu21";
			String password = "#n..Fllf";
			c = DriverManager.getConnection(url, username, password);
			
			// verifies username does not already exist
			String verifyName = "select * from user where username = ?";
			PreparedStatement vName = c.prepareStatement(verifyName);
			vName.setString(1, user);
			ResultSet rs1 = vName.executeQuery();
			
			// verifies cin exists and is not in use by another user
			String sql2 ="select * from cins where cin = ? and is_used = false";
			PreparedStatement p2 = c.prepareStatement(sql2);
			p2.setInt(1, cin);
			ResultSet rs = p2.executeQuery();
			
			if (rs.next() && !rs1.next()) {
				// create user
				String sql = "insert into user (username, email, cin_id, password) values (?, ?, ?, ?)";
				PreparedStatement p = c.prepareStatement(sql);
				p.setString(1, user);
				p.setString(2, email);
				p.setInt(3, rs.getInt("id"));
				p.setString(4, pass);
				p.executeUpdate();
				
				// update cin to used so it cannot be used by a new user
				String used = "update cins set is_used = true where cin = ?";
				PreparedStatement p3 = c.prepareStatement(used);
				p3.setInt(1, cin);
				p3.executeUpdate();
				
			}
			

		} catch (SQLException e) {
			throw new ServletException(e);
		} finally {
			try {
				if (c != null)
					c.close();
			} catch (SQLException e) {
				throw new ServletException(e);
			}
		}

		response.sendRedirect("Login");

	}

}
