package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Category;

public class CategoryDao {
	private static final String SQL_SELECT_CATEGORYID = "SELECT categoryid,categoryname FROM category";

	private Connection conn;

	public CategoryDao(Connection conn) {
		this.conn = conn;
	}

	public List<Category> find() {
		List<Category> list = new ArrayList<Category>();

		try (PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_CATEGORYID)) {

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Category u = new Category(rs.getInt("categoryId"), rs.getString("categoryName"));
				list.add(u);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return list;
	}

}
