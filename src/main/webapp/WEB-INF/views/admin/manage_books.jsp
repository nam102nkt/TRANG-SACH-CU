<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Quản lý tài khoản - BookMarket</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/base.css">
</head>
<body>

	<jsp:include page="/WEB-INF/views/common/header.jsp" />

	<main>
		<h2>Sách chờ duyệt</h2>

		<table border="1">
			<tr>
				<th>Tiêu đề</th>
				<th>Tác giả</th>
				<th>Người bán</th>
				<th>Giá</th>
				<th>Hành động</th>
			</tr>

			<c:forEach items="${pendingBooks}" var="b">
				<tr>
					<td>${b.title}</td>
					<td>${b.author}</td>
					<td>${b.sellerName}</td>
					<td>${b.price}</td>
					<td>
						<form method="post"
							action="${pageContext.request.contextPath}/admin/approve-book">
							<input type="hidden" name="bookId" value="${b.id}">
							<button name="action" value="approve">Duyệt</button>
							<button name="action" value="reject">Từ chối</button>
						</form>
					</td>
				</tr>
			</c:forEach>
		</table>
	</main>

	<jsp:include page="/WEB-INF/views/common/footer.jsp" />

</body>
</html>