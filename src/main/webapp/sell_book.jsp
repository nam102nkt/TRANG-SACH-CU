<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="header.jsp" />
<div class="container">
  <h2>Đăng bán sách</h2>
  <c:if test="${not empty error}"><div class="error">${error}</div></c:if>
  <form action="sell" method="post" enctype="multipart/form-data">
    <label>Tiêu đề</label><input type="text" name="title" required/>
    <label>Tác giả</label><input type="text" name="author" required/>
    <label>Giá</label><input type="number" name="price" step="0.01" required/>
    <label>Tình trạng</label>
    <select name="condition"><option value="">--Chọn--</option><option value="new">Mới</option><option value="used">Cũ</option></select>
    <label>Mô tả</label><textarea name="description"></textarea>
    <label>Ảnh</label><input type="file" name="image" accept="image/*"/>
    <button type="submit" class="btn">Đăng bán</button>
  </form>
</div>
