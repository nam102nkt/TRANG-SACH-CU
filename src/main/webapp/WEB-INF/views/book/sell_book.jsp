<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng bán sách cũ</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/sell.css">
</head>

<body>

<c:if test="${sessionScope.user == null || sessionScope.user.role ne 'SELLER'}">
    <c:redirect url="/login"/>
</c:if>
<c:if test="${not empty successMessage}">
    <div class="success">${successMessage}</div>
</c:if>


<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<div class="sell-form">
    <h2>Đăng bán sách</h2>

    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/seller/book"
          method="post"
          enctype="multipart/form-data">

        <div class="sell-row">
            <div>
                <label>Tiêu đề</label>
                <input type="text" name="title" required>
            </div>
            <div>
                <label>Tác giả</label>
                <input type="text" name="author" required>
            </div>
        </div>

        <div class="sell-row">
            <div>
                <label>Giá (VNĐ)</label>
                <input type="number" name="price" step="0.01" required>
            </div>
            <div>
                <label>Tình trạng</label>
                <select name="condition" required>
                    <option value="">-- Chọn --</option>
                    <option value="NEW">Mới</option>
                    <option value="USED">Cũ</option>
                </select>
            </div>
        </div>

        <label>Thể loại</label>
        <select name="categoryId" required>
            <option value="">-- Chọn --</option>
            <c:forEach items="${categories}" var="c">
                <option value="${c.id}">${c.name}</option>
            </c:forEach>
        </select>

        <label>Mô tả</label>
        <textarea name="description" placeholder="Mô tả chi tiết về cuốn sách..."></textarea>

        <label>Ảnh bìa</label>
        <input type="file" name="image" accept="image/*">

        <button type="submit">Đăng bán</button>
    </form>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
<script>
setTimeout(() => {
    const msg = document.querySelector('.success');
    if (msg) msg.style.display = 'none';
}, 5000);
</script>
</body>
</html>
