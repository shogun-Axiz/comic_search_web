package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ComicSearchServlet
 */
@WebServlet("/comicSearch")
public class ComicSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//aaaaaaa

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String title = request.getParameter("title");
		String authorName = request.getParameter("auhtorName");
		String publisher = request.getParameter("publisher");
		String strCategoryId = request.getParameter("categoryId");
		String strPrice1 = request.getParameter("price1");
		String strPrice2 = request.getParameter("price2");
		String strReleaseDate1 = request.getParameter("releaseDate1");

	}

}
