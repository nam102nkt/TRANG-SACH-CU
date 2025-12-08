<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="header.jsp" />
<div class="container">
  <h1>Giỏ hàng</h1>
  <c:choose>
    <c:when test="${empty sessionScope.cart or sessionScope.cart.size == 0}">
      <p>Giỏ hàng trống.</p>
    </c:when>
    <c:otherwise>
      <form action="cart" method="post">
        <table class="cart-table">
          <tr><th>Ảnh</th><th>Tên</th><th>Giá</th><th>Số lượng</th><th>Tổng</th><th></th></tr>
          <c:forEach var="item" items="${sessionScope.cart.items}">
            <tr>
              <td><img src="${item.book.imageUrl}" width="60"/></td>
              <td>${item.book.title}</td>
              <td><fmt:formatNumber value='${item.book.price}' type='currency' currencySymbol='đ' /></td>
              <td><input type="number" name="qty_${item.book.id}" value="${item.quantity}" min="0"/></td>
              <td><fmt:formatNumber value='${item.total}' type='currency' currencySymbol='đ' /></td>
              <td><a href="cart?action=remove&id=${item.book.id}" class="btn-remove">Xóa</a></td>
            </tr>
          </c:forEach>
        </table>
        <div class="cart-total">
          <h3>Tổng: <fmt:formatNumber value='${sessionScope.cart.total}' type='currency' currencySymbol='đ' /></h3>
          <button type="submit" class="btn">Cập nhật</button>
          <form action="checkout" method="post" style="display:inline">
            <button type="submit" class="btn-buy">Thanh toán</button>
          </form>
        </div>
      </form>
    </c:otherwise>
  </c:choose>
</div>
