<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="css/style.css"> 
<link rel="stylesheet" href="css/homepage.css"> 
<link rel="stylesheet" href="css/product_detail.css"> 
<title>${book.title}</title> </head>
<body>

    <jsp:include page="header.jsp"></jsp:include>

    <main>
        <div class="detail-container">
            
            <c:if test="${not empty book}">
                <div class="product-gallery">
                    <div class="product-gallery-main">
                        <img src="${book.imageUrl}" alt="${book.title}">
                    </div>
                    <div class="product-gallery-thumbnails">
                        </div>
                </div>
                
                <div class="product-info">
                    <h1 class="product-title"><c:out value="${book.title}"></c:out></h1>
                    <p class="product-author">Tác giả: <c:out value="${book.author}"></c:out></p>
                    <p class="product-price"><c:out value="${book.price}"></c:out> VNĐ</p>
                    
                    <!-- <p class="product-availability">
                        Tình trạng: <b>Còn hàng</b>
                    </p> -->
                    <h5>Có thể thay đổi số lượng muốn mua vào ô(mặc định: 1)</h5>
                    <form action="${pageContext.request.contextPath}/add-to-cart" method="POST">
                        <input type="hidden" name="bookId" value="${book.id}">
                        
                        <div class="quantity-selector">
                        	
                            <label for="quantity">Số lượng:</label>
                            <input type="number" id="quantity" name="quantity" value="1" min="1">
                        </div>
                        
                        <div class="product-actions">
                            <a href="#" class="btn-buy-now">Mua ngay</a>
                            
                            <button type="submit" class="btn-add-to-cart">
                                Thêm vào giỏ hàng
                            </button>
                        </div>
                    </form>
                    
                    <div class="product-description">
                        <h3>Mô tả sản phẩm</h3>
                        <p>
                            <c:out value="${book.description}"></c:out>
                        </p>
                    </div>
                </div>
            </c:if>
            
            <c:if test="${empty book}">
                <h2>Không tìm thấy sản phẩm</h2>
                <p>Sản phẩm bạn tìm kiếm không tồn tại.</p>
            </c:if>

        </div>
    </main>
    
    </body>
</html>