<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Comic Search -漫画検索-</title>
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

<script>
	$(function() {
	  $(".xdate").datepicker( {
	    changeYear: true,  // 年選択をプルダウン化
	    changeMonth: true , // 月選択をプルダウン化
	    yearRange: "-50:+1"
	  } );

	  // 日本語化
	  $.datepicker.regional['ja'] = {
	    closeText: '閉じる',
	    prevText: '<前',
	    nextText: '次>',
	    currentText: '今日',
	    monthNames: ['1月','2月','3月','4月','5月','6月',
	    '7月','8月','9月','10月','11月','12月'],
	    monthNamesShort: ['1月','2月','3月','4月','5月','6月',
	    '7月','8月','9月','10月','11月','12月'],
	    dayNames: ['日曜日','月曜日','火曜日','水曜日','木曜日','金曜日','土曜日'],
	    dayNamesShort: ['日','月','火','水','木','金','土'],
	    dayNamesMin: ['日','月','火','水','木','金','土'],
	    weekHeader: '週',
	    dateFormat: 'yy/mm/dd',
	    firstDay: 0,
	    isRTL: false,
	    showMonthAfterYear: true,
	    yearSuffix: '年'};
	  $.datepicker.setDefaults($.datepicker.regional['ja']);
	});
	</script>
</head>
<body>
	<jsp:include page="include/header.jsp" flush="true" />
	<div class="login">
		<div class="login-triangle"></div>

		<h2 class="login-header">漫画検索</h2>
		<div style="background-color: snow">
			<form class="login-container" action="comicSearch" method="GET">
				<p>
					<label for="title">タイトル</label><input type="text" name="title"
						placeholder="タイトル">
				</p>
				<p>
					<label for="authorName">原作者名</label><input type="text"
						name="authorName" placeholder="原作者名">
				</p>
				<p>
					<label for="publisher">出版社</label><input type="text"
						name="publisher" placeholder="出版社">
				</p>
				<p>
					<label for="category">カテゴリー</label> <select name="カテゴリー"
						style="width: 100%;">
						<option value="" selected>全て</option>
						<option value="コメディ">コメディ</option>
						<option value="スポーツ">スポーツ</option>
						<option value="ドラマ">ドラマ</option>
						<option value="ビジネス">ビジネス</option>
						<option value="ファンタジー">ファンタジー</option>
						<option value="ホラー">ホラー</option>
						<option value="ロマンス">ロマンス</option>
						<option value="海外コミック">海外コミック</option>
						<option value="絵本・童話">絵本・童話</option>
						<option value="格闘・アクション・冒険">格闘・アクション・冒険</option>
						<option value="教養・学習">教養・学習</option>
						<option value="推理・ミステリー">推理・ミステリー</option>
						<option value="料理・グルメ">料理・グルメ</option>
						<option value="歴史マンガ">歴史マンガ</option>
					</select>
				</p>
				<p>
					<label for="price">値段(円)</label>
				<div>
					<select name="値段" style="width: 43%; margin-left: 12px;">
						<option value="" selected>指定なし</option>
						<option value="0">0</option>
						<option value="100">100</option>
						<option value="200">200</option>
						<option value="300">300</option>
						<option value="400">400</option>
						<option value="500">500</option>
						<option value="600">600</option>
						<option value="700">700</option>
						<option value="800">800</option>
						<option value="900">900</option>
						<option value="1000">1000</option>
					</select> &emsp;～&emsp; <select name="値段" style="width: 43%;">
						<option value="" selected>指定なし</option>
						<option value="0">0</option>
						<option value="100">100</option>
						<option value="200">200</option>
						<option value="300">300</option>
						<option value="400">400</option>
						<option value="500">500</option>
						<option value="600">600</option>
						<option value="700">700</option>
						<option value="800">800</option>
						<option value="900">900</option>
						<option value="1000">1000</option>
					</select>
				</div>
				</p>
				<p>
					<label for="releaseDate">発売日</label>
				<div>
					<input type="text" id="xxdate1" class="xdate"
						style="width: 44%; margin-left: 11px;" name="releaseDate1"
						placeholder="発売日" />&emsp;～&emsp;<input type="text" id="xxdate2"
						class="xdate" style="width: 44%" name="releaseDate2"
						placeholder="発売日" />
					<div>
						</p>
						<h2>
							<p>
								<input type="submit" value="検索"
									style="background-color: #28d; color: white;">
							</p>
						</h2>
			</form>
		</div>
	</div>
	</div>
	</div>
	<jsp:include page="include/footer2.jsp" flush="true" />
</body>
</html>