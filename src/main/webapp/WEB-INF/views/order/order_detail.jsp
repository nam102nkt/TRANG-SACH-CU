<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Chi tiết đơn hàng</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/order.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<div class="container">
    <h2>Chi tiết đơn hàng #${order.id}</h2>

    <p><b>Ngày:</b> ${order.orderDate}</p>
    <p><b>Trạng thái:</b> ${order.status}</p>

    <table class="order-table">
        <tr>
            <th>Book ID</th>
            <th>Số lượng</th>
            <th>Đơn giá</th>
            <th>Thành tiền</th>
        </tr>
        <c:forEach var="d" items="${order.details}">
            <tr>
                <td>${d.bookId}</td>
                <td>${d.quantity}</td>
                <td>${d.price} đ</td>
                <td>${d.lineTotal} đ</td>
            </tr>
        </c:forEach>
    </table>

    <div class="order-summary">
        <strong>Tổng cộng: ${order.totalPrice} đ</strong>
    </div>

    <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">Quay lại</a>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>