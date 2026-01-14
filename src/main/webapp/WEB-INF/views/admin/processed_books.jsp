<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<h2>Sách đã xử lý</h2>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Tên sách</th>
        <th>Giá</th>
        <th>Trạng thái</th>
    </tr>
    </thead>

    <tbody>
    <c:forEach var="b" items="${books}">
        <tr>
            <td>${b.id}</td>
            <td>${b.title}</td>
            <td>${b.price}</td>
            <td>${b.status}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>