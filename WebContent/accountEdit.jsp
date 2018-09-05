<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Comic Search -会員情報編集-</title>
<!-- BootstrapのCSS読み込み -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">
<!-- jQuery読み込み -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<!-- BootstrapのJS読み込み -->
<script src="js/bootstrap.min.js"></script>
<script type="text/javascript">
	function disp() {

		// 「OK」時の処理開始 ＋ 確認ダイアログの表示
		if (window.confirm('この内容でよろしいですか？')) {

			var url = "/comic_search_web/accountEditConfirm";

			var request = {
				email : document.getElementById("email").value,
				username : document.getElementById("username").value,
				password : document.getElementById("password").value,
				rePassword : document.getElementById("rePassword").value,
				birthday : document.getElementById("xxdate").value
			};
			var result = $.ajax({
				type : 'GET',
				url : url,
				data : request,
				dataType : 'text',
				async : false,
				success : function(data) {
					//取得成功したら実行する処理
					if (data == 'success') {
						alert("会員情報を編集しました！");
						location.href = "/comic_search_web/toUserTop?username="
								+ document.getElementById("username").value;
					}else if(data == 'withdrawal'){
						location.href = "/comic_search_web/index.jsp";
					}else {
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
<link rel="stylesheet" href="themes/base/jquery.ui.all.css" />
<script type="text/javascript" src="jquery-1.4.2.js"></script>
<script type="text/javascript" src="ui/jquery.ui.core.js"></script>
<script type="text/javascript" src="ui/jquery.ui.datepicker.js"></script>
<link type="text/css" rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.3/themes/cupertino/jquery-ui.min.css" />
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
	<jsp:include page="include/header.jsp" flush="true" />
	<div class="login">
		<div class="login-triangle"></div>

		<h2 class="login-header">会員情報編集</h2>
		<c:if test="${msg !='success'}">
			<pre id="error" style="color: white; font-size : 24px; white-space: pre-wrap ; margin-bottom: 0px;"></pre>
		</c:if>
		<form class="login-container" method="GET">

			<p>
				<label for="email">メールアドレス</label><input type="text" id="email"
					value="${email}" placeholder="メールアドレス">
			</p>
			<p>
				<label for="username">ユーザーネーム</label><input type="text"
					id="username" value="${username}" placeholder="ユーザーネーム">
			</p>
			<p>
				<label for="password">パスワード</label><input type="password"
					id="password" placeholder="パスワード">
			</p>
			<p>
				<label for="rePassword">パスワード（再入力）</label><input type="password"
					id="rePassword" placeholder="パスワード(再入力)">
			</p>
			<p>
				<label for="birthday">生年月日</label><input type="text" class="xdate"
					id="xxdate" value="${birthday}">
			</p>
			<h2>
				<input type="button" value="編集適用" onClick="disp()"
					style="background-color: #28d; color: white;">
			</h2>

		</form>

	</div>
	<jsp:include page="include/footer2.jsp" flush="true" />
</body>
</html>