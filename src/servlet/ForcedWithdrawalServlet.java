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
 * Servlet implementation class ForcedWithdrawalServlet
 */
@WebServlet("/forcedWithdrawal")
public class ForcedWithdrawalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");

		String strUserId = request.getParameter("userId");

		UUID userId = UUID.fromString(strUserId);

		UserService userService = new UserService();

		List<User> user = null;

		String msg = "";

		try {
			user = userService.authentication4(userId);
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			msg += "サーバーエラーが発生しました\r\n" +
					"製造元に問い合わせてください<br>";
		}

		HttpSession session = request.getSession();

		String email = user.get(0).getEmail();
		String userName = user.get(0).getUserName();
		String password = user.get(0).getPassword();
		Date birthday = user.get(0).getBirthday();
		Date joinDate = user.get(0).getJoinDate();
		Date withdrawalDate = new Date(System.currentTimeMillis());
		boolean isAdminFlg = user.get(0).isAdminFlg();
		String modifiedUser = (String) session.getAttribute("username");
		Date modifiedDate = new Date(System.currentTimeMillis());

		User forcedWithdrawal = new User(userId, email, userName, password, birthday, joinDate, withdrawalDate, isAdminFlg,
				modifiedUser, modifiedDate);

		try {
			userService.forcedWithdrawal(forcedWithdrawal);
			msg += "success";
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			msg += "サーバーエラーが発生しました\r\n" +
					"製造元に問い合わせてください<br>";
		}

		if (msg.equals("success")){
			request.getRequestDispatcher("accountInfoManagement.jsp").forward(request, response);
		} else {
			request.setAttribute("msg", msg);
			request.getRequestDispatcher("accountInfoManagement.jsp").forward(request, response);
		}
	}

}
