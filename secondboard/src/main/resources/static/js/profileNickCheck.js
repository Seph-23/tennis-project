
let validationStatus = {
    nickname: ""
}

let validationLength = {
    length: ""
}

$("#selfIntroduction").on("focusout", function () {

    let member = {
        introduction : $("#selfIntroduction").val()
    }
    console.log(member.introduction.length)

    if (member.introduction.length >= 100) {
        //자기소개 100자 이상일 시 에러
        $("#introduction-length-check").removeAttr("hidden");
        validationLength.length = "error"
        validationLengthCheck();
    } else {
        //정상 경우
        $("#introduction-length-check").attr("hidden", true);
        validationLength.length = "ok";
        validationLengthCheck();
    }
})


$("#nickname").on("focusout", function () {
    let nick = {
        nickname: $("#nickname").val(),
        loginNick: $("#loginNick").text()
    }
    console.log(nick.nickname)
    console.log(nick.loginNick)
    if (nick.nickname == nick.loginNick) {


        $("#nickname_validate").attr("hidden", true);
        $("#nickname_dup_check").attr("hidden", true);
        validationStatus.nickname = "ok";
        validationStatusCheck();
    }
    else if (nick.nickname.length < 4 || nick.nickname.length > 10) {
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

function validationLengthCheck() {
    if (validationLength.length === "ok") {
        $(".updatePf").removeAttr("disabled");
    } else {
        $(".updatePf").attr("disabled", true);
    }
}

function validationStatusCheck() {
    if (validationStatus.nickname === "ok") {
        $(".updatePf").removeAttr("disabled");
    } else {
        $(".updatePf").attr("disabled", true);
    }
}