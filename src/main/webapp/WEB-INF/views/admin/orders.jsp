<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h2>Quản lý đơn hàng</h2>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Khách hàng</th>
        <th>Tổng tiền</th>
        <th>Trạng thái</th>
    </tr>
    </thead>

    <tbody>
    <c:forEach var="o" items="${orders}">
        <tr>
            <td>${o.id}</td>
            <td>${o.userEmail}</td>
            <td>${o.total}</td>
            <td>${o.status}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>