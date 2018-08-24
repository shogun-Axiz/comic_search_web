<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Comic Search -login-</title>
<!-- BootstrapのCSS読み込み -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">
<!-- jQuery読み込み -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<!-- BootstrapのJS読み込み -->
<script src="js/bootstrap.min.js"></script>
</head>
<body>
	<header style="background-color: blue;">
		<div style="text-align: right;">
			<div style="text-align: left;">
				<a href="index.jsp"
					style="float: left; font-size: 36px; color: white; text-decoration: none; margin-left: 30px;">
					Comic Searcher </a>
			</div>
			<div style="clear: both;"></div>
		</div>
	</header>
	<div class="login">
		<div class="login-triangle"></div>

		<h2 class="login-header">ログイン</h2>
		<c:if test="${not empty msg}">
			<p style="color: white;">${msg}</p>
		</c:if>
		<form class="login-container" method="GET" action="./login">
			<p>
				<input type="email" name="email" value="${fn:escapeXml(email)}" placeholder="メールアドレス">
			</p>
			<p>
				<input type="password" name="password" placeholder="パスワード">
			</p>
			<p>
				<input type="submit" value="ログイン">
			</p>
		</form>
		<form action="newUser.jsp">
			<p>
				<input type="submit" value="新規会員登録">
			</p>
		</form>
	</div>
	<jsp:include page="include/footer.jsp" flush="true" />
</body>
</html>