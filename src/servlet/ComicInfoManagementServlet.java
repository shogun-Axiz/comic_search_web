package servlet;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

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
import util.ConversionDate;

/**
 * Servlet implementation class ComicInfoManagementServlet
 */
@WebServlet("/comicInfoManagement")
public class ComicInfoManagementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();

		String title = request.getParameter("title");
		String authorName = request.getParameter("authorName");
		String publisher = request.getParameter("publisher");
		String strCategoryId = request.getParameter("categoryId");
		String strPrice1 = request.getParameter("price1");
		String strPrice2 = request.getParameter("price2");
		String strReleaseDate1 = request.getParameter("releaseDate1");
		String strReleaseDate2 = request.getParameter("releaseDate2");

		request.setAttribute("title", title);
		request.setAttribute("authorName", authorName);
		request.setAttribute("publisher", publisher);
		request.setAttribute("strCategoryId", strCategoryId);
		request.setAttribute("strPrice1", strPrice1);
		request.setAttribute("strPrice2", strPrice2);
		request.setAttribute("strReleaseDate1", strReleaseDate1);
		request.setAttribute("strReleaseDate2", strReleaseDate2);

		Integer categoryId = Integer.parseInt(strCategoryId);
		Integer price1 = Integer.parseInt(strPrice1);
		Integer price2 = Integer.parseInt(strPrice2);

		ConversionDate cond = new ConversionDate();

		Date releaseDate1 = null;
		Date releaseDate2 = null;

		try {
			// 日付の書式を指定する(発売日・左)
			releaseDate1 = cond.conversion(strReleaseDate1);

			// 日付の書式を指定する(発売日・右)
			releaseDate2 = cond.conversion(strReleaseDate2);
		}catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "発売日をyyyy/mm/dd形式で入力してください<br>");
			// 次画面指定
			request.getRequestDispatcher("toComicInfoManagement").forward(request, response);
			return;
		}

		ComicService comicService = new ComicService();

		List<Comic> list;
		try {
			list = comicService.authentication(title, authorName, publisher, categoryId, price1, price2,
					releaseDate1, releaseDate2);
			session.setAttribute("list", list);

			if (list.size() != 0) {

				request.setAttribute("isSuccess", true);

				CategoryService categoryService = new CategoryService();

				List<Category> cat = categoryService.authentication();
				boolean isSuccess2 = cat.size() != 0;
				if (isSuccess2 == true) {
					request.setAttribute("cat", cat);
					request.getRequestDispatcher("toComicInfoManagement").forward(request, response);
				}

			} else {
				// メッセージ設定
				request.setAttribute("msg", "入力した条件に一致するデータが見つかりませんでした");

				// 次画面指定
				request.getRequestDispatcher("toComicInfoManagement").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "サーバーエラーが発生しました\r\n" +
					"製造元に問い合わせてください");
			// 次画面指定
			request.getRequestDispatcher("comicInfoManagement.jsp").forward(request, response);
			return;
		}
	}

}
