<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Trang chủ - BOOK MARKET</title>

<!-- CSS nền tảng -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/base.css">

<!-- CSS riêng cho trang Home -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/home.css">

</head>
<body>

	<jsp:include page="/WEB-INF/views/common/header.jsp" />

	<main>
		<section class="main-banner">
			<img
				src="${pageContext.request.contextPath}/assets/images/main_banner.png"
				alt="KHÁM PHÁ TRI THỨC VIỆT">
		</section>

		<section class="product-section">
			<div class="container">

				<div class="section-header">
					<h2 class="section-title">SÁCH MỚI ĐƯỢC YÊU THÍCH</h2>
					<p class="section-subtitle">Tuyển tập những cuốn sách bán chạy
						nhất tháng</p>
					<div class="title-underline"></div>
				</div>

				<div class="book-grid">
					<c:forEach items="${featuredBooks}" var="book">
						<div class="book-card">
							<div class="card-image">
								<a
									href="${pageContext.request.contextPath}/book-detail?id=${book.id}">
									<img src="${book.imageUrl}" alt="${book.title}">
								</a>
							</div>

							<div class="card-content">
								<h4>
									<a
										href="${pageContext.request.contextPath}/book-detail?id=${book.id}">
										<c:out value="${book.title}" />
									</a>
								</h4>
								<p class="author">
									Tác giả:
									<c:out value="${book.author}" />
								</p>
								<p class="price">
									<c:out value="${book.price}" />
									VNĐ
								</p>
							</div>

							<div class="card-action">
								<a
									href="${pageContext.request.contextPath}/book-detail?id=${book.id}"
									class="btn-detail"> Xem chi tiết </a>
							</div>
						</div>
					</c:forEach>
				</div>

			</div>
		</section>
<section class="author-section">
			<h2>Tác giả nổi bật</h2>
			<div class="author-list">
				<c:forEach items="${topAuthors}" var="a">
					<div class="author-card">
						<div class="author-avatar">
							<img
								src="${pageContext.request.contextPath}/assets/images/user_icon.png">
						</div>
						<div class="author-name">${a.author}</div>
					</div>
				</c:forEach>

			</div>
		</section>
		<div></div>
		<c:forEach items="${booksByCategory}" var="entry">
			<section class="category-section">
				<div class="container">
					<div class="section-header">
						<h2 class="section-title">${entry.key.name}</h2>
						<div class="title-underline"></div>
					</div>

					<div class="book-grid">
						<c:forEach items="${entry.value}" var="book">
							<c:set var="book" value="${book}" scope="request" />
							<jsp:include page="/WEB-INF/views/book/book_card.jsp" />
						</c:forEach>
					</div>

					<div class="view-more-area">
						<a
							href="${pageContext.request.contextPath}/products?category=${entry.key.id}"
							class="btn-view-more">Xem thêm</a>
					</div>
				</div>
			</section>
		</c:forEach>
	</main>

	<jsp:include page="/WEB-INF/views/common/footer.jsp" />

</body>
</html>
