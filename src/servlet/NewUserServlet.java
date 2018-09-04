package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.User;
import service.UserService;
import util.ConversionDate;

/**
 * Servlet implementation class NewUser
 */
@WebServlet("/newUser")
public class NewUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String email = request.getParameter("email");
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		String rePassword = request.getParameter("rePassword");
		String strBirthday = request.getParameter("birthday");

		String msg = "";

		UserService userService = new UserService();

		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = response.getWriter();

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
				User user = null;
				try {
					user = userService.authentication2(email);
				} catch (Exception e) {
					e.printStackTrace();
					msg += "サーバーエラーが発生しました" +
							" 製造元に問い合わせてください\r\n";
				}
				String exEmail = user.getEmail();
				if (email.equals(exEmail)) {
					msg += "このメールアドレスは既に登録済みです\r" +
							"別のメールアドレスを入力してください\r\n";
				}
			}
		}
		if ((userName == null) || (userName.equals(""))) {
			msg += "ユーザーネームを入力してください\r\n";
		} else if (userName != null && userName.length() > 50) {
			msg += "ユーザーネームは50字までです\r\n";
		}
		if ((password == null) || (password.equals(""))) {
			msg += "パスワードを入力してください\r\n";
		} else if (password != null && password.length() > 20) {
			msg += "パスワードは20字までです\r\n";
		} else if ((rePassword == null) || (rePassword.equals(""))) {
			msg += "パスワード（再入力）を入力してください\r\n";
		} else if (!(password.equals(rePassword))) {
			msg += "パスワードが一致していません\r\n";
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

			//4項目以外の情報を取得(not null制約でない変数についてはnullに設定)
			//会員ID
			UUID userId = UUID.randomUUID();

			//入会日
			Date joinDate = new Date(System.currentTimeMillis());

			//退会日
			Date withdrawalDate = null;

			//管理者フラグ
			boolean adminFlg = false;

			//更新者
			String modifiedUser = null;

			//更新日
			Date modifiedDate = null;

			User regist = new User(userId, email, userName, password, birthday, joinDate, withdrawalDate, adminFlg,
					modifiedUser, modifiedDate);

			try {
				userService.registration(regist);
			} catch (Exception e) {
				e.printStackTrace();
				msg += "サーバーエラーが発生しました" +
						" 製造元に問い合わせてください\r\n";
			}

		}

		out.print(msg);

	}

}
