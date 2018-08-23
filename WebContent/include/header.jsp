<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script type="text/javascript">
<!--
	function logout() {
		alert("ログアウトしました \nログイン画面に戻ります");
		location.href = "./logout";
	}
// -->
</script>
</head>
<body>
	<header style="background-color: blue;">
		<div style="text-align: right;">
			<div style="text-align: left;">
				<a href="toUserTop?username=${username}"
					style="float: left; font-size: 36px; color: white; text-decoration: none; margin-left: 30px;">
					Comic Searcher </a>
				<p
					style="float: left; font-size: 18px; color: white; margin-top: 15px;">&emsp;&emsp;${username}
					さん</p>
			</div>
			<div style="float: right; margin-top: 12px; margin-right: 30px;">
				<input type="button" style="float: right;" value="トップに戻る"
					onClick="location.href='toUserTop?username=${username}'"> <input type="button"
					style="float: right;" value="ログアウト" onClick="logout()">
			</div>
			<div style="clear: both;"></div>
		</div>
	</header>
</body>
</html>