<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Lịch sử đơn hàng</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/base.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/order.css">
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp" />

	<div class="container">
		<h2>Lịch sử đơn hàng</h2>

		<c:if test="${empty orders}">
			<p>Bạn chưa có đơn hàng nào.</p>
		</c:if>

		<c:if test="${not empty orders}">
			<table class="order-table">
				<tr>
					<th>Mã</th>
					<th>Ngày</th>
					<th>Tổng</th>
					<th>Trạng thái</th>
					<th></th>
				</tr>
				<c:forEach var="o" items="${orders}">
					<tr>
						<td>#${o.id}</td>
						<td>${o.orderDate}</td>
						<td>${o.totalPrice}đ</td>
						<td>${o.status}</td>
						<td><a
							href="${pageContext.request.contextPath}/order/detail?id=${o.id}"
							class="btn-link"> Chi tiết </a></td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
	</div>

	<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>