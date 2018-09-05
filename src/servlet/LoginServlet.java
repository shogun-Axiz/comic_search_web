package servlet;

import java.io.IOException;
import java.sql.Date;
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

		request.setAttribute("email", email);

		String msg = null;

		if ((email == null) || (email.equals("")) || ((password == null) || (password.equals("")))) {
			if ((email == null) || (email.equals(""))) {
				msg = "メールアドレスを入力してください";
			}
			if (((password == null) || (password.equals("")))) {
				if (msg != null) {
					msg = "メールアドレスを入力してください \r\nパスワードを入力してください";
				} else {
					msg = "パスワードを入力してください";
				}
			}

			request.setAttribute("msg", msg);

			request.getRequestDispatcher("index.jsp").forward(request, response);
			return;
		}

		UserService userService = new UserService();
		User user = null;
		try {
			user = userService.authentication(email, password);
		} catch (Exception e) {
			e.printStackTrace();

			request.setAttribute("msg", "サーバーエラーが発生しました\r\n" + "製造元に問い合わせてください");

			request.getRequestDispatcher("index.jsp").forward(request, response);

			return;

		}

		if (user != null) {
			Date withdrawaldate = user.getWithdrawalDate();
			if (withdrawaldate != null) {
				request.setAttribute("msg", "入力されたアカウントは既に退会されております");

				request.getRequestDispatcher("index.jsp").forward(request, response);
			} else {
				boolean adminFlg = user.isAdminFlg();
				String userName = user.getUserName();
				UUID userId = user.getUserId();

				HttpSession session = request.getSession();

				session.setAttribute("userid", userId);
				session.setAttribute("username", userName);
				session.setAttribute("withdrawalDate", withdrawaldate);
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
