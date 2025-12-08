<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
    /* CSS nhúng nhanh cho Footer (bạn có thể chuyển vào style.css sau) */
    .site-footer {
        background-color: #07805b; /* Màu xanh chủ đạo */
        color: white;
        padding: 40px 0;
        margin-top: 50px;
    }
    .footer-content {
        max-width: 1200px;
        margin: 0 auto;
        padding: 0 15px;
        display: flex;
        justify-content: space-between;
    }
    .footer-column h3 { border-bottom: 2px solid #055a40; padding-bottom: 10px; margin-bottom: 20px;}
    .footer-column ul { list-style: none; padding: 0; }
    .footer-column li { margin-bottom: 10px; }
    .footer-column a { color: #e0e0e0; text-decoration: none; }
    .footer-column a:hover { color: white; text-decoration: underline; }
    .copyright { text-align: center; margin-top: 30px; border-top: 1px solid #055a40; padding-top: 20px; font-size: 0.9em;}
</style>

<footer class="site-footer">
    <div class="footer-content">
        <div class="footer-column">
            <h3>Về BookMarket</h3>
            <p>Nơi lưu giữ những giá trị tri thức vượt thời gian.</p>
        </div>
        <div class="footer-column">
            <h3>Hỗ trợ khách hàng</h3>
            <ul>
                <li><a href="#">Trung tâm trợ giúp</a></li>
                <li><a href="#">Hướng dẫn mua hàng</a></li>
            </ul>
        </div>
        <div class="footer-column">
            <h3>Liên hệ</h3>
            <ul>
                <li>Hotline: 1900 6750</li>
                <li>Email: hotro@bookmarket.vn</li>
            </ul>
        </div>
    </div>
    <div class="copyright">
        &copy; 2024 BookMarket. All rights reserved.
    </div>
</footer>