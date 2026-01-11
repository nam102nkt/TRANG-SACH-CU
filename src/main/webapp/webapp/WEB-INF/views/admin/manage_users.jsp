<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>Manage Users</h2>
<c:forEach items="${users}" var="u">
    ${u.username} — ${u.active ? "Active" : "Locked"}
    <form method="post">
        <input type="hidden" name="id" value="${u.id}">
        <input type="hidden" name="active" value="${!u.active}">
        <button>${u.active ? "Khóa" : "Mở"}</button>
    </form>
</c:forEach>

</body>
</html>