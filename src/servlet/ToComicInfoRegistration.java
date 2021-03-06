package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Category;
import service.CategoryService;

/**
 * Servlet implementation class ToComicInfoRegistration
 */
@WebServlet("/toComicInfoRegistration")
public class ToComicInfoRegistration extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		CategoryService categoryService = new CategoryService();
		try {
			List<Category> cat = categoryService.authentication();
			if (cat.size() != 0) {
				request.setAttribute("cat", cat);
			}
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			request.setAttribute("msg", "サーバーエラーが発生しました\r\n" +
					"製造元に問い合わせてください\r\n");

			// 次画面指定
			request.getRequestDispatcher("index.jsp").forward(request, response);
			return;
		}
		request.getRequestDispatcher("comicInfoRegistration.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");

		doGet(request, response);
	}

}
