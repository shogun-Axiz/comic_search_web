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
<title>漫画詳細</title>
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
</head>
<body>
	<jsp:include page="include/header.jsp" flush="true" />
	<c:if test="${not empty msg}">
		<pre
			style="color: white; font-size: 24px; white-space: pre-wrap; margin-bottom: 0px; text-align: center;">${msg}</pre>
	</c:if>
	<c:if test="${isSuccess == true}">
		<div class="login" style="width: 80%;">
			<c:forEach var="list" items="${list}">
				<div>
					<img src="${fn:escapeXml(list.image)}" style="float: left;">
					<table border="1" bordercolor="black"
						style="width: 70%; background-color: white; float: right; font-size: 26px;">
						<tr align="center">
							<th>タイトル</th>
							<td>${fn:escapeXml(list.title)}</td>
						</tr>
						<tr border="1" align="center">
							<th>出版社</th>
							<td>${fn:escapeXml(list.publisher)}</td>
						</tr>
						<tr border="1" align="center">
							<th>原作者</th>
							<td>${fn:escapeXml(list.authorName)}</td>
						</tr>
						<tr border="1" align="center">
							<th>カテゴリー</th>
							<td>${fn:escapeXml(list.categoryName)}</td>
						</tr>
						<tr border="1" align="center">
							<th>値段</th>
							<td>${fn:escapeXml(list.price)}円</td>
						</tr>
						<tr border="1" align="center">
							<th>発売日</th>
							<td>${fn:escapeXml(releaseDate)}</td>
						</tr>
						<tr border="1" align="center">
							<th>詳細リンク</th>
							<td style="text-decoration: underline;"><a
								href="${fn:escapeXml(list.link)}" target="_blank">${fn:escapeXml(list.title)}</a></td>
						</tr>
					</table>
					<div style="clear: both;"></div>
				</div>
				<br>
				<br>
				<p style="color: white;">${fn:escapeXml(list.synopsis)}</p>
				<br>
				<br>
			</c:forEach>
		</div>
		<br>
		<br>
		<br>
		<div style="text-align: center;">
			<a href="#" onclick="window.close()"
				style="font-size: 36px; color: white; text-decoration: none; text-decoration: underline">
				ウィンドウを閉じる </a>
		</div>
		</div>
	</c:if>
	<jsp:include page="include/footer.jsp" flush="true" />
</body>
</html>