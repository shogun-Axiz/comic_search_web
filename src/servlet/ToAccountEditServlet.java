package servlet;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
 * Servlet implementation class AccountEditServlet
 */
@WebServlet("/accountEdit")
public class ToAccountEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();

		UUID userId = (UUID) session.getAttribute("userid");

		UserService userService = new UserService();

		try {
			List<User> user = userService.authentication4(userId);

			String userName = user.get(0).getUserName();
			String email = user.get(0).getEmail();
			String strBirthday = user.get(0).getBirthday().toString();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			Date birthday = null;
			try {
				java.util.Date day = sdf.parse(strBirthday);
				birthday = new java.sql.Date(day.getTime());
				System.out.println(birthday);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			request.setAttribute("username", userName);
			request.setAttribute("email", email);
			request.setAttribute("birthday", birthday);

			request.getRequestDispatcher("accountEdit.jsp").forward(request, response);
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

}
