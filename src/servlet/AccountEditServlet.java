package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

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
@WebServlet("/accountEditConfirm")
public class AccountEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");

		String email = request.getParameter("email");
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		String rePassword = request.getParameter("rePassword");
		String strBirthday = request.getParameter("birthday");

		HttpSession session = request.getSession();

		String msg = "";

		//会員ID
		UUID userId = (UUID) session.getAttribute("userid");

		UserService userService = new UserService();

		List<User> user = null;
		try {
			user = userService.authentication4(userId);
		} catch (SQLException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
			msg += "サーバーエラーが発生しました\r\n" +
					"製造元に問い合わせてください<br>";
		}

		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = response.getWriter();

		if ((email == null) || (email.equals(""))) {
			msg += "メールアドレスを入力してください<br>";
		}
		if ((userName == null) || (userName.equals(""))) {
			msg += "ユーザーネームを入力してください<br>";
		}
		if ((email != null) && email.length() > 50) {
			msg += "メールアドレスは50字までです<br>";
		}
		if (userName != null && userName.length() > 50) {
			msg += "ユーザーネームは50字までです<br>";
		}
		if ((strBirthday == null) || (strBirthday.equals(""))) {
			msg += "生年月日を入力してください<br>";
		}

		//メールアドレス判定
		String pattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
		Pattern p = Pattern.compile(pattern);

		if (!(p.matcher(email).find())) {
			msg += "メールアドレスの形式が正しくありません<br>";
		}

		// 日付の書式を指定する
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		// 日付解析を厳密に行う設定にする
		df.setLenient(false);
		try {
			df.parse(strBirthday);
		} catch (ParseException e) {
			msg += "生年月日をyyyy/mm/dd形式で入力してください<br>";
		}
		//パスワード（再入力含む）が空の場合はログインしているユーザーのパスワードを取得する
		if (((password == null) && (rePassword == null)) || ((password.equals("") && rePassword.equals("")))) {
			password = user.get(0).getPassword();
		}else if (password.length() > 20) {
			msg += "パスワードは20字までです<br>";
		}else if (((rePassword == null)) || (rePassword.equals(""))) {
			msg += "パスワード（再入力）を入力してください<br>";
		}else if ((!(password.equals(rePassword)))) {
			msg += "パスワードが一致していません<br>";
		}
		if (msg == "") {
			msg += "success";

			//生年月日をDate型に変換
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			Date birthday = null;
			try {
				java.util.Date day = sdf.parse(strBirthday);
				birthday = new java.sql.Date(day.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}

			//入会日
			Date joinDate = user.get(0).getJoinDate();

			//退会日
			Date withdrawalDate = null;

			//管理者フラグ
			boolean adminFlg = false;

			//更新者
			String modifiedUser = userName;

			//更新日
			Date modifiedDate = new Date(System.currentTimeMillis());

			User updateData = new User(userId, email, userName, password, birthday, joinDate, withdrawalDate, adminFlg,
					modifiedUser, modifiedDate);

			try {
				userService.update(updateData);
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}

		}

		out.print(msg);
	}

}
