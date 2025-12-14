<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Kết Quả Tìm Kiếm</title>
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/homepage.css">
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<header>
</header>




<div class="container">
  <h2>Kết quả tìm kiếm cho: ${q}</h2>
  <c:if test="${empty books}"><p>Không tìm thấy kết quả.</p></c:if>
  <div class="book-grid">
    <c:forEach var="b" items="${books}">
      <div class="book-card">
        <img src="${b.imageUrl}" alt="${b.title}"/>
        <h4><a href="book?id=${b.id}">${b.title}</a></h4>
        <p class="author">${b.author}</p>
        <p class="price">${b.price} đ</p>
      </div>
    </c:forEach>
  </div>
</div>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>