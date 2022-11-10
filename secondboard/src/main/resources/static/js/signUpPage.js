$(document).ready(function(){
  let now = new Date();
  let year = now.getFullYear();
  let mon = (now.getMonth() + 1) > 9 ? ''+(now.getMonth() + 1) : '0'+(now.getMonth() + 1);
  let day = (now.getDate()) > 9 ? ''+(now.getDate()) : '0'+(now.getDate());
  for(let i = 1900 ; i <= year ; i++) {
    $('#year').append('<option th:value="' + i + '">' + i + '</option>');
  }
  for(let i=1; i <= 12; i++) {
    let mm = i > 9 ? i : "0"+i ;
    $('#month').append('<option th:value="' + mm + '">' + mm + '</option>');
  }
  for(let i=1; i <= 31; i++) {
    let dd = i > 9 ? i : "0"+i ;
    $('#day').append('<option th:value="' + dd + '">' + dd + '</option>');
  }
  $("#year  > option[value="+year+"]").attr("selected", "true");
  $("#month  > option[value="+mon+"]").attr("selected", "true");
  $("#day  > option[value="+day+"]").attr("selected", "true");

  $("#inlineRadio1").prop("checked", true);
});

let validationStatus = {
  id: "",
  password: "",
  nickname: "",
  email: "",
  phone: ""
}

$("#login-id").on("focusout", function () {
  let id = {
    loginId: $("#login-id").val(),
  };
  let idval = $('#login-id').val()
  let idvalcheck = /^[A-za-z]/g;

  if (id.loginId.length < 8 || id.loginId.length > 15) {
    $("#login_id_dup_check").attr("hidden", true);
    $("#login_id_validate").removeAttr("hidden");
    $("#login_id_format").attr("hidden", true);
    validationStatus.id = "";
    validationStatusCheck();
  } else if (!idvalcheck.test(idval)) {
    $("#login_id_format").removeAttr("hidden");
    $("#login_id_validate").attr("hidden", true);
    validationStatus.id = "";
    validationStatusCheck();
  } else {
    $.ajax({
      type: "get",
      url: "/api/members/checkId",
      data: id,
      success: function (data) {
        if (data.result === "duplicate") {
          $("#login_id_dup_check").removeAttr("hidden");
          $("#login_id_validate").attr("hidden", true);
          validationStatus.id = "";
          validationStatusCheck();
        } else if (data.result === "ok") {
          $("#login_id_dup_check").attr("hidden", true);
          $("#login_id_validate").attr("hidden", true);
          $("#login_id_format").attr("hidden", true);
          validationStatus.id = "ok";
          validationStatusCheck();
        }
      },
      error: function () {
      },
    });
  }
});

$("#password").on("focusout", function () {
  let password = $("#password").val();
  if (password.length < 9 || password.length > 24) {
    $("#password_validate").removeAttr("hidden");
    validationStatus.password = "";
    validationStatusCheck();
  } else {
    $("#password_validate").attr("hidden", true);
  }
});

$("#password_check").on("focusout", function () {
  let password = $("#password").val();
  let passwordCheck = $("#password_check").val();
  if (password !== passwordCheck) {
    $("#password_check_message").removeAttr("hidden");
    validationStatus.password = "";
    validationStatusCheck();
  } else {
    $("#password_check_message").attr("hidden", true);
    validationStatus.password = "ok";
    validationStatusCheck();
  }
});

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

$("#email").on("focusout", function () {
  let email = {
    email: $("#email").val(),
  }
  $.ajax({
    type: "get",
    url: "/api/members/checkEmail",
    data: email,
    success: function (data) {
      if (data.result === "duplicate") {
        $("#email_duplicate").removeAttr("hidden");
        validationStatus.email = "";
        validationStatusCheck();
      } else if (data.result === "ok") {
        $("#email_duplicate").attr("hidden", true);
        validationStatus.email = "ok";
        validationStatusCheck();
      }
    },
  });
});

$("#phone-number").on("focusout", function () {
  let phoneNum = {
    phoneNum: $("#phone-number").val(),
  }
  if (phoneNum.phoneNum.length !== 11) {
    $("#phone_validate").removeAttr("hidden");
    validationStatus.phone = "";
    validationStatusCheck();
  } else {
    $.ajax({
      type: "get",
      url: "/api/members/checkPhoneNum",
      data: phoneNum,
      success: function (data) {
        if (data.result === "duplicate") {
          $("#phone_duplicate").removeAttr("hidden");
          $("#phone_validate").attr("hidden", true);
          validationStatus.phone = "";
          validationStatusCheck();
        } else if (data.result === "ok") {
          $("#phone_duplicate").attr("hidden", true);
          $("#phone_validate").attr("hidden", true);
          validationStatus.phone = "ok";
          validationStatusCheck();
        }
      },
    });
  }
});

function validationStatusCheck() {
  if (validationStatus.id === "ok" && validationStatus.password === "ok"
      && validationStatus.nickname === "ok" && validationStatus.email === "ok"
      && validationStatus.phone === "ok") {
    $("#signup-submit").removeAttr("disabled");
  } else {
    $("#signup-submit").attr("disabled", true);
  }
}