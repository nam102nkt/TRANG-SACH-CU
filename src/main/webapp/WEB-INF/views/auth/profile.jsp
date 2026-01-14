<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Quản lý tài khoản - BookMarket</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/base.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/profile.css">
</head>
<body>

	<jsp:include page="/WEB-INF/views/common/header.jsp" />

	<main>
		<div class="profile-container">

			<aside class="profile-sidebar">
				<div class="profile-user-info">
					<img src="${pageContext.request.contextPath}/assets/images/user_icon.png"
						alt="Avatar" class="profile-avatar">
					<div class="profile-name">
						<c:out value="${sessionScope.user.fullName}" />
					</div>
				</div>
				<nav class="profile-menu">

					<a href="profile?tab=info"
						class="${currentTab == 'info' ? 'active' : ''}"> Hồ sơ của tôi
					</a> 
					<c:if test="${sessionScope.user.role eq 'SELLER'}">
        				<a href="${pageContext.request.contextPath}/seller/book">Bán sách</a>
    				</c:if> 
					<a href="profile?tab=orders" class="${currentTab == 'orders' ? 'active' : ''}"> Quản lý đơn hàng </a> 
					<a href="wishlist">Danh sách yêu thích</a> 
					<a href="profile?tab=password" class="${currentTab == 'password' ? 'active' : ''}"> Đổi mật khẩu </a> 
					<a href="logout" style="color: red;">Đăng xuất</a>

				</nav>
			</aside>

			<section class="profile-content">

				<c:if test="${currentTab == 'info'}">
					<div class="profile-header">
						<h2>Hồ sơ của tôi</h2>
						<p>Quản lý thông tin hồ sơ để bảo mật tài khoản</p>
					</div>
					<form action="profile" method="POST">
						<div class="form-group">
							<label>Họ và tên</label> <input type="text" name="fullname"
								class="form-control" value="${sessionScope.user.fullName}"
								required>
						</div>
						<div class="form-group">
							<label>Email</label> <input type="text" class="form-control"
								value="${sessionScope.user.email}" disabled>
						</div>
						<div class="form-group">
							<label>Số điện thoại</label> <input type="text" name="phone"
								class="form-control" value="${sessionScope.user.phone}">
						</div>
						<button type="submit" class="btn-save">Lưu thay đổi</button>
					</form>
				</c:if>

			<c:if test="${currentTab == 'orders'}">
    <div class="profile-header">
        <h2>Đơn hàng của tôi</h2>
        <p>Xem lại lịch sử mua hàng</p>
    </div>

    <table class="order-table">
        <thead>
            <tr>
                <th>Mã đơn hàng</th>
                <th>Ngày đặt</th>
                <th>Địa chỉ giao hàng</th>
                <th>Giá trị</th>
                <th>Trạng thái</th>
                <th>Hành động</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${orders}" var="o">
                <tr>
                    <td><b>#${o.id}</b>
                        <c:if test="${sessionScope.latestOrderId == o.id}">
                            <span class="new-order-badge">Mới</span>
                        </c:if>
                    </td>
                 <td><fmt:formatDate value="${o.orderDateAsDate}" pattern="dd/MM/yyyy HH:mm" /></td>
<td>${o.shippingAddress}</td>

                    <td><fmt:formatNumber value="${o.totalPrice}" type="currency" currencySymbol="VN₫" /></td>
                    <td>
                        <span class="status-badge ${o.status == 'Đã giao' ? 'status-success' : 'status-pending'}">
                            ${o.status}
                        </span>
                    </td>
                    <td>
                        <a href="order_detail?orderId=${o.id}" class="btn-view">Xem chi tiết</a>
                    </td>
                </tr>
            </c:forEach>

            <c:if test="${empty orders}">
                <tr>
                    <td colspan="6" style="text-align: center;">Bạn chưa có đơn hàng nào.</td>
                </tr>
            </c:if>
        </tbody>
    </table>
</c:if>

				<c:if test="${currentTab == 'password'}">
					<div class="profile-header">
						<h2>Đổi mật khẩu</h2>
						<p>Vui lòng nhập mật khẩu hiện tại để thay đổi</p>
					</div>
					<form action="change-password" method="POST">
						<div class="form-group">
							<label>Mật khẩu hiện tại</label> <input type="password"
								name="currentPass" class="form-control" required>
						</div>
						<div class="form-group">
							<label>Mật khẩu mới</label> <input type="password" name="newPass"
								class="form-control" required>
						</div>
						<div class="form-group">
							<label>Xác nhận mật khẩu mới</label> <input type="password"
								name="confirmPass" class="form-control" required>
						</div>
						<button type="submit" class="btn-save">Cập nhật mật khẩu</button>
					</form>
				</c:if>

				<c:if test="${currentTab == 'favorites'}">
					<div class="profile-header">
						<h2>Sản phẩm yêu thích</h2>
					</div>
					<p>Tính năng đang phát triển...</p>
				</c:if>

			</section>
		</div>

	</main>

	<jsp:include page="/WEB-INF/views/common/footer.jsp" />

</body>
</html>