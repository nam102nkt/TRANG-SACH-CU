<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h2>Quản lý người dùng</h2>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Email</th>
        <th>Role</th>
        <th>Trạng thái</th>
        <th>Hành động</th>
    </tr>
    </thead>

    <tbody>
    <c:forEach var="u" items="${users}">
        <tr>
            <td>${u.id}</td>
            <td>${u.email}</td>
            <td>${u.role}</td>
            <td>${u.status}</td>
            <td>
                <form method="post"
                      action="${pageContext.request.contextPath}/admin/users/toggle">

                    <input type="hidden" name="userId" value="${u.id}"/>

                    <c:choose>
                        <c:when test="${u.status == 'ACTIVE'}">
                            <button class="btn btn-warning">Khóa</button>
                        </c:when>
                        <c:otherwise>
                            <button class="btn btn-success">Mở</button>
                        </c:otherwise>
                    </c:choose>

                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>