<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Comic Search -新規会員登録-</title>
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

			var url = "/comic_search_web/newUser";

			var request = {
					email : document.getElementById("email").value,
					username : document.getElementById("username").value,
					password : document.getElementById("password").value,
					rePassword : document.getElementById("rePassword").value,
					birthday : document.getElementById("xxdate").value
			};
			$.ajax({
				type : 'GET',
				url : url,
				data :request,
				dataType : 'text',
				success : function(data) {
					//取得成功したら実行する処理
					alert(data);
					if(data =='success'){
						alert("登録が完了しました！");
						location.href = "/comic_search_web/toIndex?email="+document.getElementById("email").value;
					}else{
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

<script>
	$(function() {
		$("#xxdate").datepicker({
			changeYear : true, // 年選択をプルダウン化
			changeMonth : true, // 月選択をプルダウン化
			yearRange : "-50:+1"
		});

		// 日本語化
		$.datepicker.regional['ja'] = {
			closeText : '閉じる',
			prevText : '<前',
	    nextText: '次>',
			currentText : '今日',
			monthNames : [ '1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月',
					'9月', '10月', '11月', '12月' ],
			monthNamesShort : [ '1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月',
					'9月', '10月', '11月', '12月' ],
			dayNames : [ '日曜日', '月曜日', '火曜日', '水曜日', '木曜日', '金曜日', '土曜日' ],
			dayNamesShort : [ '日', '月', '火', '水', '木', '金', '土' ],
			dayNamesMin : [ '日', '月', '火', '水', '木', '金', '土' ],
			weekHeader : '週',
			dateFormat : 'yy/mm/dd',
			firstDay : 0,
			isRTL : false,
			showMonthAfterYear : true,
			yearSuffix : '年'
		};
		$.datepicker.setDefaults($.datepicker.regional['ja']);
	});
</script>
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

		<h2 class="login-header">新規会員登録</h2>
		<div id="error" style="color : white;"></div>
		<form class="login-container" method="GET">
			<p>
				<label for="email">メールアドレス</label><input type="text" id="email"
					placeholder="メールアドレス">
			</p>
			<p>
				<label for="username">ユーザーネーム</label><input type="text"
					id="username" placeholder="ユーザーネーム">
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
				<label for="birthday">生年月日</label><input type="text" id="xxdate"
					placeholder="生年月日">
			</p>
			<h2>
				<input type="button" value="登録" onClick="disp()"
					style="background-color: #28d; color: white;">
			</h2>
		</form>


	</div>
	<jsp:include page="include/footer2.jsp" flush="true" />
</body>
</html>