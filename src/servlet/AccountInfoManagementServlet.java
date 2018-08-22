package servlet;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.User;
import service.UserService;

/**
 * Servlet implementation class AccountInfoManagementServlet
 */
@WebServlet("/AccountInfoManagementServlet")
public class AccountInfoManagementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String email = request.getParameter("email");
		String userName = request.getParameter("username");
		String strBirthday = request.getParameter("birthday");
		String strJoinDate = request.getParameter("joindate");

		// 日付の書式を指定する(誕生日)
		Date birthday = null;
		if (!(strBirthday.equals(""))) {
			DateFormat df1 = new SimpleDateFormat("yyyy/MM/dd");
			// 日付解析を厳密に行う設定にする
			df1.setLenient(false);
			try {
				df1.parse(strBirthday);
			} catch (ParseException e) {
				request.setAttribute("msg", "誕生日をyyyy/mm/dd形式で入力してください<br>");
				// 次画面指定
				request.getRequestDispatcher("accountInfoManagement.jsp").forward(request, response);
				return;
			}

			//誕生日をDate型に変換
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd");

			try {
				java.util.Date day1 = sdf1.parse(strBirthday);
				birthday = new java.sql.Date(day1.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		// 日付の書式を指定する(入会日)
		//boy
		Date joinDate = null;
		if ((!(strJoinDate.equals("")))) {
			DateFormat df2 = new SimpleDateFormat("yyyy/MM/dd");
			// 日付解析を厳密に行う設定にする
			df2.setLenient(false);
			try {
				df2.parse(strJoinDate);
			} catch (ParseException e) {
				request.setAttribute("msg", "入会日をyyyy/mm/dd形式で入力してください<br>");
				// 次画面指定
				request.getRequestDispatcher("accountInfoManagement.jsp").forward(request, response);
				return;
			}

			//入会日をDate型に変換
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");

			try {
				java.util.Date day2 = sdf2.parse(strJoinDate);
				joinDate = new java.sql.Date(day2.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		UserService userService = new UserService();

		List<User> list;
		try {
			list = userService.search(email, userName, birthday, joinDate);
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

}
