package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ComicInfoRegistrationServlet
 */
@WebServlet("/ComicInfoRegistrationServlet")
public class ComicInfoRegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");

		String title = request.getParameter("title");
		String strCategoryId = request.getParameter("category");
		String authorName = request.getParameter("authorName");
		String strPrice = request.getParameter("price");
		String strReleaseDate = request.getParameter("releaseDate");
		String publisher = request.getParameter("publisher");
		String synopsis = request.getParameter("synopsis");
		String link = request.getParameter("link");
		String pic = request.getParameter("pic");

	}

}
