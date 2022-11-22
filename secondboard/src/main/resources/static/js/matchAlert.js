function matchMemberDelete() {
    Swal.fire({
        title: "참가를 취소하시겠습니까?",
        icon: "warning",
        inputType: "submit",
        showDenyButton: true,
    }).then((confirm) => {
        if (confirm.isConfirmed) {
            $("#matchMemberDelete").submit()
        } else if (confirm.isDenied) {
            Swal.fire('요청이 취소되었습니다.', '', 'info')
        }
    })

}