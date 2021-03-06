package servlet;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entity.Comic;
import entity.User;
import service.ComicService;
import service.UserService;

/**
 * Servlet implementation class BookServlet
 */
@WebServlet("/book")
public class BookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();

		UUID userId = (UUID) session.getAttribute("userid");

		UserService userService = new UserService();

		List<User> user = null;

		boolean isSuccess;

		try {
			user = userService.authentication4(userId);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			// メッセージ設定
			request.setAttribute("msg", "サーバーエラーが発生しました\r\n" +
					"製造元に問い合わせてください");

			// 次画面指定
			request.getRequestDispatcher("book.jsp").forward(request, response);
			return;
		}

		Date withdrawalDate = user.get(0).getWithdrawalDate();

		session.setAttribute("withdrawalDate", withdrawalDate);

		if (withdrawalDate != null) {
			request.getRequestDispatcher("index.jsp").forward(request, response);
		} else {
			String strComicId = request.getParameter("comicId");

			UUID comicId = UUID.fromString(strComicId);

			ComicService comicService = new ComicService();

			try {
				List<Comic> target = comicService.select(comicId);
				String releaseDate = target.get(0).getReleaseDate().toString().replace("-", "/");
				isSuccess = true;
				request.setAttribute("list", target);
				request.setAttribute("releaseDate", releaseDate);
				request.setAttribute("isSuccess", isSuccess);
				request.getRequestDispatcher("book.jsp").forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();

				// メッセージ設定
				request.setAttribute("msg", "サーバーエラーが発生しました\r\n" +
						"製造元に問い合わせてください");

				// 次画面指定
				request.getRequestDispatcher("book.jsp").forward(request, response);
				return;
			}
		}

	}

}
