<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${book.title}</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/base.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/wishlist.css?v=3">
</head>

<body data-context="${pageContext.request.contextPath}">

	<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>

	<main>
		<div class="container">

			<h2>Danh sách yêu thích</h2>

			<c:if test="${empty wishlist}">
				<p class="empty">Bạn chưa có sản phẩm yêu thích nào.</p>
			</c:if>

			<c:if test="${not empty wishlist}">
				<table class="wishlist-table">
					<thead>
						<tr>
							<th>Ảnh</th>
							<th>Tên sách</th>
							<th>Giá</th>
							<th>Thao tác</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${wishlist}" var="w">
							<tr>
								<td><img src="${w.book.imageUrl}" class="thumb"></td>
								<td>${w.book.title}</td>
								<td><fmt:formatNumber value="${w.book.price}"
										type="currency" currencySymbol="VN₫" /></td>
								<td>
									<form method="post" action="wishlist/remove"
										style="display: inline;">
										<input type="hidden" name="bookId" value="${w.book.id}">
										<button type="submit" class="btn-remove">Xóa</button>
									</form>

									<form method="post" action="cart/add" style="display: inline;">
										<input type="hidden" name="bookId" value="${w.book.id}">
										<button type="submit" class="btn-add">Thêm vào giỏ</button>
									</form>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>

		</div>

	</main>
</body>
</html>
