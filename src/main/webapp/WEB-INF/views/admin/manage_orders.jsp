<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>Manage Orders</h2>
<c:forEach items="${orders}" var="o">
    #${o.id} — ${o.status}
    <form method="post">
        <input type="hidden" name="id" value="${o.id}">
        <select name="status">
            <option>PAID</option>
            <option>SHIPPED</option>
            <option>CANCELLED</option>
        </select>
        <button>Cập nhật</button>
    </form>
</c:forEach>

</body>
</html>