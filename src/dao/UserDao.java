package dao;

import java.sql.Connection;
import java.sql.Date;
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
	private static final String SQL_SELECT_ID = "SELECT userid, email, username, password, birthday, joindate, withdrawaldate, adminflg, modifieduser, modifieddate FROM users WHERE userid = ?";
	private static final String SQL_INSERT_ALL = "INSERT INTO users (userid, email, username, password, birthday, joindate, withdrawaldate, adminflg, modifieduser, modifieddate) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String SQL_UPDATE_ALL = "UPDATE  users SET email = ?, username = ?, password = ?, birthday = ?, joindate = ?, withdrawaldate = ?, adminflg = ?, modifieduser = ?, modifieddate = ? WHERE userid = ?";
	private static final String TABLE_NAME = "SELECT userid, email, username, password, birthday, joindate, withdrawaldate, adminflg, modifieduser, modifieddate FROM users WHERE adminflg = 'f' AND withdrawaldate IS NULL";
	private static final String LATE = " ORDER BY joindate";

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
				return new User((UUID.fromString(rs.getString("userid"))), rs.getString("email"),
						rs.getString("username"), rs.getString("password"), rs.getDate("birthday"),
						rs.getDate("joindate"), rs.getDate("withdrawaldate"), rs.getBoolean("adminflg"),
						rs.getString("modifieduser"), rs.getDate("modifieddate"));
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
				return new User((UUID.fromString(rs.getString("userid"))), rs.getString("email"),
						rs.getString("username"), rs.getString("password"), rs.getDate("birthday"),
						rs.getDate("joindate"), rs.getDate("withdrawaldate"), rs.getBoolean("adminflg"),
						rs.getString("modifieduser"), rs.getDate("modifieddate"));
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
				return new User((UUID.fromString(rs.getString("userid"))), rs.getString("email"),
						rs.getString("username"), rs.getString("password"), rs.getDate("birthday"),
						rs.getDate("joindate"), rs.getDate("withdrawaldate"), rs.getBoolean("adminflg"),
						rs.getString("modifieduser"), rs.getDate("modifieddate"));
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int registration(User regist) {
		try (PreparedStatement stmt = conn.prepareStatement(SQL_INSERT_ALL)) {
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

			return stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<User> findById(UUID userId) {
		List<User> list = new ArrayList<User>();

		try (PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_ID)) {

			stmt.setObject(1, userId);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				User u = new User((UUID.fromString(rs.getString("userid"))), rs.getString("email"),
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

	public int update(User updateData) {
		try (PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE_ALL)) {
			stmt.setString(1, updateData.getEmail());
			stmt.setString(2, updateData.getUserName());
			stmt.setString(3, updateData.getPassword());
			stmt.setDate(4, updateData.getBirthday());
			stmt.setDate(5, updateData.getJoinDate());
			stmt.setDate(6, updateData.getWithdrawalDate());
			stmt.setBoolean(7, updateData.isAdminFlg());
			stmt.setString(8, updateData.getModifiedUser());
			stmt.setDate(9, updateData.getModifiedDate());
			stmt.setObject(10, updateData.getUserId());

			return stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<User> find(String email, String userName, Date birthday, Date joinDate) {
		List<User> list = new ArrayList<User>();

		try {
			if (((email == null) || (email.equals(""))) && ((userName == null) || (userName.equals("")))
					&& ((birthday == null) || (birthday.equals(""))) && ((joinDate == null) || (joinDate.equals("")))) {
				String data = TABLE_NAME + LATE;

				try (PreparedStatement stmt = conn.prepareStatement(data)) {

					ResultSet rs = stmt.executeQuery();

					while (rs.next()) {
						User u = new User((UUID.fromString(rs.getString("userid"))), rs.getString("email"),
								rs.getString("username"), rs.getString("password"), rs.getDate("birthday"),
								rs.getDate("joindate"), rs.getDate("withdrawaldate"), rs.getBoolean("adminflg"),
								rs.getString("modifieduser"), rs.getDate("modifieddate"));
						list.add(u);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
					throw new RuntimeException(e1);
				}
			} else {
				ArrayList<String> strList = new ArrayList<String>();

				if ((email != null) && (!(email.equals("")))) {
					strList.add("email LIKE '%" + email + "%'");
				}
				if ((userName != null) && (!(userName.equals("")))) {
					strList.add("username LIKE '%" + userName + "%'");
				}
				if ((birthday != null) && (!(birthday.equals("")))) {
					strList.add("birthday = ?");
				}
				if ((joinDate != null) && (!(joinDate.equals("")))) {
					strList.add("joinDate = ?");
				}

				String DATA = String.join(" AND ", strList);

				String data = TABLE_NAME + " AND " + DATA + LATE;

				try (PreparedStatement stmt = conn.prepareStatement(data)) {

					if ((birthday != null) && (!(birthday.equals("")))) {
						stmt.setDate(1, birthday);
					}
					if ((joinDate != null) && (!(joinDate.equals("")))) {
						stmt.setDate(1, joinDate);
					}

					ResultSet rs = stmt.executeQuery();

					while (rs.next()) {
						User u = new User((UUID.fromString(rs.getString("userid"))), rs.getString("email"),
								rs.getString("username"), rs.getString("password"), rs.getDate("birthday"),
								rs.getDate("joindate"), rs.getDate("withdrawaldate"), rs.getBoolean("adminflg"),
								rs.getString("modifieduser"), rs.getDate("modifieddate"));
						list.add(u);
					}
				} catch (SQLException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public int forcedWithdrawal(User forcedWithdrawal) {
		try (PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE_ALL)) {
			stmt.setString(1, forcedWithdrawal.getEmail());
			stmt.setString(2, forcedWithdrawal.getUserName());
			stmt.setString(3, forcedWithdrawal.getPassword());
			stmt.setDate(4, forcedWithdrawal.getBirthday());
			stmt.setDate(5, forcedWithdrawal.getJoinDate());
			stmt.setDate(6, forcedWithdrawal.getWithdrawalDate());
			stmt.setBoolean(7, forcedWithdrawal.isAdminFlg());
			stmt.setString(8, forcedWithdrawal.getModifiedUser());
			stmt.setDate(9, forcedWithdrawal.getModifiedDate());
			stmt.setObject(10, forcedWithdrawal.getUserId());

			return stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
