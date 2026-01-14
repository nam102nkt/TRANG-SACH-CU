<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<div class="book-card">
    <div class="card-image">
        <a href="${pageContext.request.contextPath}/book-detail?id=${book.id}">
            <img src="${book.imageUrl}" alt="${book.title}">
        </a>
    </div>

    <div class="card-content">
        <h4>
            <a href="${pageContext.request.contextPath}/book-detail?id=${book.id}">
                ${book.title}
            </a>
        </h4>
        <p class="author">Tác giả: ${book.author}</p>
        <p class="price">${book.price} VNĐ</p>
    </div>

    <div class="card-action">
        <a href="${pageContext.request.contextPath}/book-detail?id=${book.id}" class="btn-detail">
            Xem chi tiết
        </a>
    </div>
</div>