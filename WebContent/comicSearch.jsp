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
		$(".xdate").datepicker({
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
	<jsp:include page="include/header.jsp" flush="true" />
	<div class="login">
		<div class="login-triangle"></div>
		<c:if test="${not empty msg}">
			<p style="color: white;">${msg}</p>
		</c:if>
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
					<label for="category">カテゴリー</label> <select name="categoryId"
						style="width: 100%;">
						<option value="0" selected>全て</option>
						<option value="1">コメディ</option>
						<option value="2">スポーツ</option>
						<option value="3">ドラマ</option>
						<option value="4">ビジネス</option>
						<option value="5">ファンタジー</option>
						<option value="6">ホラー</option>
						<option value="7">ロマンス</option>
						<option value="8">海外コミック</option>
						<option value="9">絵本・童話</option>
						<option value="10">格闘・アクション・冒険</option>
						<option value="11">教養・学習</option>
						<option value="12">推理・ミステリー</option>
						<option value="13">料理・グルメ</option>
						<option value="14">歴史マンガ</option>
					</select>
				</p>
				<p>
					<label for="price">値段(円)</label>
				<div>
					<select name="price1" style="width: 43%; margin-left: 12px;">
						<option value="-1" selected>指定なし</option>
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
					</select> &emsp;～&emsp; <select name="price2" style="width: 43%;">
						<option value="-1" selected>指定なし</option>
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
	<br>

	<c:if test="${isSuccess == true}">
	<h2>
		<p style="text-align: center; color: white;">[検索結果]</p>
	</h2>
	<br>
	<div id="comic">
		<table border="1" align="center"
			style="width: 90%; background-color: white; font-size: 18px;">
			<thead>
				<tr align="center">
					<th class="sort" data-sort="title" style="background-color: #CCFFFF;">タイトル</th>
					<th class="sort" data-sort="category" style="background-color: #CCFFFF;">カテゴリー</th>
					<th class="sort" data-sort="publisher" style="background-color: #CCFFFF;">出版社</th>
					<th class="sort" data-sort="price" style="background-color: #CCFFFF;">値段（円）</th>
					<th class="sort" data-sort="releaseDate" style="background-color: #CCFFFF;">発売日</th>
					<th class="sort" data-sort="authorName" style="background-color: #CCFFFF;">原作者名</th>
				</tr>
			</thead>
			<tbody class="list">
				<c:forEach var="list" items="${list}">
						<tr align="center">
							<td class="title"><a href="./book?comicId=${list.comicId}"
								target="_blank">${fn:escapeXml(list.title)}</a></td>
							<td class="category">${fn:escapeXml(list.categoryName)}</td>
							<td class="auhtorName">${fn:escapeXml(list.authorName)}</td>
							<td class="price">${fn:escapeXml(list.price)}</td>
							<td class="releaseDate">${fn:escapeXml(list.releaseDate)}</td>
							<td class="publisher">${fn:escapeXml(list.publisher)}</td>
						</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	</c:if>
	<jsp:include page="include/footer2.jsp" flush="true" />
</body>
<script src="https://cdnjs.cloudflare.com/ajax/libs/list.js/1.5.0/list.min.js"></script>
<script>
var options = {
  valueNames: [ 'title', 'category', 'publisher', 'price', 'releaseDate', 'authorName' ]
};
var comicList = new List('comic', options);
</script>
</html>