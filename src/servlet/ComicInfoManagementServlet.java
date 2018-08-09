package servlet;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

/**
 * Servlet implementation class ComicInfoManagementServlet
 */
@WebServlet("/comicInfoManagement")
public class ComicInfoManagementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		boolean isSuccess = false;

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

		// 日付の書式を指定する(発売日・左)
		Date releaseDate1 = null;
		if (!(strReleaseDate1.equals(""))) {
			DateFormat df1 = new SimpleDateFormat("yyyy/MM/dd");
			// 日付解析を厳密に行う設定にする
			df1.setLenient(false);
			try {
				df1.parse(strReleaseDate1);
			} catch (ParseException e) {
				request.setAttribute("msg", "発売日をyyyy/mm/dd形式で入力してください<br>");
				// 次画面指定
				request.getRequestDispatcher("comicInfoManagement.jsp").forward(request, response);
				return;
			}

			//発売日・左をDate型に変換
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd");

			try {
				java.util.Date day1 = sdf1.parse(strReleaseDate1);
				releaseDate1 = new java.sql.Date(day1.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		// 日付の書式を指定する(発売日・右)
		//boy
		Date releaseDate2 = null;
		if ((!(strReleaseDate2.equals("")))) {
			DateFormat df2 = new SimpleDateFormat("yyyy/MM/dd");
			// 日付解析を厳密に行う設定にする
			df2.setLenient(false);
			try {
				df2.parse(strReleaseDate2);
			} catch (ParseException e) {
				request.setAttribute("msg", "発売日をyyyy/mm/dd形式で入力してください<br>");
				// 次画面指定
				request.getRequestDispatcher("comicInfoManagement.jsp").forward(request, response);
				return;
			}

			//発売日・をDate型に変換
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");

			try {
				java.util.Date day2 = sdf2.parse(strReleaseDate2);
				releaseDate2 = new java.sql.Date(day2.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		ComicService comicService = new ComicService();

		List<Comic> list;
		try {
			list = comicService.authentication(title, authorName, publisher, categoryId, price1, price2,
					releaseDate1, releaseDate2);
			session.setAttribute("list", list);

			if (list.size() != 0) {
				isSuccess = true;
			}
			if (isSuccess == true) {

				request.setAttribute("isSuccess", isSuccess);

				CategoryService categoryService = new CategoryService();
				try {
					List<Category> cat = categoryService.authentication();
					boolean isSuccess2 = cat.size() != 0;
					if(isSuccess2 == true) {
						request.setAttribute("cat", cat);
						request.getRequestDispatcher("comicInfoManagement.jsp").forward(request, response);
					}
				} catch (SQLException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}

			} else {
				// メッセージ設定
				request.setAttribute("msg", "入力した条件に一致するデータが見つかりませんでした");

				// 次画面指定
				request.getRequestDispatcher("comicInfoManagement.jsp").forward(request, response);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
