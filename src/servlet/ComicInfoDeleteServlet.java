package servlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entity.Comic;
import service.ComicService;

/**
 * Servlet implementation class ComicInfoDeleteServlet
 */
@WebServlet("/comicInfoDelete")
public class ComicInfoDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();

		UUID comicId = (UUID) session.getAttribute("comicId");

		String msg = "";

		ComicService comicService = new ComicService();

		List<Comic> comic = null;
		try {
			comic = comicService.select(comicId);
		} catch (SQLException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
			msg += "サーバーエラーが発生しました\r\n" +
					"製造元に問い合わせてください<br>";
		}

		String fileName = comic.get(0).getImage();

		String spa = FileSystems.getDefault().getSeparator();

		String extension = null;
		Path targetPath = null;

		try {
			int position = fileName.lastIndexOf(".");
			if (position != -1) {
				extension = fileName.substring(position + 1);
			}
			File file = new File(
					"../workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/comic_search_web/img");
			String Path = file.getAbsolutePath();
			targetPath = Paths.get(Path + spa + comicId + "." + extension);
			Files.deleteIfExists(targetPath);
		} catch (IOException e) {
			e.printStackTrace();
			msg += "サーバーエラーが発生しました\r\n" +
					"製造元に問い合わせてください<br>";
		}

		try {
			comicService.delete(comicId);
			msg += "success";
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			msg += "サーバーエラーが発生しました\r\n" +
					"製造元に問い合わせてください";
		}

		if (msg.equals("success")){
			request.getRequestDispatcher("toComicInfoManagement").forward(request, response);
		} else {
			String strComicId = String.valueOf(comicId);
			session.setAttribute("comicId", strComicId);
			request.setAttribute("msg", msg);
			request.getRequestDispatcher("toComicInfoDelete").forward(request, response);
		}
	}
}
