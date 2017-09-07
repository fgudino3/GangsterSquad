package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User;

@WebServlet("/Login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/jsp/Login.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// get user name and password
		String user = request.getParameter("username");
		String pass = request.getParameter("password");
		boolean isUser = false;

		// retrieve data from database
		Connection c = null;
		try {
			// connect
			String url = "jdbc:mysql://localhost/cs3220stu21?useSSL=false";
			String username = "cs3220stu21";
			String password = "#n..Fllf";
			c = DriverManager.getConnection(url, username, password);

			// verify login
			String sql = "select * from user where username = ? and password = ?";
			PreparedStatement p = c.prepareStatement(sql);
			p.setString(1, user);
			p.setString(2, pass);
			ResultSet rs = p.executeQuery();

			if (rs.next()) {
				User currentUser = new User(rs.getString("username"), rs.getString("email"), rs.getInt("cin"), rs.getString("password"));
				HttpSession session = request.getSession();
				session.setAttribute("user", currentUser);
				isUser = true;
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

		if (isUser) {
			response.sendRedirect("Home");
		} else {
			 response.setContentType("text/html");
			  PrintWriter out = response.getWriter();
			  
			  out.println("<script src='https://code.jquery.com/jquery-3.2.1.min.js'></script>");
			  out.println("<script>");
			  out.println("$(function() {$('#matching').show();});");
			  out.println("</script>");
			  
			  request.getRequestDispatcher("/WEB-INF/jsp/Login.jsp").include(request, response);
			  
//			response.sendRedirect("Login");
		}
	}

}
