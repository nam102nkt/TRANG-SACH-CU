<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${book.title}</title>

<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/homepage.css">
<link rel="stylesheet" href="css/product_detail.css?v=3">
</head>

<body data-context="${pageContext.request.contextPath}">

    <jsp:include page="header.jsp"></jsp:include>

    <main>
        <div class="detail-container">

            <c:if test="${not empty book}">

                <!-- GALLERY -->
                <div class="product-gallery">
                    <div class="product-gallery-main">
                        <img src="${book.imageUrl}" alt="${book.title}">
                    </div>
                </div>

                <!-- THÔNG TIN -->
                <div class="product-info">

                    <h1 class="product-title">${book.title}</h1>
                    <p class="product-author">Tác giả: ${book.author}</p>
                    <p class="product-price">${book.price} VNĐ</p>

                    <h5>Có thể thay đổi số lượng muốn mua vào ô (mặc định: 1)</h5>

                    <!-- CHỌN SỐ LƯỢNG -->
                    <div class="quantity-selector">
                        <label for="quantity">Số lượng:</label>
                        <input type="number" id="quantity" name="quantity" value="1" min="1">
                    </div>

                    <!-- VÙNG NÚT -->
                    <div class="product-actions">

                        <!-- ❤️ WISHLIST -->
                        <i class="wishlist-heart ${isInWishlist ? 'active' : ''}"
                           data-id="${book.id}"> ❤ </i>

                        <!-- ⚡ MUA NGAY -->
                        <form action="${pageContext.request.contextPath}/payment-confirm"
                              method="GET" class="action-form">
                            <input type="hidden" name="bookId" value="${book.id}">
                            <input type="hidden" id="buy-now-quantity" name="quantity" value="1">
                            <button type="submit" class="btn-buy-now">Mua ngay</button>
                        </form>

                        <!-- 🛒 THÊM GIỎ -->
                        <form action="${pageContext.request.contextPath}/add-to-cart"
                              method="POST" class="action-form">
                            <input type="hidden" name="bookId" value="${book.id}">
                            <input type="hidden" id="cart-quantity" name="quantity" value="1">
                            <button type="submit" class="btn-add-to-cart">Thêm vào giỏ hàng</button>
                        </form>

                    </div>

                    <!-- MÔ TẢ -->
                    <div class="product-description">
                        <h3>Mô tả sản phẩm</h3>
                        <p>${book.description}</p>
                    </div>

                </div>
            </c:if>

            <c:if test="${empty book}">
                <h2>Không tìm thấy sản phẩm</h2>
                <p>Sản phẩm bạn tìm kiếm không tồn tại.</p>
            </c:if>

        </div>
    </main>

    <!-- IMPORT JS -->
    <script src="${pageContext.request.contextPath}/js/product_detail.js?v=3"></script>

</body>
</html>
