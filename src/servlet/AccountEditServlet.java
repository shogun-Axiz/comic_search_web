package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
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
import util.ConversionDate;

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

		//退会チェック
		Date withdrawalDate = user.get(0).getWithdrawalDate();
		if (withdrawalDate != null) {
			msg += "withdrawal";
		}

		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = response.getWriter();

		if ((email == null) || (email.equals(""))) {
			msg += "メールアドレスを入力してください<br>";
		} else if ((email != null) && email.length() > 50) {
			msg += "メールアドレスは50字までです<br>";
		} else {
			//メールアドレス判定
			String pattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
			Pattern p = Pattern.compile(pattern);

			if (!(p.matcher(email).find())) {
				msg += "メールアドレスの形式が正しくありません<br>";
			} else {
				try {
					User user2 = userService.authentication2(email);
					String exEmail = user2.getEmail();
					if (email.equals(exEmail)) {
						msg += "このメールアドレスは既に登録済みです\r\n" +
								"別のメールアドレスを入力してください<br>";
					}
				} catch (SQLException e) {
					msg += "サーバーエラーが発生しました\r\n" +
							"製造元に問い合わせてください<br>";
				}
			}
		}
		if ((userName == null) || (userName.equals(""))) {
			msg += "ユーザーネームを入力してください<br>";
		} else if (userName != null && userName.length() > 50) {
			msg += "ユーザーネームは50字までです<br>";
		}
		if ((password == null) || (password.equals(""))) {
			msg += "パスワードを入力してください<br>";
		} else if (password != null && password.length() > 20) {
			msg += "パスワードは20字までです<br>";
		} else if ((rePassword == null) || (rePassword.equals(""))) {
			msg += "パスワード（再入力）を入力してください<br>";
		} else if (!(password.equals(rePassword))) {
			msg += "パスワードが一致していません<br>";
		}

		Date birthday = null;

		if ((strBirthday == null) || (strBirthday.equals(""))) {
			msg += "生年月日を入力してください<br>";
		} else {
			ConversionDate cond = new ConversionDate();

			// 日付の書式を指定する(誕生日)
			try {
				birthday = cond.conversion(strBirthday);
			} catch (Exception e) {
				msg += "生年月日をyyyy/mm/dd形式で入力してください<br>";
			}
		}
		if (msg == "") {
			msg += "success";

			//入会日
			Date joinDate = user.get(0).getJoinDate();

			//退会日
			withdrawalDate = null;

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
