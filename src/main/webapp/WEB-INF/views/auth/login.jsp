<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Đăng nhập - BookMarket</title>
<%-- Dòng này sẽ "gọi" toàn bộ CSS thiết kế 2 cột của bạn --%>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/base.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/auth.css">
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
	<div class="container-wrapper">

		<div class="image-panel">
			<img
				src="${pageContext.request.contextPath}/assets/images/login_BookMarket.png"
				alt="Hình ảnh cửa hàng sách">
		</div>

		<div class="form-panel">
			<div class="form-card">

				<a class="back-link" href="${pageContext.request.contextPath}/"
					title="Quay về trang chủ"> <img
					src="${pageContext.request.contextPath}/assets/images/home.png"
					alt="Về trang chủ" class="home-icon">
				</a>

				<div class="app-logo">
					<span>BOOK MARKET</span>
				</div>

				<h3>Đăng nhập</h3>
				<p class="subtitle">Chào mừng bạn trở lại!</p>

				<c:if test="${not empty errorMessage}">
					<p class="error-message">
						<c:out value="${errorMessage}" />
					</p>
				</c:if>

				<form action="login" method="POST">

					<div class="input-group">
						<label for="email">Email</label> <input type="email" id="email"
							name="email" placeholder="example@email.com" required>
					</div>

					<div class="input-group">
						<label for="password">Mật khẩu</label> <input type="password"
							id="password" name="password" placeholder="********" required>
					</div>

					<button type="submit">Đăng nhập</button>

					<p class="login-link">
						Chưa có tài khoản? <a
							href="${pageContext.request.contextPath}/register">Đăng ký
							ngay</a>
					</p>
				</form>
			</div>
		</div>
	</div>
</body>
</html>