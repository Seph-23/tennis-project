$("#visitor-submit").click(function () {
    let newVisitor = {
        content: $("#content").val(),
        clubId: $("#club-id").val()
    }
    Swal.fire({
        title: "댓글을 등록하시겠습니까?",
        icon: "success",
        inputType: "submit",
        buttons: {
            confirm: true,
            cancel: true
        },
    }).then(function (confirm) {
        if (confirm) {
            $.ajax({
                type: "post",
                async: false,
                url: "/club/visitor/visitorAdd/" + newVisitor.clubId,
                data: newVisitor,
                success: function (data) {
                    if (data.result === "success") {
                        window.location.reload();
                    } else if (data.result === "validate") {
                        alert("댓글 길이는 1 ~ 100자 이내여야 합니다.");
                    }
                },
                error: function () {
                    alert("서버 에러!");
                },
            });
        } else {
            return false;
        }
    });
});


const deleteVisitor = (visitorId) => {
    Swal.fire({
        title: "삭제하시겠습니까?",
        icon: "warning",
        inputType: "submit",
        buttons: {
            confirm: true,
            cancel: true
        },
    }).then(function (confirm) {
        if (confirm) {
            $.ajax({
                type: "post",
                url: "/club/visitor/visitorDelete/" + visitorId,
                success: function (data) {
                    if (data.result === "success") {
                        window.location.reload();
                    }
                },
                error: function () {
                    alert("서버 에러!");
                },});
        } else {
            return false;
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
        buttons: {
            confirm: true,
            cancel: true
        },
    }).then(function (confirm) {
        if (confirm) {
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
                        alert("댓글은 1 ~ 100 자 이내여야 합니다.");
                    }
                },
                error: function () {
                    alert("서버 에러!");
                },
            });
        } else {
            return false;
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
            alert("서버 에러!");
        },
    });
};


