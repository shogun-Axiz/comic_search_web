package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
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

		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = response.getWriter();

		List<User> user = null;
		try {
			user = userService.authentication4(userId);
		} catch (Exception e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
			msg += "サーバーエラーが発生しました\r\n" +
					"製造元に問い合わせてください\r\n";
			out.print(msg);
			return;
		}

		//退会チェック
		Date withdrawalDate = user.get(0).getWithdrawalDate();
		if (withdrawalDate != null) {
			msg += "withdrawal";
		}

		if ((email == null) || (email.equals(""))) {
			msg += "メールアドレスを入力してください\r\n";
		} else if ((email != null) && email.length() > 50) {
			msg += "メールアドレスは50字までです\r\n";
		} else {
			//メールアドレス判定
			String pattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
			Pattern p = Pattern.compile(pattern);

			if (!(p.matcher(email).find())) {
				msg += "メールアドレスの形式が正しくありません\r\n";
			} else {
				List<User> searchEmail = null;
				try {
					searchEmail = userService.authentication5(userId, email);
					if (searchEmail.size() != 0) {
						msg += "このメールアドレスは既に登録済みです\r\n" +
								"別のメールアドレスを入力してください\r\n";
					}
				} catch (Exception e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
					msg += "サーバーエラーが発生しました\r\n" +
							"製造元に問い合わせてください\r\n";
				}

			}
		}
		if ((userName == null) || (userName.equals(""))) {
			msg += "ユーザーネームを入力してください\r\n";
		} else if (userName != null && userName.length() > 50) {
			msg += "ユーザーネームは50字までです\r\n";
		}

		if ((password != null) && (!(password.equals("")))) {
			if (password.length() > 20) {
				msg += "パスワードは20字までです\r\n";
			} else if ((rePassword == null) || (rePassword.equals(""))) {
				msg += "パスワード（再入力）を入力してください\r\n";
			} else if ((!(password.equals(rePassword)))) {
				msg += "パスワードが一致していません\r\n";
			}
		} else {
			if ((((rePassword != null)) && (!(rePassword.equals(""))))) {
				msg += "パスワードを入力してください\r\n";
			} else {
				password = user.get(0).getPassword();
			}
		}

		Date birthday = null;

		if ((strBirthday == null) || (strBirthday.equals(""))) {
			msg += "生年月日を入力してください\r\n";
		} else {
			ConversionDate cond = new ConversionDate();

			// 日付の書式を指定する(誕生日)
			try {
				birthday = cond.conversion(strBirthday);
			} catch (Exception e) {
				e.printStackTrace();
				msg += "生年月日をyyyy/mm/dd形式で入力してください\r\n";
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
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
				msg += "サーバーエラーが発生しました\r\n" +
						"製造元に問い合わせてください\r\n";
			}

		}
		out.print(msg);
	}

}
