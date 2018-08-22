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
<title>Comic Search (AdminMode) -会員情報管理-</title>
<!-- BootstrapのCSS読み込み -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">
<!-- jQuery読み込み -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<!-- BootstrapのJS読み込み -->
<script src="js/bootstrap.min.js"></script>
<script type="text/javascript">

</script>
<link rel="stylesheet" href="themes/base/jquery.ui.all.css" />
<script type="text/javascript" src="jquery-1.4.2.js"></script>
<script src="js/calendar.js"></script>
</head>
<body>
	<c:if test="${empty username}">
		<c:redirect url="index.jsp" />
	</c:if>
	<jsp:include page="include/header2.jsp" flush="true" />
	<div class="login">
		<div class="login-triangle"></div>

		<h2 class="login-header">会員情報検索</h2>

		<form class="login-container" method="post"
			action="accountInfoManagement">
			<p>
				<label for="email">メールアドレス</label><input type="text" name="email"
					placeholder="メールアドレス">
			</p>
			<p>
				<label for="username">ユーザーネーム</label><input type="text"
					name="username" placeholder="ユーザーネーム">
			</p>
			<p>
				<label for="birthday">生年月日</label><input type="text" class="xdate"
					name="birthday" placeholder="生年月日">
			</p>
			<p>
				<label for="joinDate">入会日</label><input type="text" class="xdate"
					name="joinDate" placeholder="入会日">
			</p>
			<h2>
				<p>
					<input type="submit" value="検索"
						style="background-color: #28d; color: white;">
				</p>
			</h2>
		</form>
	</div>
	<br>
	<c:if test="${not empty msg}">
		<p style="color: white; font-size: 24px; text-align: center;">${msg}</p>
	</c:if>
	<c:if test="${isSuccess == true}">
		<h2>
			<p style="text-align: center; color: white;">[検索結果]</p>
		</h2>
		<br>
		<div id = "user">
			<table border="1" align="center"
				style="width: 80%; background-color: white; font-size: 24px;">
				<thead>
					<tr align="center">
						<th class = "sort" data-sort = "email" style="background-color: #CCFFFF;">メールアドレス</th>
						<th class = "sort" data-sort = "username" style="background-color: #CCFFFF;">ユーザーネーム</th>
						<th class = "sort" data-sort = "birthday" style="background-color: #CCFFFF;">生年月日</th>
						<th class = "sort" data-sort = "joindate" style="background-color: #CCFFFF;">入会日</th>
						<th style="background-color: #CCFFFF;"></th>
					</tr>
				</thead>
				<tbody class = "list">
					<c:forEach var="list" items="${list}">
						<tr align="center">
							<td class = "email">${list.email}</td>
							<td class = "username">${list.username}</td>
							<td class = "birthday">${list.birthday}</td>
							<td class = "joindate">${list.joindate}</td>
							<td><button type="submit" class="btn btn-danger btn-lg"
									onClick="location.href='./userDelete?userId=${list.userId}'">強制退会</button></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</c:if>
	<jsp:include page="include/footer2.jsp" flush="true" />
</body>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/list.js/1.5.0/list.min.js"></script>
<script>
	var options = {
		valueNames : [ 'email', 'username', 'birthday', 'joindate', ]
	};
	var comicList = new List('user', options);
</script>
</html>