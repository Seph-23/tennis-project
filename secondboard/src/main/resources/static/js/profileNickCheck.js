
let validationStatus = {
    nickname: ""
}

$("#nickname").on("focusout", function () {
    let nick = {
        nickname: $("#nickname").val()
    }
    if (nick.nickname.length < 4 || nick.nickname.length > 10) {
        $("#nickname_validate").removeAttr("hidden");
        $("#nickname_dup_check").attr("hidden", true);
        validationStatus.nickname = "";
        validationStatusCheck();
    } else {
        $.ajax({
            type: "get",
            url: "/api/members/checkNick",
            data: nick,
            success: function (data) {
                if (data.result === "duplicate") {
                    $("#nickname_dup_check").removeAttr("hidden");
                    $("#nickname_validate").attr("hidden", true);
                    validationStatus.nickname = "";
                    validationStatusCheck();
                } else if (data.result === "ok") {
                    $("#nickname_validate").attr("hidden", true);
                    $("#nickname_dup_check").attr("hidden", true);
                    validationStatus.nickname = "ok";
                    validationStatusCheck();
                }
            },
            error: function () {
            },
        });
    }
});

function validationStatusCheck() {
    if (validationStatus.nickname === "ok") {
        $(".updatePf").removeAttr("disabled");
    } else {
        $(".updatePf").attr("disabled", true);
    }
}