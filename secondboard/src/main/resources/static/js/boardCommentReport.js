function checkReport() {
    const report = document.getElementsByClassName("reportCount");
    $(".reportCount[value=" + 2 + "]").addClass("commentReport")
    Array.from(document.getElementsByClassName(
        "commentReport")).forEach(
            cmReport => cmReport.innerText = "신고 처리된 댓글입니다.");
}

document.addEventListener("DOMContentLoaded", checkReport);


const reportComment = (commentId) => {
    var tmp = $("#comment-report-" + commentId).closest(".reportCount");
    console.log(tmp)
        Swal.fire({
            title: '신고 내용을 작성해주세요',
            input: 'text',
            inputAttributes: {
                maxLength: 20,
                required: true
            },
            inputValidator: (value) => {
                if (!value) {
                    return 'You need to write something!'
                } else if (value.length < 5) {
                    return '5자 이상 입력해주세요'
                }
            },
            showCancelButton: true,
            confirmButtonText: '신고하기',
        }).then(function (result) {
            if (result.isConfirmed) {
                $.ajax({
                    type: "POST",
                    url: "/api/comments/report",
                    data: {'commentId': commentId, 'content': result.value},
                    success: function (data) {
                        if (data === 1) {
                            Swal.fire({
                                icon: 'info',
                                title: '이미 신고된 댓글입니다.'
                            })
                        } else {
                            Swal.fire({
                                icon: 'success',
                                title: '신고되었습니다'
                            }).then(function(confirm) {
                                if (confirm.isConfirmed && data === 2) {
                                 $("#comment-report-" + commentId).closest(".reportCount").addClass("clickedComment")
                                    Array.from(document.getElementsByClassName(
                                        "clickedComment")).forEach(
                                        cmReport => cmReport.innerText = "신고 처리된 댓글입니다.");
                                }
                            })
                        }
                    },
                    error: function (data) {
                        Swal.fire({
                            icon: 'error',
                            title: '오류가 발생했습니다'
                        })
                    }
                });
            }
        })
    };

