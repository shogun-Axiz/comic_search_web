package servlet;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entity.Category;
import entity.Comic;
import service.CategoryService;
import service.ComicService;

/**
 * Servlet implementation class ToComicInfoDeleteServlet
 */
@WebServlet("/toComicInfoDelete")
public class ToComicInfoDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String strComicId = request.getParameter("comicId");

		HttpSession session = request.getSession();

		if ((strComicId == null) || (strComicId.equals(""))) {
			strComicId = (String) session.getAttribute("comicId");
		}

		UUID comicId = UUID.fromString(strComicId);

		session.setAttribute("comicId", comicId);

		ComicService comicService = new ComicService();

		try {
			List<Comic> target = comicService.select(comicId);
			String categoryName = target.get(0).getCategoryName();
			String strReleaseDate = target.get(0).getReleaseDate().toString().replace("-", "/");
			request.setAttribute("categoryName", categoryName);
			request.setAttribute("releaseDate", strReleaseDate);
			request.setAttribute("list", target);

			CategoryService categoryService = new CategoryService();

			List<Category> cat = categoryService.authentication();
			if (cat.size() != 0) {
				request.setAttribute("cat", cat);
				Category category = categoryService.authentication3(categoryName);
				Integer categoryId = category.getCategoryId();
				request.setAttribute("catId", categoryId);
			}
			request.getRequestDispatcher("comicInfoDelete.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();

			// メッセージ設定
			request.setAttribute("msg", "サーバーエラーが発生しました\r\n" +
					"製造元に問い合わせてください");
			// 次画面指定
			request.getRequestDispatcher("comicInfoManagement.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");

		doGet(request, response);
	}

}
