package servlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

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
			msg += "0.サーバーエラーが発生しました\r\n" +
					"製造元に問い合わせてください<br>";
		}

		System.out.println("this1");

		String fileName = null;

		try {
			Part part = request.getPart(comic.get(0).getImage());
			System.out.println(part.toString());
			fileName = extractFileName(part);
			System.out.println(fileName);
			File file = new File("C:\\tmp\\img\\" + fileName);
			System.out.println(file.delete());
			if (!(fileName.isEmpty())) {
				part.write("C:\\tmp\\img\\" + fileName);
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg += "0.サーバーエラーが発生しました\r\n" +
					"製造元に問い合わせてください<br>";
		}

		System.out.println("this2");

		// 8/21 上記まで編集

		String spa = FileSystems.getDefault().getSeparator();

		String extension = null;
		Path sourcePath = null;
		Path targetPath = null;

		String pic = null;

		if (!(fileName.isEmpty())) {
			try {
				sourcePath = Paths.get("C:\\tmp\\img\\" + fileName);
				int position = fileName.lastIndexOf(".");
				if (position != -1) {
					extension = fileName.substring(position + 1);
				}
				File file = new File(
						"../workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/comic_search_web/img");
				String Path = file.getAbsolutePath();
				targetPath = Paths.get(Path + spa + comicId + "." + extension);
				Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
				pic = "img/" + comicId + "." + extension;
			} catch (IOException e) {
				e.printStackTrace();
				msg += "2.サーバーエラーが発生しました\r\n" +
						"製造元に問い合わせてください<br>";
			}
		}

		try {
			sourcePath = Paths.get("C:\\tmp\\img\\" + fileName);
			int position = fileName.lastIndexOf(".");
			if (position != -1) {
				extension = fileName.substring(position + 1);
			}
			File file = new File(
					"../workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/comic_search_web/img");
			String Path = file.getAbsolutePath();
			targetPath = Paths.get(Path + spa + comicId + "." + extension);
			Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
			pic = "img/" + comicId + "." + extension;
		} catch (IOException e) {
			e.printStackTrace();
			msg += "3.サーバーエラーが発生しました\r\n" +
					"製造元に問い合わせてください<br>";
		}

		System.out.println("this3");

		if (fileName.isEmpty()) {
			pic = comic.get(0).getImage();
		}

		String createUser = comic.get(0).getCreatedUser();

		Date createDate = comic.get(0).getCreatedDate();

		String modifiedUser = (String) session.getAttribute("username");

		Date modifiedDate = new Date(System.currentTimeMillis());

		System.out.println("this4");
	}
/*
		Comic update = new Comic(comicId, title, categoryId, price, publisher, authorName, releaseDate, synopsis,
				link, pic, createUser, createDate, modifiedUser, modifiedDate);

		try {
			comicService.update(update);

		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			msg += "5.サーバーエラーが発生しました\r\n" +
					"製造元に問い合わせてください";
		}
		msg += "success";
	}

	if(msg.equals("success"))

	{
		request.getRequestDispatcher("toComicInfoManagement").forward(request, response);
	}else
	{
		String strComicId = String.valueOf(comicId);
		session.setAttribute("comicId", strComicId);
		request.setAttribute("msg", msg);
		request.getRequestDispatcher("toComicInfoDelete").forward(request, response);
	}
	}
*/
	private String extractFileName(Part part) {
		String[] splitedHeader = part.getHeader("Content-Disposition").split(";");

		String fileName = null;
		for (String item : splitedHeader) {
			System.out.println(item);
			if (item.trim().startsWith("filename")) {
				fileName = item.substring(item.indexOf('"')).replaceAll("\"", "");
			}
		}
		return fileName;
	}


}
