<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Giỏ hàng của bạn</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/base.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/cart.css">
</head>
<body>

	<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>

	<main>
		<div class="cart-container">
			<h2>Giỏ hàng của bạn</h2>

			<c:if test="${empty sessionScope.cart}">
				<p>Giỏ hàng của bạn đang trống.</p>
				<a href="${pageContext.request.contextPath}/" class="btn-detail"
					style="font-size: 25px"> MUA SẮM NGAY</a>
			</c:if>

			<c:if test="${not empty sessionScope.cart}">
				<table class="cart-table">
					<thead>
						<tr>
							<th colspan="2">Sản phẩm</th>
							<th>Đơn giá</th>
							<th>Số lượng</th>
							<th>Thành tiền</th>
							<th>Xóa</th>
						</tr>
					</thead>
					<tbody>

						<c:set var="grandTotal" value="0" />

						<c:forEach items="${sessionScope.cart.values()}" var="item">
							<tr>
								<td><img src="${item.book.imageUrl}"
									alt="${item.book.title}" class="cart-item-image"></td>
								<td>${item.book.title}</td>
								<td>${item.book.price}VNĐ</td>
								<td class="quantity-cell">
									<form action="update_cart" method="post"
										style="display: inline;">
										<input type="hidden" name="bookId" value="${item.book.id}">
										<input type="hidden" name="action" value="dec">
										<button class="qty-btn">−</button>
									</form> <span class="qty-number">${item.quantity}</span>

									<form action="update_cart" method="post"
										style="display: inline;">
										<input type="hidden" name="bookId" value="${item.book.id}">
										<input type="hidden" name="action" value="inc">
										<button class="qty-btn">+</button>
									</form>
								</td>

								<td><b style="color: black;">${item.total} VNĐ</b></td>
								<td><a href="remove_from_cart?bookId=${item.book.id}"
									class="remove-item-link" style="color: red;">Xóa</a></td>
							</tr>

							<c:set var="grandTotal" value="${grandTotal + item.total}" />


						</c:forEach>
					</tbody>
				</table>

				<div class="cart-total">
					<b>Tổng cộng: ${grandTotal} VNĐ</b>
				</div>
				<div class="cart-actions">
					<a href="${pageContext.request.contextPath}/" class="btn-detail">Tiếp
						tục mua sắm</a> <a href="${pageContext.request.contextPath}/checkout"
						class="btn-detail">Tiến hành Thanh toán</a>
				</div>
			</c:if>

		</div>
	</main>
	<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
</body>
</html>