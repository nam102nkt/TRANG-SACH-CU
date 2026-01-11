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
	href="${pageContext.request.contextPath}/assets/css/checkout_success.css">
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp" />

	<main class="checkout-container">
		<div class="container">
			<div class="success-box">
				<h2>Thanh toán thành công!</h2>
				<p>Mã đơn hàng: #${orderId}</p>
				<a href="${pageContext.request.contextPath}/"
					class="btn btn-primary">Về trang chủ</a> <a
					href="${pageContext.request.contextPath}/order_detail?orderId=${orderId}"
					class="btn btn-secondary">Xem chi tiết đơn</a>

			</div>
		</div>
	</main>

	<jsp:include page="/WEB-INF/views/common/footer.jsp" />

</body>
</html>