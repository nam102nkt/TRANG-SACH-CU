<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Quản lý tài khoản - BookMarket</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/base.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/author.css">
</head>
<body>

	<jsp:include page="/WEB-INF/views/common/header.jsp" />

	<main class="author-page">
    <h1 class="page-title">Danh sách tác giả</h1>

    <div class="author-grid">
        <c:forEach items="${authors}" var="a">
            <div class="author-card">
                <div class="author-avatar">
                    <img src="${pageContext.request.contextPath}/assets/images/user_icon.png" alt="Author">
                </div>
                <div class="author-name">
                    <c:out value="${a.author}" />
                </div>
            </div>
        </c:forEach>
    </div>
</main>

	<jsp:include page="/WEB-INF/views/common/footer.jsp" />

</body>
</html>