<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="header.jsp" />
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
