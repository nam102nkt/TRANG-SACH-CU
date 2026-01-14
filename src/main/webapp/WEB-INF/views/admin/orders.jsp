<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<style>
.paid-row {
    opacity: 0.5;
}
</style>

<h2>Quản lý đơn hàng</h2>

<table>
	<tr>
		<th>ID</th>
		<th>Tên khách hàng</th>
		<th>Tổng tiền</th>
		<th>Trạng thái</th>
		<th>Hành động</th>
	</tr>

	<c:forEach var="o" items="${orders}">
		<tr class="${o.status == 'PAID' ? 'paid-row' : ''}">
		<tr>
			<td>${o.id}</td>
			<td>${o.fullName}</td>
			<td>${o.totalPrice}</td>
			<td>${o.status}</td>

			<td><c:choose>
					<c:when test="${o.status == 'PENDING'}">
						<form method="post"
							action="${pageContext.request.contextPath}/admin/orders/confirm">
							<input type="hidden" name="orderId" value="${o.id}" />
							<button class="btn btn-success">Xác nhận thanh toán</button>
						</form>
					</c:when>
					<c:otherwise>
						<button class="btn btn-secondary" disabled>Đã thanh toán</button>
					</c:otherwise>
				</c:choose></td>
		</tr>
	</c:forEach>
</table>