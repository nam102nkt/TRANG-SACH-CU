document.addEventListener("DOMContentLoaded", () => {
    const buttons = document.querySelectorAll(".btn-remove");

    buttons.forEach(btn => {
        btn.addEventListener("click", (e) => {
            e.preventDefault(); // ✅ CHẶN submit form

            let id = btn.getAttribute("data-id");

            fetch(contextPath + "/wishlist", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded",
                    "X-Requested-With": "XMLHttpRequest" // ✅ BÁO AJAX
                },
                body: "action=toggle&bookId=" + id
            })
            .then(r => r.json())
            .then(d => {
                if (d.success) {
                    location.reload(); // OK
                }
            });
        });
    });
});
