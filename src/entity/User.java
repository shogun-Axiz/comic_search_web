package entity;

import java.sql.Date;
import java.util.UUID;

public class User {
	private UUID userId;
	private String email;
	private String userName;
	private String password;
	private Date birthday;
	private Date joinDate;
	private Date withdrawalDate;
	private boolean adminFlg;
	private String modifiedUser;
	private Date modifiedDate;

	public User() {

	}

	public User(UUID userId, String email, String userName, String password, Date birthday, Date joinDate,
					Date withdrawalDate, boolean adminFlg, String modifiedUser, Date modifiedDate) {
		this.userId = userId;
		this.email = email;
		this.userName = userName;
		this.password = password;
		this.birthday = birthday;
		this.joinDate = joinDate;
		this.withdrawalDate = withdrawalDate;
		this.adminFlg = adminFlg;
		this.modifiedUser = modifiedUser;
		this.modifiedDate = modifiedDate;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public Date getWithdrawalDate() {
		return withdrawalDate;
	}

	public void setWithdrawalDate(Date withdrawalDate) {
		this.withdrawalDate = withdrawalDate;
	}

	public boolean isAdminFlg() {
		return adminFlg;
	}

	public void setAdminFlg(boolean adminFlg) {
		this.adminFlg = adminFlg;
	}

	public String getModifiedUser() {
		return modifiedUser;
	}

	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}



}
