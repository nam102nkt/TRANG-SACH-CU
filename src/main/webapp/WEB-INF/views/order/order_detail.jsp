<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Chi tiết đơn hàng - BookMarket</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/base.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/order_detail.css">
</head>
<body>

	<jsp:include page="/WEB-INF/views/common/header.jsp" />

	<div class="order-detail-container">
		<a href="${pageContext.request.contextPath}/profile?tab=orders"
			class="btn-back">← Quay lại danh sách đơn hàng</a>

		<!-- Thông tin đơn hàng + nút hủy -->
		<div class="order-header-actions">
			<div class="order-header">
				<h2>
					Chi tiết đơn hàng #
					<c:out value="${order.id}" />
				</h2>
				<p>
					<b>Ngày đặt:</b>
					<fmt:formatDate value="${order.orderDateAsDate}"
						pattern="dd/MM/yyyy HH:mm" />
				</p>
				<p>
					<b>Trạng thái:</b> <span
						class="status 
                <c:choose>
                    <c:when test='${order.status == "Đã giao"}'>status-success</c:when>
                    <c:when test='${order.status == "Đã hủy"}'>status-cancel</c:when>
                    <c:otherwise>status-pending</c:otherwise>
                </c:choose>">
						${order.status} </span>
				</p>
			</div>

			<div class="order-actions">
				<button type="button" class="btn-cancel" data-order-id="${order.id}">
					Hủy đơn hàng</button>
			</div>
		</div>


		<!-- Thông tin giao hàng -->
		<div class="shipping-info">
			<h3>Thông tin giao hàng</h3>
			<p>
				<b>Người đặt:</b> ${user.fullName}
			</p>
			<p>
				<b>Số điện thoại:</b> ${user.phone}
			</p>
			<p>
				<b>Địa chỉ:</b> ${order.shippingAddress}
			</p>
		</div>

		<!-- Bảng sản phẩm -->
		<h3>Sản phẩm trong đơn hàng</h3>
		<table class="order-table">
			<thead>
				<tr>
					<th>Sản phẩm</th>
					<th>Số lượng</th>
					<th>Đơn giá</th>
					<th>Thành tiền</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="d" items="${order.details}">
					<tr>
						<td>${d.bookTitle}</td>
						<td>${d.quantity}</td>
						<td><fmt:formatNumber value="${d.price}" type="currency"
								currencySymbol="VN₫" /></td>
						<td><fmt:formatNumber value="${d.lineTotal}" type="currency"
								currencySymbol="VN₫" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<div class="order-summary">
			Tổng cộng:
			<fmt:formatNumber value="${order.totalPrice}" type="currency"
				currencySymbol="VN₫" />
		</div>
	</div>

	<jsp:include page="/WEB-INF/views/common/footer.jsp" />

	<!-- SweetAlert2 -->
	<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
	<!-- Gán contextPath cho JS -->
	<script>
		const contextPath = '${pageContext.request.contextPath}';
	</script>
	<script
		src="${pageContext.request.contextPath}/assets/js/cancel_order.js"></script>

</body>
</html>
