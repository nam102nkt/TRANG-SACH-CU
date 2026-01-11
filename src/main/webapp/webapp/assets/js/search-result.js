document.addEventListener("DOMContentLoaded", () => {
    const cards = document.querySelectorAll(".book-card");
    const contextPath = document.body.dataset.context || "";

    cards.forEach(card => {
        card.addEventListener("click", () => {
            const id = card.dataset.id;
            if (id) {
                window.location.href =
                    contextPath + "/book-detail?id=" + id;
            }
        });
    });
});
