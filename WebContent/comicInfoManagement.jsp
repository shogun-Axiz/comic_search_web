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
<title>Comic Search (AdminMode) -漫画情報管理-</title>
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
		<h2 class="login-header">漫画検索</h2>
		<div style="background-color: snow">
			<form class="login-container" action="comicInfoManagement"
				method="GET">
				<p>
					<label for="title">タイトル</label><input type="text" name="title"
						placeholder="タイトル" value="${title}">
				</p>
				<p>
					<label for="authorName">原作者名</label><input type="text"
						name="authorName" placeholder="原作者名" value="${authorName}">
				</p>
				<p>
					<label for="publisher">出版社</label><input type="text"
						name="publisher" placeholder="出版社" value="${publisher}">
				</p>
				<p>
					<label for="category">カテゴリー</label> <select id="catSel"
						name="categoryId" style="width: 100%;">
						<option value="0" selected>全て</option>
						<c:forEach var="cat" items="${cat}">
							<option value="${cat.categoryId }">${cat.categoryName }</option>
						</c:forEach>
					</select>
				</p>
				<p>
					<label for="price">値段(円)</label>
				<div>
					<select id="priceSel1" name="price1"
						style="width: 43%; margin-left: 12px;">
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
					</select> &emsp;～&emsp; <select id="priceSel2" name="price2"
						style="width: 43%;">
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
						style="width: 43%; margin-left: 11px;" name="releaseDate1"
						placeholder="発売日" value="${strReleaseDate1}" /> &emsp;～&emsp; <input
						type="text" id="xxdate2" class="xdate" style="width: 43%"
						name="releaseDate2" placeholder="発売日" value="${strReleaseDate2}" />
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
		<form action="toComicInfoRegistration">
			<h2>
				<p>
					<input type="submit" value="漫画情報登録"
						style="background-color: #28d; color: white;">
				</p>
			</h2>
		</form>
	</div>
	</div>
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
		<p style="text-align: center; color: white;">各項目名をクリックすると並び替えができます（asc
			: 昇順 desc : 降順）</p>
		<br>
		<div id="comic">
			<table border="1" align="center"
				style="width: 90%; background-color: white; font-size: 18px;">
				<thead>
					<tr align="center">
						<th class="sort" data-sort="title"
							style="background-color: #CCFFFF;">タイトル</th>
						<th class="sort" data-sort="category"
							style="background-color: #CCFFFF;">カテゴリー</th>
						<th class="sort" data-sort="publisher"
							style="background-color: #CCFFFF;">出版社</th>
						<th class="sort" data-sort="price"
							style="background-color: #CCFFFF;">値段（円）</th>
						<th class="sort" data-sort="releaseDate"
							style="background-color: #CCFFFF;">発売日</th>
						<th class="sort" data-sort="authorName"
							style="background-color: #CCFFFF;">原作者名</th>
						<th style="background-color: #CCFFFF;"></th>
					</tr>
				</thead>
				<tbody class="list">
					<c:forEach var="list" items="${list}">
						<tr align="center">
							<td class="title">${fn:escapeXml(list.title)}</td>
							<td class="category">${fn:escapeXml(list.categoryName)}</td>
							<td class="auhtorName">${fn:escapeXml(list.authorName)}</td>
							<td class="price">${fn:escapeXml(list.price)}</td>
							<td class="releaseDate">${fn:escapeXml(list.releaseDate)}</td>
							<td class="publisher">${fn:escapeXml(list.publisher)}</td>
							<td><button type="button" class="btn btn-primary"
									onClick="location.href='./comicInfoUpdate?comicId=${list.comicId}'">更新</button>
								<button type="button" class="btn btn-danger"
									onClick="location.href='./comicInfoDelete?comicId=${list.comicId}'">削除</button></td>
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
		valueNames : [ 'title', 'category', 'publisher', 'price',
				'releaseDate', 'authorName' ]
	};
	var comicList = new List('comic', options);

	$(function() {
		$("#catSel").val("${strCategoryId}");
	});

	$(function() {
		$("#priceSel1").val("${strPrice1}");
	});

	$(function() {
		$("#priceSel2").val("${strPrice2}");
	});
</script>

</html>