package service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

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

	public int registration(User regist) throws SQLException {
		Connection conn = DbUtil.getConnection();
		try  {
			UserDao userDao = new UserDao(conn);
			return userDao.registration(regist);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
            // コネクションの解放
            conn.close();
        }

		return 0;
	}

	public List<User> authentication4(UUID userId) throws SQLException {
		Connection conn = DbUtil.getConnection();
		try  {
			UserDao userDao = new UserDao(conn);
			return userDao.findById(userId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
            // コネクションの解放
            conn.close();
        }
		return null;
	}

	public int update(User updateData) throws SQLException {
		Connection conn = DbUtil.getConnection();
		try  {
			UserDao userDao = new UserDao(conn);
			return userDao.update(updateData);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
            // コネクションの解放
            conn.close();
        }

		return 0;
	}

	public List<User> search(String email, String userName, Date birthday, Date joinDate) throws SQLException {
		Connection conn = DbUtil.getConnection();
		try{
			UserDao userDao = new UserDao(conn);
			return userDao.find(email, userName, birthday, joinDate);
		}catch(Exception e) {
			e.printStackTrace();
		} finally {
            // コネクションの解放
            conn.close();
        }
		return Collections.emptyList();
	}

	public int forcedWithdrawal(User forcedWithdrawal) throws SQLException {
		Connection conn = DbUtil.getConnection();
		try  {
			UserDao userDao = new UserDao(conn);
			return userDao.forcedWithdrawal(forcedWithdrawal);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
            // コネクションの解放
            conn.close();
        }

		return 0;
	}

}
