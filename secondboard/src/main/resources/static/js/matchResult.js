function gameResultTemp() {
    Swal.fire({
        title: "결과를 등록하시겠습니까?",
        icon: "success",
        inputType: "submit",
        showDenyButton: true,
    }).then((confirm) => {
        if (confirm.isConfirmed) {
            $("#gameResultTemp").submit()
        } else if (confirm.isDenied) {
            Swal.fire('등록을 취소하였습니다.', '', 'info')
        }
    })
}