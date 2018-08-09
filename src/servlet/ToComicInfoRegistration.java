package servlet;

import java.io.IOException;
import java.sql.SQLException;
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		CategoryService categoryService = new CategoryService();
		try {
			List<Category> cat = categoryService.authentication();
			boolean isSuccess = cat.size() != 0;
			if(isSuccess == true) {
				request.setAttribute("cat", cat);
				request.getRequestDispatcher("comicInfoRegistration.jsp").forward(request, response);
			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

}
