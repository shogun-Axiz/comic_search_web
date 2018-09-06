package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ToIndexServlet
 */
@WebServlet("/toIndex")
public class ToIndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");

		String email = request.getParameter("email");
		try {
			request.setAttribute("email", email);
		}catch(Exception e) {
			request.setAttribute("msg",  "サーバーエラーが発生しました\r\n" +
					"製造元に問い合わせてください\r\n");
		}

		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

}
