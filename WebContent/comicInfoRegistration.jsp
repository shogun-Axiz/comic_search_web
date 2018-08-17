<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Comic Search (AdminMode) -漫画情報登録-</title>
<!-- BootstrapのCSS読み込み -->
<link href="css/style.css" rel="stylesheet">
<link rel="stylesheet" href="themes/base/jquery.ui.all.css" />
<link type="text/css" rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.3/themes/cupertino/jquery-ui.min.css" />
<!-- jQuery読み込み -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<!-- BootstrapのJS読み込み -->
<script src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery-1.4.2.js"></script>
<script type="text/javascript" src="ui/jquery.ui.core.js"></script>
<script type="text/javascript" src="ui/jquery.ui.datepicker.js"></script>
<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
<script type="text/javascript"
	src="http://code.jquery.com/ui/1.10.3/jquery-ui.min.js"></script>
<script src="js/calendar.js"></script>
</head>
<body>
	<c:if test="${empty username}">
		<c:redirect url="index.jsp" />
	</c:if>
	<jsp:include page="include/header2.jsp" flush="true" />
	<div class="login">
		<div class="login-triangle"></div>

		<h2 class="login-header">漫画情報登録</h2>
		<div style="background-color: snow">
			<form class="login-container" method="post"
				enctype="multipart/form-data" action="comicInfoRegistration">
				<p>
					<label for="title">タイトル</label><input type="text" name="title"
						placeholder="タイトル">
				</p>
				<p>
					<label for="category">カテゴリー</label> <select id="catSel" name = "categoryId"
						style="width: 100%;">
						<option value="0" selected>全て</option>
						<c:forEach var="cat" items="${cat}">
							<option value="${cat.categoryId }">${cat.categoryName }</option>
						</c:forEach>
					</select>
				</p>
				<p>
					<label for="authorName">原作者名</label><input type="text"
						name="authorName" placeholder="原作者名">
				</p>
				<p>
					<label for="price">値段</label><input type="text" name="price"
						placeholder="値段">
				</p>
				<p>
					<label for="releaseDate">発売日</label><input type="text"
						name="releaseDate" class="xdate" placeholder="発売日" />
				</p>
				<p>
					<label for="publisher">出版社</label><input type="text"
						name="publisher" placeholder="出版社">
				</p>
				<p>
					<label for="synopsis">紹介文</label>
					<textarea name="synopsis" rows="16" cols="50"
						style="width: 100%; height: 400px;" placeholder="紹介文"></textarea>
				</p>
				<p>
					<label for="link">詳細リンク</label><input type="text" name="link"
						placeholder="詳細リンク">
				</p>
				<p>
					<label for="pic">表紙画像</label> <input type="file" name="picture">
				</p>
				<h2>
					<p>
						<input type="submit" value="登録"
							style="background-color: #28d; color: white;">
					</p>
				</h2>
			</form>
		</div>
	</div>
	<jsp:include page="include/footer2.jsp" flush="true" />
</body>
<script>
	$(function() {
		$("#catSel").val("${strCategoryId}");
	});
</script>
</html>