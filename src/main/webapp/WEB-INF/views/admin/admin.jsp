<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Panel</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/css/admin.css">
</head>
<body>

<div class="admin-wrapper">

    <!-- SIDEBAR -->
    <jsp:include page="layout/sidebar.jsp"/>

    <!-- CONTENT -->
    <div class="admin-content">
        <jsp:include page="${contentPage}"/>
    </div>

</div>

</body>
</html>