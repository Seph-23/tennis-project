let check = {
    clickbox: ""
}

function validateByCheckbox() {
    // 1. checkbox element를 찾습니다.
    const checkbox = document.getElementById('checkbox_withdrawl');

    // 2. checked 속성을 체크합니다.
    const is_checked = checkbox.checked;

    if (is_checked == false) {
        $("#memberWithdrawlCheck").removeAttr("hidden")
        check.clickbox = ""
        checkWithdrawlbox()
    } else if (is_checked == true) {
        $("#memberWithdrawlCheck").attr("hidden", true)
        check.clickbox = "ok"
        checkWithdrawlbox()
    }
}


function checkWithdrawlbox() {
    if (check.clickbox === "ok") {
        $(".withdrawl").removeAttr("disabled");
    } else {
        $(".withdrawl").attr("disabled", true);
    }
}