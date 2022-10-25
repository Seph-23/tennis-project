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
})

$("#id-dup-button").click(function () {
  $("#signup-submit").attr("type", "button");
  let id = {
    loginId: $("#login-id").val()
  }
  $.ajax({
    type: "get",
    async: false,
    url: "/api/members/checkId",
    data: id,
    success: function (data) {
      if (data.result === "duplicate") {
        alert("이미 가입된 아이디 입니다.");
      } else if (data.result === "ok") {
        alert("사용 가능한 아이디 입니다.");
        $("#signup-submit").attr("type", "submit");
      } else if (data.result === "validate") {
        alert("아이디는 8 ~ 15 자 이내여야 합니다.");
      }
    },
    error: function () {
      alert("서버 에러!");
    }
  });
});
$("#nick-dup-button").click(function () {
  $("#signup-submit").attr("type", "button");
  let nick = {
    nickname: $("#nickname").val()
  }
  $.ajax({
    type: "get",
    async: false,
    url: "/api/members/checkNick",
    data: nick,
    success: function (data) {
      if (data.result === "duplicate") {
        alert("이미 사용중인 닉네임 입니다.");
      } else if (data.result === "ok") {
        alert("사용 가능한 닉네임 입니다.");
        $("#signup-submit").attr("type", "submit");
      } else if (data.result === "validate") {
        alert("닉네임은 4 ~ 10 자 이내여야 합니다.");
      }
    },
    error: function () {
      alert("서버 에러!");
    }
  })
});
$("#email-dup-button").click(function () {
  $("#signup-submit").attr("type", "button");
  let email = {
    email: $("#email").val()
  }
  $.ajax({
    type: "get",
    async: false,
    url: "/api/members/checkEmail",
    data: email,
    success: function (data) {
      if (data.result === "duplicate") {
        alert("이미 사용중인 이메일 입니다.");
      } else if (data.result === "ok") {
        alert("사용 가능한 이메일 입니다.");
        $("#signup-submit").attr("type", "submit");
      }
    },
    error: function () {
      alert("서버 에러!");
    }
  })
});