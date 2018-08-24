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
			}else {
				UserService userService = new UserService();
				try {
					List<User> user = userService.find();
					for(int i = 0; i < user.size(); i++) {
						String exEmail = user.get(i).getEmail();
						if(email.equals(exEmail)) {
							msg += "このメールアドレスは既に登録済みです\r\n" +
									"別のメールアドレスを入力してください<br>";
						}
					}
				} catch (SQLException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
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
		}
		if ((rePassword == null) || (rePassword.equals(""))) {
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
				msg += "誕生日をyyyy/mm/dd形式で入力してください<br>";
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

			UserService userService = new UserService();

			try {
				userService.registration(regist);
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}

		}

		out.print(msg);

	}

}
