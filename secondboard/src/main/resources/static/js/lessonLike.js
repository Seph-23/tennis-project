$(document).ready(function () {
    let checkLike = $("#checkLike").attr("value");
    if (checkLike == "is") {
        $(".like").addClass("heart-active");
        $(".heart").addClass("heart-active");
    } else if (checkLike == "not") {
        $(".like").removeClass("heart-active");
        $(".heart").removeClass("heart-active");
    }
    const lessonId = $("#lessonId").attr("value");

    $(".content").on("click", function () {
        $.ajax({
            url: '/api/lesson/like',
            type: 'POST',
            data: {'lessonId': lessonId},
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