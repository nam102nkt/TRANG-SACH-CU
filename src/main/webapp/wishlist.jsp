<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<meta charset="UTF-8">
<title>Danh Sách Yêu Thích</title>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="header.jsp" />
<div class="container">
  <h1>Yêu thích</h1>
  <c:choose>
    <c:when test="${empty sessionScope.wl}">
      <p>Chưa có sách yêu thích.</p>
    </c:when>
    <c:otherwise>
      <div class="book-grid">
        <c:forEach var="id" items="${sessionScope.wl}">
          
          <c:if test="${empty book}">
            <!-- fallback: try to fetch via BookDAOImpl in servlet (recommended) -->
            <div>Book id: ${id} - <a href="book?id=${id}">Xem</a> - <a href="wishlist?action=remove&id=${id}">Xóa</a></div>
          </c:if>
          <c:if test="${not empty book}">
            <div class="book-card">
              <img src="${book.imageUrl}" width="120"/>
              <h3><a href="book?id=${book.id}">${book.title}</a></h3>
              <p>${book.author}</p>
              <p class="price"><fmt:formatNumber value='${book.price}' type='currency' currencySymbol='đ' /></p>
              <a href="wishlist?action=remove&id=${book.id}" class="btn-remove">Xóa</a>
            </div>
          </c:if>
        </c:forEach>
      </div>
    </c:otherwise>
  </c:choose>
</div>
