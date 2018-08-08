package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.CategoryDao;
import entity.Category;
import util.DbUtil;

public class CategoryService {
	public List<Category> authentication() throws SQLException {
		Connection conn = DbUtil.getConnection();
		try{
			CategoryDao categoryDao = new CategoryDao(conn);

			return categoryDao.find();
		}catch(Exception e) {
			e.printStackTrace();
		} finally {
            // コネクションの解放
            conn.close();
        }

		return null;
	}
}
