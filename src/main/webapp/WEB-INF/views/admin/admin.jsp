<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ADMIN DASHBOARD</title>
</head>
<body>
<h1>ADMIN DASHBOARD</h1>

<div>
    <p>Total Users: ${totalUsers}</p>
    <p>Total Books: ${totalBooks}</p>
    <p>Total Orders: ${totalOrders}</p>
</div>

<h2>Sách chờ duyệt</h2>

<table border="1">
    <tr>
        <th>Title</th>
        <th>Seller</th>
        <th>Action</th>
    </tr>

    <c:forEach var="b" items="${pendingBooks}">
        <tr>
            <td>${b.title}</td>
            <td>${b.sellerId}</td>
            <td>
                <form action="${pageContext.request.contextPath}/admin/book/approve" method="post">
                    <input type="hidden" name="id" value="${b.id}">
                    <button name="action" value="approve">Duyệt</button>
                    <button name="action" value="reject">Từ chối</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>