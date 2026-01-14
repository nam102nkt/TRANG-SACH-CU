<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<h2>Duyệt sách bán</h2>

<table id="bookTable">
	<thead>
		<tr>
			<th>ID</th>
			<th>Tên sách</th>
			<th>Người bán</th>
			<th>Giá</th>
			<th>Hành động</th>
		</tr>
	</thead>

	<tbody>
		<c:forEach var="b" items="${books}">
			<tr data-id="${b.id}">
				<td>${b.id}</td>
				<td>${b.title}</td>
				<td>${b.sellerName}</td>
				<td>${b.price}</td>
				<td>
					<form method="post"
						action="${pageContext.request.contextPath}/admin/books/approve">
						<input type="hidden" name="bookId" value="${b.id}" />

						<button type="submit" name="action" value="approve"
							class="btn btn-success">✔ Duyệt</button>

						<button type="submit" name="action" value="reject"
							class="btn btn-danger">✖ Từ chối</button>
					</form>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
