<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Đăng ký tài khoản</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/base.css">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/auth.css">
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
	<div class="container-wrapper">
		<div class="image-panel">
			<img src="${pageContext.request.contextPath}/assets/images/register_BookMarket.png"
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

				<h3>Đăng ký tài khoản</h3>
				<p class="subtitle">Tạo tài khoản mới để bắt đầu</p>

				<c:if test="${not empty errorMessage}">
					<p class="error-message">
						<c:out value="${errorMessage}" />
					</p>
				</c:if>

				<form action="register" method="POST">
					<div class="input-group">
						<label for="fullname">Họ và tên</label> <input type="text"
							id="fullname" name="fullname" placeholder="Nguyễn Văn A" required>
					</div>
					<div class="input-group">
						<label for="email">Email</label> <input type="email" id="email"
							name="email" placeholder="example@email.com" required>
					</div>
					<div class="input-group">
						<label for="phone">Số điện thoại</label> <input type="text"
							id="phone" name="phone" placeholder="0123456789">
					</div>
					<div class="input-group">
						<label for="password">Mật khẩu</label> <input type="password"
							id="password" name="password" placeholder="********" required>
					</div>
					<div class="input-group">
						<label for="confirm-password">Nhập lại mật khẩu</label> <input
							type="password" id="confirm-password" name="confirmPassword"
							placeholder="********" required>
					</div>
					<div class="input-group">
						<label>Bạn muốn đăng ký vai trò gì?</label>
						<div class="role-selection"
							style="display: flex; gap: 20px; margin-top: 5px;">
							<label style="cursor: pointer;"> <input type="radio"
								name="role" value="BUYER" checked> Người mua (Chỉ mua
								sách)
							</label> <label style="cursor: pointer;"> <input type="radio"
								name="role" value="SELLER"> Người bán (Được đăng ký gửi
								sách)
							</label>
						</div>
					</div>

					<button type="submit">Đăng ký</button>

					<p class="login-link">
						Đã có tài khoản? <a href="${pageContext.request.contextPath}/login">Đăng nhập ngay</a>
					</p>
				</form>
			</div>
		</div>
	</div>
</body>
</html>