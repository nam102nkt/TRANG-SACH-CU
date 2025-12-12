<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Thanh Toán Thành Công</title>
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/homepage.css">
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<div class="container success-box">
  <h1>Thanh toán thành công!</h1>
  <c:if test="${not empty orderId}">
    <p>Mã đơn hàng: <strong>${orderId}</strong></p>
  </c:if>
  <a href="index.jsp" class="btn">Về trang chủ</a>
</div>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>