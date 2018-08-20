package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
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

import entity.Comic;
import service.ComicService;

/**
 * Servlet implementation class ComicInfoRegistrationServlet
 */
@WebServlet("/comicInfoRegistration")
@MultipartConfig(maxFileSize = 209715200)
public class ComicInfoRegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		System.out.println("partx");

		 Collection<Part> parts = request.getParts();
		 Map<String, String> map1 = new HashMap<String, String>();

	        parts.stream().forEach(part -> {
	            System.out.println("name:" + part.getName());
	            String contentType = part.getContentType();
	            System.out.println("contentType:" + contentType);
	            if ( contentType == null) {
	                try(InputStream inputStream = part.getInputStream()) {
	                    BufferedReader bufReader = new BufferedReader(new InputStreamReader(inputStream));
	                    map1.put(part.getName(),  bufReader.lines().collect(Collectors.joining()));

	                } catch (IOException e) {
	                    throw new RuntimeException(e);
	                }
	            }
	        });

	        String title = map1.get("title");
	        String strCategoryId = map1.get("categoryId");
	        String authorName = map1.get("authorName");
	        String strPrice = map1.get("price");
	        String strReleaseDate = map1.get("releaseDate");
	        String publisher = map1.get("publisher");
	        String synopsis = map1.get("synopsis");
	        String link = map1.get("link");
	        String picture = map1.get("picture");

	        System.out.println(title);
	        System.out.println(strCategoryId);
	        System.out.println(authorName);
	        System.out.println(strPrice);
	        System.out.println(strReleaseDate);
	        System.out.println(publisher);
	        System.out.println(synopsis);
	        System.out.println(link);
	        System.out.println(picture);

		try {
			Part part = request.getPart("picture");
			String name = this.getFileName(part);
			part.write(getServletContext().getRealPath("/img"));
		}catch(Exception e) {
			e.printStackTrace();
		}

		String msg = "";

		if ((title == null) || (title.equals(""))) {
			msg += "タイトルを入力してください";
		} else if (title.length() > 100) {
			msg += "タイトルは100字までです";
		}

		if ((strCategoryId == null) || (strCategoryId.equals(""))) {
			msg += "カテゴリーを選択してください";
		}

		if ((authorName == null) || (authorName.equals(""))) {
			msg += "原作者名を入力してください";
		} else if (authorName.length() > 20) {
			msg += "原作者名は20字までです";
		}

		if ((strPrice == null) || (strPrice.equals(""))) {
			msg += "値段を入力してください";
		}

		if ((strReleaseDate == null) || (strReleaseDate.equals(""))) {
			msg += "発売日を入力してください";
		}

		if ((publisher == null) || (publisher.equals(""))) {
			msg += "出版社を入力してください";
		} else if (publisher.length() > 10) {
			msg += "出版社は10字までです";
		}

		if ((link == null) || (link.equals(""))) {
			msg += "詳細リンクを入力してください";
		}

		System.out.println("this1");

		Integer categoryId = Integer.parseInt(strCategoryId);
		Integer price = Integer.parseInt(strPrice);

		// 日付の書式を指定する(発売日)
		if (!(strReleaseDate.equals(""))) {
			DateFormat df1 = new SimpleDateFormat("yyyy/MM/dd");
			// 日付解析を厳密に行う設定にする
			df1.setLenient(false);
			try {
				df1.parse(strReleaseDate);
			} catch (ParseException e) {
				msg += "生年月日をyyyy/mm/dd形式で入力してください<br>";
			}

		}

		if (msg == "") {
			//発売日をDate型に変換
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			Date releaseDate = null;

			try {
				java.util.Date day = sdf.parse(strReleaseDate);
				releaseDate = new java.sql.Date(day.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
				msg += "サーバーエラーが発生しました\r\n" +
						"製造元に問い合わせてください";
			}

			//漫画ID
			UUID comicId = UUID.randomUUID();

			String spa = FileSystems.getDefault().getSeparator();

			String extension = null;
			Path sourcePath = null;
			Path targetPath = null;
/*
			try {
				sourcePath = Paths.get(pic);
				int position = pic.lastIndexOf(".");
				if (position != -1) {
					extension = pic.substring(position + 1);
				}
				targetPath = Paths.get("img/" + spa + comicId + "." + extension);
				System.out.println(sourcePath.toString());
				System.out.println(targetPath.toString());
				Files.move(sourcePath, targetPath);
			} catch (IOException e) {
				e.printStackTrace();
				msg += "サーバーエラーが発生しました\r\n" +
						"製造元に問い合わせてください";
			}

			pic = targetPath.toString();
*/
			System.out.println("this2");
			HttpSession session = request.getSession();

			String createUser = (String) session.getAttribute("username");

			Date createDate = new Date(System.currentTimeMillis());

			String modifiedUser = null;

			Date modifiedDate = null;

			ComicService comicService = new ComicService();

			String pic = null;

			Comic regist = new Comic(comicId, title, categoryId, price, publisher, authorName, releaseDate, synopsis,
					link, pic, createUser, createDate, modifiedUser, modifiedDate);

			System.out.println("this3");
			try {
				comicService.registration(regist);

			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
				msg += "サーバーエラーが発生しました\r\n" +
						"製造元に問い合わせてください";
			}
			msg += "success";
		}

		if (msg == "success") {
			request.getRequestDispatcher("./toComicInfoManagement").forward(request, response);
		} else {
			request.setAttribute("msg", msg);
			request.getRequestDispatcher("comicInfoRegistration").forward(request, response);
		}
	}

	private String getFileName(Part part) {
		String name = null;
        for (String dispotion : part.getHeader("Content-Disposition").split(";")) {
            if (dispotion.trim().startsWith("filename")) {
                name = dispotion.substring(dispotion.indexOf("=") + 1).replace("\"", "").trim();
                name = name.substring(name.lastIndexOf("\\") + 1);
                break;
            }
        }
        return name;
	}

}
