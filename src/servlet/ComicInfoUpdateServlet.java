package servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import entity.Category;
import entity.Comic;
import service.CategoryService;
import service.ComicService;
import util.ConversionDate;
import util.ExtractFileName;

/**
 * Servlet implementation class ComicInfoUpdateServlet
 */
@WebServlet("/comicInfoUpdate")
@MultipartConfig(maxFileSize = 209715200)
public class ComicInfoUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		Collection<Part> parts = request.getParts();
		Map<String, String> map1 = new HashMap<String, String>();

		parts.stream().forEach(part -> {
			String contentType = part.getContentType();
			if (contentType == null) {
				try (InputStream inputStream = part.getInputStream()) {
					BufferedReader bufReader = new BufferedReader(new InputStreamReader(inputStream));
					map1.put(part.getName(), bufReader.lines().collect(Collectors.joining()));

				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});

		String title = map1.get("title");
		String strCategoryId = map1.get("catSel");
		String authorName = map1.get("authorName");
		String strPrice = map1.get("price");
		String strReleaseDate = map1.get("releaseDate");
		String publisher = map1.get("publisher");
		String synopsis = map1.get("synopsis");
		String link = map1.get("link");
		String strImageDelete = map1.get("imageDelete");

		HttpSession session = request.getSession();

		UUID comicId = (UUID) session.getAttribute("comicId");

		String msg = "";

		boolean imageDelete = strImageDelete != null;

		String fileName = null;

		Integer categoryId = null;

		Integer price = null;

		String pic = null;

		try {
			Part part = request.getPart("picture");

			ExtractFileName efn = new ExtractFileName();

			fileName = efn.extractFileName(part).replaceAll(".*?\\\\", "");

			if (!(fileName.isEmpty())) {
				part.write("C:\\tmp\\img\\" + fileName);
			}


		} catch (Exception e) {
			e.printStackTrace();
			msg += "サーバーエラーが発生しました\r\n" +
					"製造元に問い合わせてください\r\n";
			request.setAttribute("msg", msg);
			request.getRequestDispatcher("index.jsp").forward(request, response);
			return;
		}

		if ((title == null) || (title.equals(""))) {
			msg += "タイトルを入力してください\r\n";
		} else if (title.length() > 100) {
			msg += "タイトルは100字までです\r\n";
		}

		if ((strCategoryId == null) || (strCategoryId.equals(""))) {
			msg += "カテゴリーを選択してください\r\n";
		} else {
			categoryId = Integer.parseInt(strCategoryId);
			if (categoryId == 0) {
				msg += "カテゴリーを選択してください\r\n";
			}
		}

		if ((authorName == null) || (authorName.equals(""))) {
			msg += "原作者名を入力してください\r\n";
		} else if (authorName.length() > 20) {
			msg += "原作者名は20字までです\r\n";
		}

		if ((strPrice == null) || (strPrice.equals(""))) {
			msg += "値段を入力してください\r\n";
		} else {
			try {
				price = Integer.parseInt(strPrice);
			} catch (Exception e) {
				msg += "値段を数字で入力してください\r\n";
			}

		}

		Date releaseDate = null;

		ConversionDate cond = new ConversionDate();

		if ((strReleaseDate == null) || (strReleaseDate.equals(""))) {
			msg += "発売日を入力してください\r\n";
		} else {
			try {
				releaseDate = cond.conversion(strReleaseDate);
			} catch (Exception e) {
				e.printStackTrace();
				msg += "発売日をyyyy/mm/dd形式で入力してください\r\n";

			}
		}

		if ((publisher == null) || (publisher.equals(""))) {
			msg += "出版社を入力してください\r\n";
		} else if (publisher.length() > 10) {
			msg += "出版社は10字までです\r\n";
		}

		if ((link == null) || (link.equals(""))) {
			msg += "詳細リンクを入力してください\r\n";
		} else if (link.length() > 500) {
			msg += "詳細リンクは500字までです\r\n";
		}

		if ((msg == null) || (msg.equals(""))) {

			String spa = FileSystems.getDefault().getSeparator();

			String extension = null;
			Path sourcePath = null;
			Path targetPath = null;

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
					if (imageDelete) {
						pic = null;
					}
				} catch (Exception e) {
					e.printStackTrace();
					msg += "サーバーエラーが発生しました\r\n" +
							"製造元に問い合わせてください\r\n";
					request.setAttribute("msg", msg);
					request.getRequestDispatcher("index.jsp").forward(request, response);
					return;
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
				if (imageDelete) {
					pic = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
				msg += "サーバーエラーが発生しました\r\n" +
						"製造元に問い合わせてください\r\n";
				request.setAttribute("msg", msg);
				request.getRequestDispatcher("index.jsp").forward(request, response);
				return;
			}

			ComicService comicService = new ComicService();

			List<Comic> comic = null;

			try {
				comic = comicService.select(comicId);
			} catch (Exception e1) {
				// TODO 自動生成された catch ブロック
				e1.printStackTrace();
				msg += "サーバーエラーが発生しました\r\n" +
						"製造元に問い合わせてください\r\n";
				request.setAttribute("msg", msg);
				request.getRequestDispatcher("index.jsp").forward(request, response);
				return;
			}

			if (fileName.isEmpty()) {
				pic = comic.get(0).getImage();
				if (imageDelete) {
					pic = null;
				}
			}

			String createUser = comic.get(0).getCreatedUser();

			Date createDate = comic.get(0).getCreatedDate();

			String modifiedUser = (String) session.getAttribute("username");

			Date modifiedDate = new Date(System.currentTimeMillis());

			Comic update = new Comic(comicId, title, categoryId, price, publisher, authorName, releaseDate, synopsis,
					link, pic, createUser, createDate, modifiedUser, modifiedDate);

			try {
				comicService.update(update);

			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
				msg += "サーバーエラーが発生しました\r\n" +
						"製造元に問い合わせてください\r\n";
				request.setAttribute("msg", msg);
				request.getRequestDispatcher("index.jsp").forward(request, response);
				return;
			}
			msg += "success";
		}

		if (msg.equals("success")) {
			request.getRequestDispatcher("toComicInfoManagement").forward(request, response);
		} else {
			if(fileName != null) {
				msg += "画像をアップロードする場合には、画像を再アップロードしてください\r\n";
			}

			request.setAttribute("title", title);
			request.setAttribute("catId", strCategoryId);
			request.setAttribute("authorName", authorName);
			request.setAttribute("price", strPrice);
			request.setAttribute("releaseDate", strReleaseDate);
			request.setAttribute("publisher", publisher);
			request.setAttribute("synopsis", synopsis);
			request.setAttribute("link", link);
			request.setAttribute("imageDelete", imageDelete);

			request.setAttribute("msg", msg);
			CategoryService categoryService = new CategoryService();
			try {
				List<Category> cat = categoryService.authentication();
				if (cat.size() != 0) {
					request.setAttribute("cat", cat);
				}
				request.getRequestDispatcher("comicInfoUpdate2.jsp").forward(request, response);
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
				request.setAttribute("msg", "サーバーエラーが発生しました\r\n" +
						"製造元に問い合わせてください\r\n");

				// 次画面指定
				request.getRequestDispatcher("index.jsp").forward(request, response);
				return;
			}
		}
	}

}
