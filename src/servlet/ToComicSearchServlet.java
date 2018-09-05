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

import entity.Category;
import entity.User;
import service.CategoryService;
import service.UserService;

/**
 * Servlet implementation class ToComicSearchServlet
 */
@WebServlet("/toComicSearch")
public class ToComicSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		//退会チェック
		HttpSession session = request.getSession();

		UUID userId = (UUID) session.getAttribute("userid");

		UserService userService = new UserService();

		List<User> user = null;

		try {
			user = userService.authentication4(userId);
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return;
		}

		Date withdrawalDate = user.get(0).getWithdrawalDate();

		session.setAttribute("withdrawalDate", withdrawalDate);

		if (withdrawalDate != null) {
			request.getRequestDispatcher("index.jsp").forward(request, response);
		} else {
			//カテゴリーデータ
			CategoryService categoryService = new CategoryService();
			try {
				List<Category> cat = categoryService.authentication();
				if(cat.size() != 0) {
					request.setAttribute("cat", cat);
					request.getRequestDispatcher("comicSearch.jsp").forward(request, response);
				}
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
				return;
			}
		}


	}

}
