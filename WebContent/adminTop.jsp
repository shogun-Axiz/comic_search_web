<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Comic Search (AdminMode) -管理者トップ-</title>
<!-- BootstrapのCSS読み込み -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">
<!-- jQuery読み込み -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<!-- BootstrapのJS読み込み -->
<script src="js/bootstrap.min.js"></script>
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
	<c:if test="${empty username}">
		<c:redirect url="index.jsp" />
	</c:if>
	<header style="background-color: blue;">
		<div style="text-align: right;">
			<div style="text-align: left;">
				<a href="userTop.jsp"
					style="float: left; font-size: 36px; color: white; text-decoration: none; margin-left: 30px;">
					Comic Searcher </a>
				<p
					style="float: left; font-size: 18px; color: white; margin-top: 15px;">&emsp;&emsp;${username}
					さん</p>
			</div>
			<div style="float: right; margin-top: 12px; margin-right: 30px;">
				<input type="button" style="float: right;" value="ログアウト"
					onClick="logout()">
			</div>
			<div style="clear: both;"></div>
		</div>
	</header>
	<div class="login">
		<div class="login-triangle"></div>

		<h2 class="login-header">管理者メニュー</h2>
		<div style="background-color: snow">
			<form action="comicInfoManagement.jsp">
				<p>
					<input type="submit" value="漫画情報管理ページ">
				</p>
			</form>
			<form action="accountInfoManagement.jsp">
				<p>
					<input type="submit" value="会員情報管理ページ">
				</p>
			</form>
		</div>
	</div>
	<jsp:include page="include/footer.jsp" flush="true" />
</body>
</html>