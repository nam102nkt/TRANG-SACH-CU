document.addEventListener('DOMContentLoaded', function () {
    const cancelBtn = document.querySelector('.btn-cancel');
    if (!cancelBtn) return;

    cancelBtn.addEventListener('click', function () {
        const orderId = this.dataset.orderId;

        if (!orderId) {
            Swal.fire('Lỗi', 'Không xác định được ID đơn hàng', 'error');
            return;
        }

        // Disable nút để tránh click nhiều lần
        cancelBtn.disabled = true;

        Swal.fire({
            title: 'Bạn có chắc muốn hủy đơn hàng này?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Có, hủy ngay!',
            cancelButtonText: 'Không'
        }).then((result) => {
            if (!result.isConfirmed) {
                cancelBtn.disabled = false; // Enable lại nếu hủy
                return;
            }

            // Gửi AJAX POST
            fetch(`${window.location.origin}${contextPath}/cancel-order`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: `orderId=${orderId}`
            })
            .then(res => {
                if (!res.ok) throw new Error(`Server lỗi: ${res.status}`);
                return res.json();
            })
            .then(data => {
                if (data.success) {
                    Swal.fire('Thành công', data.message, 'success')
                        .then(() => {
                            // Chuyển về trang quản lý đơn hàng
                            window.location.href = `${window.location.origin}${contextPath}/profile?tab=orders`;
                        });
                } else {
                    Swal.fire('Lỗi', data.message || 'Hủy đơn thất bại', 'error');
                    cancelBtn.disabled = false; // Enable lại nút
                }
            })
            .catch(err => {
                console.error(err);
                Swal.fire('Lỗi', 'Có lỗi xảy ra khi hủy đơn.', 'error');
                cancelBtn.disabled = false; // Enable lại nút
            });
        });
    });
});
