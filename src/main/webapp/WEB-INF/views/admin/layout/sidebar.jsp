<%@ page contentType="text/html; charset=UTF-8"%>

<div class="admin-sidebar">
	<div class="sidebar-title">ADMIN</div>

	<ul class="sidebar-menu">
		<li><a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard </a></li>
		<li><a href="${pageContext.request.contextPath}/admin/users">Quản lý người dùng </a></li>
		<li><a href="${pageContext.request.contextPath}/admin/books">Duyệt sách bán </a></li>
		<li><a href="${pageContext.request.contextPath}/admin/orders">Đơn hàng </a></li>
		<li><a href="${pageContext.request.contextPath}/admin/books/processed">Sách đã xử lý </a></li>

	</ul>

	<div class="sidebar-logout">
		<a href="${pageContext.request.contextPath}/logout"> Đăng xuất </a>
	</div>
</div>