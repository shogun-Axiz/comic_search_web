package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import entity.User;

public class UserDao {
	private static final String SQL_SELECT_MAIL_AND_PASS = "SELECT userid, email, username, password, birthday, joindate, withdrawaldate, adminflg, modifieduser, modifieddate FROM users WHERE email = ? AND password = ?";
	private static final String SQL_SELECT_MAIL = "SELECT userid, email, username, password, birthday, joindate, withdrawaldate, adminflg, modifieduser, modifieddate FROM users WHERE email = ?";
	private static final String SQL_SELECT_PASS = "SELECT userid, email, username, password, birthday, joindate, withdrawaldate, adminflg, modifieduser, modifieddate FROM users WHERE password = ?";
	private static final String SQL_SELECT_NAME = "SELECT userid, email, username, password, birthday, joindate, withdrawaldate, adminflg, modifieduser, modifieddate FROM users WHERE username = ?";
	private static final String SQL_INSERT_ALL = "INSERT INTO users (userid, email, username, password, birthday, joindate, withdrawaldate, adminflg, modifieduser, modifieddate) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private Connection conn;

	public UserDao(Connection conn) {
		this.conn = conn;
	}

	public User findByEmailAndPass(String email, String password) {
		try (PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_MAIL_AND_PASS)) {
			stmt.setString(1, email);
			stmt.setString(2, password);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return new User( (UUID.fromString(rs.getString("userid"))), rs.getString("email"), rs.getString("username"), rs.getString("password"), rs.getDate("birthday"), rs.getDate("joindate"), rs.getDate("withdrawaldate"), rs.getBoolean("adminflg"), rs.getString("modifieduser"), rs.getDate("modifieddate"));
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public User findByEmail(String email) {
		try (PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_MAIL)) {
			stmt.setString(1, email);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return new User( (UUID.fromString(rs.getString("userid"))), rs.getString("email"), rs.getString("username"), rs.getString("password"), rs.getDate("birthday"), rs.getDate("joindate"), rs.getDate("withdrawaldate"), rs.getBoolean("adminflg"), rs.getString("modifieduser"), rs.getDate("modifieddate"));
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public User findByPass(String password) {
		try (PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_PASS)) {
			stmt.setString(1, password);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return new User( (UUID.fromString(rs.getString("userid"))), rs.getString("email"), rs.getString("username"), rs.getString("password"), rs.getDate("birthday"), rs.getDate("joindate"), rs.getDate("withdrawaldate"), rs.getBoolean("adminflg"), rs.getString("modifieduser"), rs.getDate("modifieddate"));
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int registration(User regist) {
		try (PreparedStatement stmt = conn.prepareStatement(SQL_INSERT_ALL)){
			stmt.setObject(1, regist.getUserId());
			stmt.setString(2, regist.getEmail());
			stmt.setString(3, regist.getUserName());
			stmt.setString(4, regist.getPassword());
			stmt.setDate(5, regist.getBirthday());
			stmt.setDate(6, regist.getJoinDate());
			stmt.setDate(7, regist.getWithdrawalDate());
			stmt.setBoolean(8, regist.isAdminFlg());
			stmt.setString(9, regist.getModifiedUser());
			stmt.setDate(10, regist.getModifiedDate());

			System.out.println("postgres     > " + stmt.toString());
			return stmt.executeUpdate();

		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<User> findByName(String userName) {
		List<User> list = new ArrayList<User>();

		try (PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_NAME)) {
			System.out.println(SQL_SELECT_NAME);

			stmt.setString(1, userName);

			ResultSet rs = stmt.executeQuery();

			while(rs.next()) {
				User u = new User( (UUID.fromString(rs.getString("userid"))), rs.getString("email"),
						rs.getString("username"), rs.getString("password"), rs.getDate("birthday"),
						rs.getDate("joindate"), rs.getDate("withdrawaldate"), rs.getBoolean("adminflg"),
						rs.getString("modifieduser"), rs.getDate("modifieddate"));
				list.add(u);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return list;
	}


}
