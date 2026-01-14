<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Danh sách yêu thích</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/base.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/wishlist.css">
</head>

<body data-context="${pageContext.request.contextPath}">


	<jsp:include page="/WEB-INF/views/common/header.jsp" />

	<main class="wishlist-page">
		<div class="container">

			<c:if test="${empty books}">
				<p class="empty">Bạn chưa có sản phẩm yêu thích nào.</p>
			</c:if>

			<c:if test="${not empty books}">
				<main class="wishlist-page">
					<div class="container">
						<h2>Danh sách yêu thích</h2>

						<c:if test="${empty books}">
							<p class="empty">Bạn chưa có sản phẩm yêu thích nào.</p>
						</c:if>

						<c:if test="${not empty books}">
							<div class="wishlist-grid">
								<c:forEach items="${books}" var="b">
									<div class="wishlist-card">

										<a
											href="${pageContext.request.contextPath}/book-detail?id=${b.id}">
											<img src="${b.imageUrl}" alt="${b.title}">
										</a>

										<h3>
											<a
												href="${pageContext.request.contextPath}/book-detail?id=${b.id}">
												${b.title} </a>
										</h3>

										<p class="author">Tác giả: ${b.author}</p>

										<p class="price">
											<fmt:formatNumber value="${b.price}" type="number" />
											VNĐ
										</p>

										<button type="button" class="btn-remove" data-id="${b.id}">
											❌ Xóa</button>

									</div>
								</c:forEach>
							</div>
						</c:if>
					</div>
				</main>

			</c:if>

		</div>
	</main>
	<script src="${pageContext.request.contextPath}/assets/js/wishlist.js"></script>

</body>
</html>
