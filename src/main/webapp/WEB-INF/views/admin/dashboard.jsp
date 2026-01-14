<%@ page contentType="text/html; charset=UTF-8" %>

<h2>Dashboard</h2>

<div class="dashboard">
    <div class="card blue">
        <h3>Tổng User</h3>
        <p>${userCount}</p>
    </div>

    <div class="card green">
        <h3>Số lượng sách</h3>
        <p>${bookCount}</p>
    </div>

    <div class="card orange">
        <h3>Đơn hàng</h3>
        <p>${orderCount}</p>
    </div>

    <div class="card red">
        <h3>Doanh thu</h3>
        <p>${revenue}</p>
    </div>
</div>