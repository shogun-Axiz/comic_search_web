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
	private static final String SQL_SELECT_CATEGORYID_ID = "SELECT categoryid,categoryname FROM category WHERE categoryid = ?";
	private static final String SQL_SELECT_CATEGORYID_NAME = "SELECT categoryid,categoryname FROM category WHERE categoryname = ?";

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
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return list;
	}

	public Category findById(Integer categoryId) {
		try (PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_CATEGORYID_ID)) {
			stmt.setInt(1, categoryId);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return new Category(rs.getInt("categoryid"), rs.getString("categoryname"));
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public Category findByName(String categoryName) {
		try (PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_CATEGORYID_NAME)) {
			stmt.setString(1, categoryName);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return new Category(rs.getInt("categoryid"), rs.getString("categoryname"));
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
