package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import entity.Comic;

public class ComicDao {
	private static final String TABLE_NAME = "SELECT comicid, title, categoryname, price, publisher, authorname,"
			+ "releasedate, synopsis, link, image, createuser, createdate,"
			+ "modifieduser, modifieddate FROM comic INNER JOIN category ON comic.categoryid = category.categoryid";
	private static final String LATE = " ORDER BY releasedate;";
	private static final String INSERT_ALL = "INSERT INTO comic (comicid, title, categoryid, price, publisher,"
			+ " authorname, releasedate, synopsis, link, image, createuser, createdate, modifieduser, modifieddate) "
			+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private Connection conn;

	public ComicDao(Connection conn) {
		this.conn = conn;
	}

	public List<Comic> find(String title, String authorName, String publisher, Integer categoryId, Integer price1,
			Integer price2, Date releaseDate1, Date releaseDate2) {

		List<Comic> list = new ArrayList<Comic>();
		try {
			if ((((title == null) || (title.equals(""))) &&
					((authorName == null) || (authorName.equals(""))) &&
					((publisher == null) || (publisher.equals(""))) &&
					((categoryId == 0) || (categoryId.equals(""))) &&
					((price1 == -1) || (price1.equals(""))) &&
					((price2 == -1) || (price2.equals(""))) &&
					((releaseDate1 == null) || (releaseDate1.equals(""))) &&
					((releaseDate2 == null) || (releaseDate2.equals(""))))) {
				String data = TABLE_NAME + LATE;

				try (PreparedStatement stmt = conn.prepareStatement(data)) {

					ResultSet rs = stmt.executeQuery();

					while (rs.next()) {
						Comic u = new Comic(UUID.fromString(rs.getString("comicid")), rs.getString("title"),
								rs.getString("categoryname"),
								rs.getInt("price"), rs.getString("authorname"), rs.getString("publisher"),
								rs.getDate("releasedate"),
								rs.getString("synopsis"), rs.getString("link"), rs.getString("image"),
								rs.getString("createuser"), rs.getDate("createdate"), rs.getString("modifieduser"),
								rs.getDate("modifieddate"));
						list.add(u);
					}
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			} else {
				ArrayList<String> strList = new ArrayList<String>();

				if ((title != null) && (!(title.equals("")))) {
					strList.add("title LIKE '%" + title + "%'");
				}
				if ((publisher != null) && (!(publisher.equals("")))) {
					strList.add("publisher LIKE '%" + publisher + "%'");
				}
				if ((authorName != null) && (!(authorName.equals("")))) {
					strList.add("authorname LIKE '%" + authorName + "%'");
				}
				if ((categoryId != 0) && (!(categoryId.equals("")))) {
					strList.add("comic.categoryId = " + categoryId);
				}
				if ((price1 >= 0) && (!(price1.equals("")))) {
					strList.add("price >= " + price1);
				}
				if ((price2 >= 0) && (!(price2.equals("")))) {
					strList.add("price <= " + price2);
				}
				if ((releaseDate1 != null) && (!(releaseDate1.equals("")))) {
					strList.add("releasedate > '" + releaseDate1 + "'");
				}
				if ((releaseDate2 != null) && (!(releaseDate2.equals("")))) {
					strList.add("releasedate < '" + releaseDate2 + "'");
				}

				String DATA = String.join(" AND ", strList);

				String data = TABLE_NAME + " WHERE " + DATA + LATE;

				try (PreparedStatement stmt = conn.prepareStatement(data)) {

					ResultSet rs = stmt.executeQuery();

					while (rs.next()) {
						Comic u = new Comic(UUID.fromString(rs.getString("comicid")), rs.getString("title"),
								rs.getString("categoryname"),
								rs.getInt("price"), rs.getString("authorname"), rs.getString("publisher"),
								rs.getDate("releasedate"),
								rs.getString("synopsis"), rs.getString("link"), rs.getString("image"),
								rs.getString("createuser"), rs.getDate("createdate"), rs.getString("modifieduser"),
								rs.getDate("modifieddate"));
						list.add(u);
					}
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

	public List<Comic> findById(UUID comicId) {
		ArrayList<String> strList = new ArrayList<String>();

		if ((comicId != null) && (!(comicId.equals("")))) {
			strList.add("comicid = '" + comicId + "'");
		}

		String DATA = String.join("", strList);

		String data = TABLE_NAME + " WHERE " + DATA + LATE;
		List<Comic> list = new ArrayList<Comic>();
		try (PreparedStatement stmt = conn.prepareStatement(data)) {

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Comic u = new Comic(UUID.fromString(rs.getString("comicid")), rs.getString("title"),
						rs.getString("categoryname"),
						rs.getInt("price"), rs.getString("authorname"), rs.getString("publisher"),
						rs.getDate("releasedate"),
						rs.getString("synopsis"), rs.getString("link"), rs.getString("image"),
						rs.getString("createuser"), rs.getDate("createdate"), rs.getString("modifieduser"),
						rs.getDate("modifieddate"));
				list.add(u);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return list;
	}

	public int registration(Comic regist) {
		try (PreparedStatement stmt = conn.prepareStatement(INSERT_ALL)) {
			stmt.setObject(1, regist.getComicId());
			stmt.setString(2, regist.getTitle());
			stmt.setInt(3, regist.getCategoryId());
			stmt.setInt(4, regist.getPrice());
			stmt.setString(5, regist.getPublisher());
			stmt.setString(6, regist.getAuthorName());
			stmt.setDate(7, regist.getReleaseDate());
			stmt.setString(8, regist.getSynopsis());
			stmt.setString(9, regist.getLink());
			stmt.setString(10, regist.getImage());
			stmt.setString(11, regist.getCreatedUser());
			stmt.setDate(12, regist.getCreatedDate());
			stmt.setString(13, regist.getModifiedUser());
			stmt.setDate(14, regist.getModifiedDate());

			return stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
