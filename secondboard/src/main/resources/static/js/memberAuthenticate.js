$("#send-phone-number").click(function () {
  let phoneNumber = $("#input-phone-number").val();
  swal('인증번호를 발송했습니다.')

  $.ajax({
    type: "GET",
    url: "/api/sms/check/sendSMS",
    data: {
      "phoneNumber": phoneNumber
    },
    success: function (res) {
      let Id =  $("#updateMemberLoginId").val();
      $("#checkBtn").click(function () {
        if ($.trim(res) === $("#inputCertifiedNumber").val()) {
          swal('인증에 성공했습니다.',
              '휴대폰 인증이 정상적으로 완료되었습니다.',
              'success').then(function () {
            location.href="/members/update/password/" + Id;
          })

          $.ajax({
            type: "GET",
            url: "/members/update/password" + Id,
            data: {
              "phoneNumber" : $('#inputPhoneNumber').val()
            }
          })

        } else {
          swal('인증번호가 올바르지 않습니다.',
              '확인 후 다시 입력해 주세요.',
              'error')
        }
      })

    }
  })
});