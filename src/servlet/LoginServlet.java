package servlet;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entity.User;
import service.UserService;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String email = request.getParameter("email");
		String password = request.getParameter("password");

		if ((email == null) || (email.equals(""))) {
			request.setAttribute("msg", "メールアドレスを入力してください");

			request.getRequestDispatcher("index.jsp").forward(request, response);
			return;
		} else if ((password == null) || (password.equals(""))) {
			request.setAttribute("msg", "パスワードを入力してください");

			request.getRequestDispatcher("index.jsp").forward(request, response);
			return;
		}

		UserService userService = new UserService();
		User user = null;
		try {
			user = userService.authentication(email, password);
		} catch (SQLException e) {
			e.printStackTrace();

			request.setAttribute("msg", "サーバーエラーが発生しました\r\n" + "製造元に問い合わせてください");

			request.getRequestDispatcher("index.jsp").forward(request, response);

			return;

		}
		boolean isSuccess = (user != null);

		if (isSuccess) {
			Date withdrawaldate = user.getWithdrawalDate();
			if (withdrawaldate != null) {
				request.setAttribute("msg", "入力されたアカウントは既に退会されております");

				request.getRequestDispatcher("index.jsp").forward(request, response);
			} else {
				boolean adminFlg = user.isAdminFlg();
				String userName = user.getUserName();

				HttpSession session = request.getSession();
				session.setAttribute("username", userName);
				if (adminFlg == true) {
					request.getRequestDispatcher("adminTop.jsp").forward(request, response);
				} else {
					request.getRequestDispatcher("userTop.jsp").forward(request, response);
				}
			}
		} else {
			request.setAttribute("msg", "メールアドレスあるいはパスワードが間違っています");

			request.getRequestDispatcher("index.jsp").forward(request, response);

		}
	}

}
