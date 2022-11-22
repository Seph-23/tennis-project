const clubJoin = (clubId) => {
    const status = $("#clubStatus").attr("value")
    console.log(status)
    if (status == 'END') {
        Swal.fire({
            icon: 'info',
            title: '현재 모집 중이 아닙니다.'
        })
    } else {
        Swal.fire({
            title: "가입하시겠습니까?",
            icon: "success",
            inputType: "submit",
            showDenyButton: true,
        }).then((confirm) => {
            if (confirm.isConfirmed) {
                $.ajax({
                    type: "POST",
                    url: "/club/join",
                    data: {'clubId': clubId},
                    success: function (response) {
                        Swal.fire({
                            icon: 'success',
                            title: '가입되었습니다.'
                        }).then(function (confirm) {
                            window.location.reload()
                        })
                    },
                    error: function (response) {
                        Swal.fire({
                            icon: 'error',
                            title: '오류가 발생했습니다'
                        })
                    }
                });
            } else if (confirm.isDenied) {
                Swal.fire('가입을 취소하였습니다.', '', 'info')
            }
        })
    }
}

