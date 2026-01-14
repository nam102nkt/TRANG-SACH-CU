<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Quản lý tài khoản - BookMarket</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/base.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/book_list.css">
</head>
<body>

	<jsp:include page="/WEB-INF/views/common/header.jsp" />

	<main>
		<div class="filter-bar">
			<form method="get" action="products">

				<select name="min">
					<option value="">Giá từ</option>
					<option value="0">0</option>
					<option value="50000">50k</option>
					<option value="100000">100k</option>
				</select> <select name="max">
					<option value="">Giá đến</option>
					<option value="50000">50k</option>
					<option value="100000">100k</option>
					<option value="100000000">>100k</option>
				</select> <select name="category">
					<option value="">Thể loại</option>
					<option <c:forEach items="${category}" var="c"> <option value="${c.id}">${c.name}</option> </c:forEach> >
				</select> <select name="condition">
					<option value="">Tình trạng</option>
					<option value="NEW">Mới</option>
					<option value="USED">Cũ</option>
				</select> <select name="sort">
					<option value="">Sắp xếp</option>
					<option value="asc">Giá ↑</option>
					<option value="desc">Giá ↓</option>
				</select>

				<button type="submit">Lọc</button>
			</form>
		</div>

		<div class="book-grid">
			<c:forEach items="${books}" var="b">
				<div class="book-card">
					<img src="${b.imageUrl}">
					<h3>${b.title}</h3>
					<p class="price">${b.price}đ</p>
					<a href="book-detail?id=${b.id}">Chi tiết</a>
				</div>
			</c:forEach>
		</div>

	</main>

	<jsp:include page="/WEB-INF/views/common/footer.jsp" />

</body>
</html>