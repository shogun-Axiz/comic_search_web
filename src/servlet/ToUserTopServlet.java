package servlet;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entity.User;
import service.UserService;

/**
 * Servlet implementation class ToUserTopServlet
 */
@WebServlet("/toUserTop")
public class ToUserTopServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();

		UUID userId = (UUID) session.getAttribute("userid");

		UserService userService = new UserService();

		List<User> user = null;

		try {
			user = userService.authentication4(userId);
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		Date withdrawalDate = user.get(0).getWithdrawalDate();

		session.setAttribute("withdrawalDate", withdrawalDate);

		if (withdrawalDate != null) {
			request.getRequestDispatcher("index.jsp").forward(request, response);
		} else {
			request.getRequestDispatcher("userTop.jsp").forward(request, response);
		}

	}

}
