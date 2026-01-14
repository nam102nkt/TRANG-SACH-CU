
// ====== ĐỒNG BỘ SỐ LƯỢNG ======
const q = document.getElementById("quantity");
const cart = document.getElementById("cart-quantity");

if (q && cart) {
    q.addEventListener("input", () => {
        cart.value = q.value;
    });
}

// ====== TOAST ======
function showToast(msg) {
    const toast = document.createElement("div");
    toast.className = "toast-msg";
    toast.innerText = msg;
    document.body.appendChild(toast);

    setTimeout(() => toast.classList.add("show"), 10);
    setTimeout(() => {
        toast.classList.remove("show");
        setTimeout(() => toast.remove(), 300);
    }, 2000);
}

// ====== WISHLIST ======
document.querySelectorAll(".wishlist-heart").forEach(heart => {
    heart.addEventListener("click", function () {
        const bookId = this.dataset.id;

        fetch(contextPath + "/wishlist", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: `action=toggle&bookId=${bookId}`
        })
        .then(res => res.json())
        .then(data => {
            if (!data.success) {
                showToast(data.message || "Bạn cần đăng nhập");
                return;
            }
            this.classList.toggle("active", data.status === "added");
            showToast(data.message);
        })
        .catch(() => showToast("Lỗi wishlist"));
    });
});
