<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Xác nhận thanh toán</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/base.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/payment_confirm.css">
</head>
<body>

	<jsp:include page="/WEB-INF/views/common/header.jsp" />

	<main class="checkout-container">

		<h2 class="page-title">Xác nhận đơn hàng</h2>

		<div class="checkout-card">

			<c:if test="${empty sessionScope.cart}">
				<p class="empty-cart">Giỏ hàng trống.</p>
			</c:if>

			<c:if test="${not empty sessionScope.cart}">
				<c:set var="grandTotal" value="0" />

				<table class="cart-table">
					<thead>
						<tr>
							<th>Sản phẩm</th>
							<th>Giá</th>
							<th>Số lượng</th>
							<th>Thành tiền</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${sessionScope.cart.values()}" var="item">
							<tr>
								<td class="product-info"><img src="${item.book.imageUrl}"
									alt="${item.book.title}" class="product-img"> <span>${item.book.title}</span>
								</td>
								<td>${item.book.price}VNĐ</td>
								<td>${item.quantity}</td>
								<td>${item.total}VNĐ</td>
							</tr>

							<c:set var="grandTotal" value="${grandTotal + item.total}" />
						</c:forEach>
					</tbody>
				</table>

				<div class="cart-summary">
					<div class="summary-left">Tổng tiền:</div>
					<div class="summary-right">${grandTotal}VNĐ</div>
				</div>

				<form action="${pageContext.request.contextPath}/place-order"
					method="post" class="checkout-form">

					<label for="shippingAddress">Địa chỉ giao hàng</label> <input
						type="text" id="shippingAddress" name="shippingAddress"
						placeholder="Nhập địa chỉ nhận hàng" required>

					<button type="submit" class="btn-confirm">Xác nhận thanh
						toán</button>
				</form>
			</c:if>

		</div>
	</main>

	<jsp:include page="/WEB-INF/views/common/footer.jsp" />

</body>
</html>
