$(document).ready(function () {
    let checkLike = $("#checkLike").attr("value");
    if (checkLike == "is") {
        $(".like").addClass("heart-active");
        $(".heart").addClass("heart-active");
    } else if (checkLike == "not") {
        $(".like").removeClass("heart-active");
        $(".heart").removeClass("heart-active");
    }
    const noticeId = $("#noticeId").attr("value");

    $(".content").on("click", function () {
        $.ajax({
            url: '/notice/like',
            type: 'POST',
            data: {'noticeId': noticeId},
            success: function (data) {
                if (data.result == 1) {
                    $(".like").addClass("heart-active");
                    $(".heart").addClass("heart-active");
                } else {
                    $(".like").removeClass("heart-active");
                    $(".heart").removeClass("heart-active");
                }
                $("#likeCount").text('like '+ data.count);
            },
            error: function () {
            }
        });
    });
});