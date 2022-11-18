let check = {
    clickbox: ""
}

const memberId = $("#loginMemberId").attr('value')
console.log(memberId)

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
const deleteMember = (memberId) => {
        $.ajax({
            type: "post",
            url: "/members/withdrawl/" + memberId,
            success: function (data) {
                if (data.result === "success") {
                    Swal.fire({
                        title: '회원 탈퇴가 완료되었습니다.',
                        confirmButtonText: 'OK',
                        icon : 'success'
                    }).then(function (){
                        location.href="/";
                    })
                }
                else if (data.result === "error") {
                    Swal.fire({
                        title: '현재 참가중인 매치가 있어 회원 탈퇴가 불가능합니다.',
                        confirmButtonText: 'OK',
                        icon : 'error'
                    })
                }
            },
            error: function () {
              alert("서버 에러");
            },
        });

};

