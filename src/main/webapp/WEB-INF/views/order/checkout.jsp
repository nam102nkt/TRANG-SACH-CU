<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Thanh toán</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/order.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<div class="container checkout-box">
    <h2>Xác nhận thanh toán</h2>

    <p>Bạn có chắc muốn thanh toán đơn hàng này?</p>

    <form action="${pageContext.request.contextPath}/checkout" method="post">
        <button type="submit" class="btn btn-primary">Xác nhận thanh toán</button>
        <a href="${pageContext.request.contextPath}/cart" class="btn btn-secondary">Quay lại giỏ hàng</a>
    </form>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>