function checkClub() {
    Swal.fire({
        title: "탈퇴하시겠습니까?",
        icon: "success",
        inputType: "submit",
        showDenyButton: true,
    }).then((confirm) => {
        if (confirm.isConfirmed) {
            $("#clubMemberDelete").submit()
        } else if (confirm.isDenied) {
            Swal.fire('탈퇴를 취소하였습니다.', '', 'info')
        }
})
}

$("#visitor-submit").click(function () {
    let newVisitor = {
        content: $("#content").val(),
        clubId: $("#club-id").val()
    }
    Swal.fire({
        title: "방명록을 등록하시겠습니까?",
        icon: "success",
        inputType: "submit",
        showDenyButton: true,
    }).then((confirm) => {
        if (confirm.isConfirmed) {
            $.ajax({
                type: "post",
                async: false,
                url: "/club/visitor/visitorAdd/" + newVisitor.clubId,
                data: newVisitor,
                success: function (data) {
                    if (data.result === "success") {
                        window.location.reload();
                    } else if (data.result === "validate") {
                        Swal.fire("내용은 100자 이내여야 합니다.", '', 'warning');
                    }
                },
                error: function () {
                    Swal.fire({
                        icon: 'error',
                        title: '오류가 발생했습니다'
                    })
                },
            });
        } else if (confirm.isDenied) {
            Swal.fire('등록을 취소하였습니다.', '', 'info')
        }
    });
});


const deleteVisitor = (visitorId) => {
    Swal.fire({
        title: "삭제하시겠습니까?",
        icon: "warning",
        inputType: "submit",
        showDenyButton: true,
    }).then((confirm) => {
        if (confirm.isConfirmed) {
            $.ajax({
                type: "post",
                url: "/club/visitor/visitorDelete/" + visitorId,
                success: function (data) {
                    if (data.result === "success") {
                        window.location.reload();
                    }
                },
                error: function () {
                    Swal.fire({
                        icon: 'error',
                        title: '오류가 발생했습니다'
                    })
                }
            });
        } else if (confirm.isDenied) {
            Swal.fire('삭제를 취소하였습니다.', '', 'info')
        }
    });
};

const updateVisitor = (visitorId) => {
    $("#visitor-content-" + visitorId).prop('disabled', false);
    $("#visitor-update-" + visitorId).attr("hidden", true);
    $("#visitor-delete-" + visitorId).attr("hidden", true);
    $("#visitor-update-confirm-" + visitorId).attr("hidden", false);
    $("#visitor-update-cancel-" + visitorId).attr("hidden", false);
};

const updateVisitorConfirm = (visitorId) => {

    let visitor = {
        content: $("#visitor-content-" + visitorId).val()
    }
    Swal.fire({
        title: "수정하시겠습니까?",
        icon: "success",
        inputType: "update",
        showDenyButton: true,
    }).then((confirm) => {
        if (confirm.isConfirmed) {
            $.ajax({
                type: "post",
                url: "/club/visitor/visitorUpdate/" + visitorId,
                data: visitor,
                success: function (data) {
                    if (data.result === "success") {
                        window.location.reload();
                        $("#visitor-content-" + visitorId).prop('disabled', true);
                        $("#visitor-update-" + visitorId).attr("hidden", false);
                        $("#visitor-delete-" + visitorId).attr("hidden", false);
                        $("#visitor-update-confirm-" + visitorId).attr("hidden", true);
                        $("#visitor-update-cancel-" + visitorId).attr("hidden", true);
                    } else if (data.result === "validate") {
                        Swal.fire("내용은 100자 이내여야 합니다.", '', 'warning');
                    }
                },
                error: function () {
                    Swal.fire({
                        icon: 'error',
                        title: '오류가 발생했습니다'
                    })
                },
            });
        } else if (confirm.isDenied) {
            Swal.fire('수정을 취소하였습니다.', '', 'info')
        }
    });
}

const updateVisitorCancel = (visitorId) => {
    let visitor = {
        content: $("#visitor-content-" + visitorId).val()
    }
    $.ajax({
        type: "post",
        url: "/club/visitor/visitorUpdateCancel/" + visitorId,
        data: visitor,
        success: function (data) {
            if (data.result === "success") {
                window.location.reload();
                $("#visitor-content-" + visitorId).prop('disabled', true);
                $("#visitor-update-" + visitorId).attr("hidden", false);
                $("#visitor-delete-" + visitorId).attr("hidden", false);
                $("#visitor-update-confirm-" + visitorId).attr("hidden", true);
                $("#visitor-update-cancel-" + visitorId).attr("hidden", true);
            }
        },
        error: function () {
            Swal.fire({
                icon: 'error',
                title: '오류가 발생했습니다'
            })
        },
    });
};


