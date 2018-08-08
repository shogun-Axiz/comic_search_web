package servlet;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entity.User;
import service.UserService;

/**
 * Servlet implementation class AccountEditServlet
 */
@WebServlet("/accountEdit")
public class ToAccountEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();

		String userName = (String) session.getAttribute("username");
		System.out.println(userName);

		UserService userService = new UserService();

		try {
			List<User> user = userService.authentication4(userName);

			String email = user.get(0).getEmail();
			System.out.println(email);
			Date birthday = user.get(0).getBirthday();
			System.out.println(birthday);

			request.setAttribute("email", email);
			request.setAttribute("birthday", birthday);

			request.setAttribute("username", userName);

			request.getRequestDispatcher("accountEdit.jsp").forward(request, response);
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

}
