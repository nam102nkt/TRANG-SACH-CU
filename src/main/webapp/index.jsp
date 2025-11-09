<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Trang chủ - BOOK MARKET</title>
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/homepage.css">
</head>
<body>

	<header class="site-header">

		<div class="header-top">
			<div class="container">
				<div class="logo-area">
					<a href="${pageContext.request.contextPath}/" class="logo-link">
						<span class="logo-text">BookMarket</span>
					</a>
				</div>

				<div class="search-area">
					<form action="search" method="GET" class="search-form">
						<input type="text" name="query"
							placeholder="Tìm sản phẩm bạn mong muốn..." maxlength= "150">
							<button>
							<img
								src="${pageContext.request.contextPath}/images/search_icon.png"
								alt="Tìm" class="search-icon">
						</button>
					</form>
				</div>

				<div class="user-area">
					<c:if test="${sessionScope.user == null}">
						<a href="login.jsp" class="nav-item"> <img
							src="${pageContext.request.contextPath}/images/user_icon.png"
							alt="Tài khoản" class="nav-icon"> <b>Đăng nhập</b>
						</a>
						<a href="register.jsp" class="nav-item"> <b>Đăng ký</b>
						</a>
					</c:if>
					<c:if test="${sessionScope.user != null}">
						<a href="profile.jsp" class="nav-item"> <img
							src="${pageContext.request.contextPath}/images/user_icon.png"
							alt="Tài khoản" class="nav-icon"> <span><c:out
									value="${sessionScope.user.fullname}" /></span>
						</a>
						<a href="logout" class="nav-item"> <span>Đăng xuất</span>
						</a>
					</c:if>

					<a href="cart.jsp" class="nav-item cart-item"> <img
						src="${pageContext.request.contextPath}/images/cart_icon.png"
						alt="Giỏ hàng" class="nav-icon"> <span class="cart-count">0</span>
					</a>
				</div>
			</div>
		</div>

		<nav class="header-nav">
			<div class="container">
				<div class="nav-links">
					<a href="${pageContext.request.contextPath}/"
						class="nav-item active">Trang chủ</a> <a
						href="products?category=moi" class="nav-item">Sách Mới Nhất</a> <a
						href="products" class="nav-item">Sản phẩm</a> <a href="#"
						class="nav-item">Tác Giả Nổi Bật</a> <a href="#" class="nav-item">Tin
						tức</a> <a href="#" class="nav-item">Liên hệ</a>
				</div>
				<div class="nav-hotline">
					<span>Hotline: <b>1900 6750</b></span>
				</div>
			</div>
		</nav>

	</header>
	<main>

		<section class="main-banner">
			<img src="${pageContext.request.contextPath}/images/main_banner.png"
				alt="KHÁM PHÁ TRI THỨC VIỆT">
		</section>

		<section class="product-section">
		<h2>SÁCH MỚI ĐƯỢC YÊU THÍCH</h2>
			<div class="container">
				
				<div class="book-grid">
					<c:forEach items="${featuredBooks}" var="book">
						<div class="book-card">
							<img src="${book.imageUrl}" alt="${book.title}">
							<h4>
								<c:out value="${book.title}" />
							</h4>
							<p class="author">
								Tác giả:
								<c:out value="${book.author}" />
							</p>
							<p class="price">
								<c:out value="${book.price}" />
								VNĐ
							</p>
							<a href="product-detail?id=${book.id}" class="btn-detail">
								Xem chi tiết </a>
						</div>
					</c:forEach>
					<c:if test="${empty featuredBooks}">
						<p>Hiện chưa có sách nào được đăng bán.</p>
					</c:if>
				</div>
			</div>
		</section>

	</main>

</body>
</html>