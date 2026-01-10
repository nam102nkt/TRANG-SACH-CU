<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Thanh toán thành công</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/base.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/order.css">
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp" />

	<div class="container success-box">
		<h1>Thanh toán thành công!</h1>

		<c:if test="${not empty orderId}">
			<p>
				Mã đơn hàng: <strong>#${orderId}</strong>
			</p>
		</c:if>

		<a href="${pageContext.request.contextPath}/" class="btn btn-primary">Về
			trang chủ</a> <a href="${pageContext.request.contextPath}/orders"
			class="btn btn-secondary">Xem lịch sử đơn</a>
	</div>

	<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>