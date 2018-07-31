package service;

import java.sql.Connection;
import java.sql.SQLException;

import dao.UserDao;
import entity.User;
import util.DbUtil;

public class UserService {

	public User authentication(String email, String password) throws SQLException {
		Connection conn = DbUtil.getConnection();
		try{
			UserDao userDao = new UserDao(conn);
			User user = userDao.findByEmailAndPass(email, password);

			return user;
		}catch(Exception e) {
			e.printStackTrace();
		} finally {
            // コネクションの解放
            conn.close();
        }

		return null;
	}

	public User authentication2(String email) throws SQLException {
		Connection conn = DbUtil.getConnection();
		try{
			UserDao userDao = new UserDao(conn);
			User user = userDao.findByEmail(email);

			return user;
		}catch(Exception e) {
			e.printStackTrace();
		} finally {
            // コネクションの解放
            conn.close();
        }

		return null;
	}

	public User authentication3(String password) throws SQLException {
		Connection conn = DbUtil.getConnection();
		try{
			UserDao userDao = new UserDao(conn);
			User user = userDao.findByPass(password);

			return user;
		}catch(Exception e) {
			e.printStackTrace();
		} finally {
            // コネクションの解放
            conn.close();
        }

		return null;
	}

	public int registration(User regist) {
		try (Connection conn = DbUtil.getConnection()) {
			UserDao userDao = new UserDao(conn);
			return userDao.registration(regist);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

}
