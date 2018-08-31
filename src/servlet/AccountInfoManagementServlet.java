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
import util.ConversionDate;

/**
 * Servlet implementation class AccountInfoManagementServlet
 */
@WebServlet("/accountInfoManagement")
public class AccountInfoManagementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();

		String email = request.getParameter("email");
		String userName = request.getParameter("username");
		String strBirthday = request.getParameter("birthday");
		String strJoinDate = request.getParameter("joinDate");

		request.setAttribute("email", email);
		request.setAttribute("userName", userName);
		request.setAttribute("birthday", strBirthday);
		request.setAttribute("joinDate", strJoinDate);

		ConversionDate cond = new ConversionDate();

		// 日付の書式を指定する(誕生日)
		Date birthday = null;
		try {
			birthday = cond.conversion(strBirthday);
		}catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "誕生日をyyyy/mm/dd形式で入力してください<br>");
			// 次画面指定
			request.getRequestDispatcher("accountInfoManagement.jsp").forward(request, response);
			return;
		}

		// 日付の書式を指定する(入会日)
		Date joinDate = null;
		try {
			joinDate = cond.conversion(strJoinDate);
		}catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "入会日をyyyy/mm/dd形式で入力してください<br>");
			// 次画面指定
			request.getRequestDispatcher("accountInfoManagement.jsp").forward(request, response);
			return;
		}

		UserService userService = new UserService();

		List<User> list = null;
		try {
			list = userService.search(email, userName, birthday, joinDate);
		}catch(SQLException e) {
			e.printStackTrace();
		}

		session.setAttribute("list", list);

		if (list.size() != 0) {

			request.setAttribute("isSuccess", true);
			request.getRequestDispatcher("accountInfoManagement.jsp").forward(request, response);

		} else {
			// メッセージ設定
			request.setAttribute("msg", "入力した条件に一致するデータが見つかりませんでした");

			// 次画面指定
			request.getRequestDispatcher("accountInfoManagement.jsp").forward(request, response);
		}
	}

}
