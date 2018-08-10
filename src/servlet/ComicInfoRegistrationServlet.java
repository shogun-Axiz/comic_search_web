package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
 * Servlet implementation class ComicInfoRegistrationServlet
 */
@WebServlet("/comicInfoRegistration")
public class ComicInfoRegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");

		String title = request.getParameter("title");
		String strCategoryId = request.getParameter("categoryid");
		String authorName = request.getParameter("authorName");
		String strPrice = request.getParameter("price");
		String strReleaseDate = request.getParameter("releaseDate");
		String publisher = request.getParameter("publisher");
		String synopsis = request.getParameter("synopsis");
		String link = request.getParameter("link");
		String pic = request.getParameter("pic");

		System.out.println(title);
		System.out.println(strCategoryId);
		System.out.println(authorName);
		System.out.println(strPrice);
		System.out.println(strReleaseDate);
		System.out.println(publisher);
		System.out.println(synopsis);
		System.out.println(link);
		System.out.println(pic);

		String msg = "";

		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = response.getWriter();

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

			//漫画ID作成
			UUID comicId = UUID.randomUUID();

			String spa = FileSystems.getDefault().getSeparator();

			String extension = null;
			Path sourcePath = null;
			Path targetPath = null;

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

			HttpSession session = request.getSession();

			String createUser = (String) session.getAttribute("username");

			Date createDate = new Date(System.currentTimeMillis());

			String modifiedUser = null;

			Date modifiedDate = null;

			ComicService comicService = new ComicService();

			Comic regist = new Comic(comicId, title, categoryId, price, publisher, authorName, releaseDate, synopsis, link, pic, createUser, createDate, modifiedUser, modifiedDate);

			try {
				comicService.registration(regist);
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
				msg += "サーバーエラーが発生しました\r\n" +
						"製造元に問い合わせてください";
			}
		}

		out.print(msg);

	}

}
