
function deleteText() {
    const element = document.getElementById('reportText');

    element.innerText = '신고 처리된 게시글입니다.';

}

function checkReport() {
    let reportCount = $("#reportCount").attr("value");
    if (reportCount  >= 2) {
        deleteText();
    };
}

document.addEventListener("DOMContentLoaded", checkReport);

$(document).ready(function () {
    $(".report-btn").click(function () {

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
                const lessonId = $("#lessonId").attr("value");
                console.log(result.value)
                $.ajax({
                    type: "POST",
                    url: "/api/lesson/report",
                    data: {'lessonId': lessonId, 'content': result.value},
                    success: function (data) {
                        if (data === 1) {
                            Swal.fire({
                                icon: 'info',
                                title: '이미 신고된 게시글입니다.'
                            })
                        } else {
                            Swal.fire({
                                icon: 'success',
                                title: '신고되었습니다'
                            }).then(function(confirm) {
                                if (confirm.isConfirmed && data === 2) {
                                    deleteText();
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
    });
});

