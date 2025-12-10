<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<meta charset="UTF-8">
<title>Lịch Sử Đơn Hàng</title>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="header.jsp" />
<div class="container">
  <h2>Lịch sử đơn hàng</h2>
  <c:if test="${empty orders}"><p>Bạn chưa có đơn hàng nào.</p></c:if>
  <table class="cart-table">
    <tr><th>Mã</th><th>Tổng</th><th>Trạng thái</th></tr>
    <c:forEach var="o" items="${orders}">
      <tr>
        <td>${o.id}</td>
        <td>${o.totalPrice} đ</td>
        <td>${o.status}</td>
      </tr>
    </c:forEach>
  </table>
</div>
