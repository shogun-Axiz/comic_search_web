package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Comic;
import service.ComicService;

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

		String strComicId = request.getParameter("comicId");

		UUID comicId = UUID.fromString(strComicId);

		System.out.println(comicId);

		ComicService comicService = new ComicService();

		try {
			List<Comic> target = comicService.select(comicId);
			request.setAttribute("list", target);
			request.getRequestDispatcher("book.jsp").forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();

			// メッセージ設定
			request.setAttribute("msg", "サーバーエラーが発生しました\r\n" +
					"製造元に問い合わせてください");

			// 次画面指定
			request.getRequestDispatcher("book.jsp").forward(request, response);
		}
	}

}
