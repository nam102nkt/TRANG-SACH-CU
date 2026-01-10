<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<header class="site-header">

    <div class="header-top">
        <div class="container">

            <div class="logo-area">
                <a href="${pageContext.request.contextPath}/" class="logo-link">
                    <span class="logo-text">BookMarket</span>
                </a>
            </div>

            <div class="search-area">
                <form action="${pageContext.request.contextPath}/search"
                      method="GET" class="search-form">
                    <input type="text" name="query"
                           placeholder="Tìm sản phẩm bạn mong muốn...">
                    <button type="submit">
                        <img src="${pageContext.request.contextPath}/assets/images/search_icon.png"
                             class="search-icon">
                    </button>
                </form>
            </div>

            <div class="user-area">
                <c:if test="${sessionScope.user == null}">
                    <a href="${pageContext.request.contextPath}/login" class="nav-item">
                        <img src="${pageContext.request.contextPath}/assets/images/user_icon.png"
                             class="nav-icon">
                        <b>Đăng nhập</b>
                    </a>
                    <a href="${pageContext.request.contextPath}/register" class="nav-item">
                        <b>Đăng ký</b>
                    </a>
                </c:if>

                <c:if test="${sessionScope.user != null}">
                    <a href="${pageContext.request.contextPath}/profile"
                       class="nav-item">
                        <img src="${pageContext.request.contextPath}/assets/images/user_icon.png"
                             class="nav-icon">
                        <span>${sessionScope.user.fullName}</span>
                    </a>
                    <a href="${pageContext.request.contextPath}/logout"
                       class="nav-item">Đăng xuất</a>
                </c:if>

                <a href="${pageContext.request.contextPath}/cart"
                   class="nav-item cart-item">
                    <img src="${pageContext.request.contextPath}/assets/images/cart_icon.png"
                         class="nav-icon">
                    <span class="cart-count">
                        ${empty sessionScope.cart ? 0 : sessionScope.cart.size}
                    </span>
                </a>
            </div>

        </div>
    </div>

    <nav class="header-nav">
        <div class="container">
            <a href="${pageContext.request.contextPath}/" class="nav-item active">Trang chủ</a>
            <a href="${pageContext.request.contextPath}/products" class="nav-item">Sản phẩm</a>
            <a href="#" class="nav-item">Tác giả</a>
            <a href="#" class="nav-item">Liên hệ</a>
        </div>
    </nav>

</header>