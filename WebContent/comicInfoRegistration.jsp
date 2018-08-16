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
<script type="text/javascript">
	function manage() {
		// 「OK」時の処理開始 ＋ 確認ダイアログの表示
		if (window.confirm('この内容でよろしいですか？')) {

			var url = "/comic_search_web/comicInfoRegistration";

			var request = {
				title : document.getElementById("title").value,
				categoryid : document.getElementById("catSel").value,
				authorName : document.getElementById("authorName").value,
				price : document.getElementById("price").value,
				releaseDate : document.getElementById("xxdate1").value,
				publisher : document.getElementById("publisher").value,
				synopsis : document.getElementById("synopsis").value,
				link : document.getElementById("link").value,
				pic : $('#file')[0].files[0]
			};
			var result = $
					.ajax({
						type : 'GET',
						url : url,
						data : request,
						dataType : 'text',
						async : false,
						success : function(data) {
							//取得成功したら実行する処理
							alert(data);
							if (data == 'success') {
								alert("登録が完了しました！");
								location.href = "/comic_search_web/comicInfoManagement";
							} else {
								$('#error').text(data);
							}

						},
						error : function() {
							//取得失敗時に実行する処理
							console.log("サーバーエラーで失敗しました");
						}
					});

		}
		// 「OK」時の処理終了

		// 「キャンセル」時の処理開始
		else {

			alert('キャンセルされました'); // 警告ダイアログを表示k
		}
		// 「キャンセル」時の処理終了

	}
</script>
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
			<form class="login-container">
				<p>
					<label for="title">タイトル</label><input type="text" id="title"
						placeholder="タイトル">
				</p>
				<p>
					<label for="category">カテゴリー</label> <select id="catSel"
						style="width: 100%;">
						<option value="0" selected>全て</option>
						<c:forEach var="cat" items="${cat}">
							<option value="${cat.categoryId }">${cat.categoryName }</option>
						</c:forEach>
					</select>
				</p>
				<p>
					<label for="authorName">原作者名</label><input type="text"
						id="authorName" placeholder="原作者名">
				</p>
				<p>
					<label for="price">値段</label><input type="text" id="price"
						placeholder="値段">
				</p>
				<p>
					<label for="releaseDate">発売日</label><input type="text" id="xxdate1"
						class="xdate" placeholder="発売日" />
				</p>
				<p>
					<label for="publisher">出版社</label><input type="text" id="publisher"
						placeholder="出版社">
				</p>
				<p>
					<label for="synopsis">紹介文</label>
					<textarea id="synopsis" rows="16" cols="50"
						style="width: 100%; height: 400px;" placeholder="紹介文"></textarea>
				</p>
				<p>
					<label for="link">詳細リンク</label><input type="text" id="link"
						placeholder="詳細リンク">
				</p>
				<p>
					<label for="pic">表紙画像</label>
				<input type="file" id="file">
				</p>
				<h2>
					<p>
						<input type="button" value="登録" onClick="manage()"
							style="background-color: #28d; color: white;">
					</p>
				</h2>
			</form>
		</div>
	</div>
	<footer class="footer2" style="background-color: blue;">
		<p style="text-align: center; color: white;">(C) Copyright 2018
			CyberTech corporation All Rights Reserved.</p>
	</footer>
</body>
<script>
	$(function() {
		$("#catSel").val("${strCategoryId}");
	});
</script>
</html>