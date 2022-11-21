let form = {
  loginId: "",
  password: ""
}

$("#submit").click(function () {
  form = {
    loginId: $("#login_id").val(),
    password: $("#password").val()
  };

  if (form.loginId === "" && form.password === "") {
    Swal.fire({
      title: "아이디, 비밀번호를 입력해주세요.",
      icon: "error",
      showCloseButton: true,
    });
  } else if (form.loginId === "") {
    Swal.fire({
      title: "아이디를 입력해주세요.",
      icon: "error",
      showCloseButton: true,
    });
  } else if (form.password === "") {
    Swal.fire({
      title: "비밀번호를 입력해주세요.",
      icon: "error",
      showCloseButton: true,
    });
  } else {
    $.ajax({
      type: "POST",
      url: "/login/modal",
      data: {'loginId': form.loginId, 'password': form.password},
      success: function (data) {
        if (data.result === "success") {
          window.location.href="/";
        } else {
          Swal.fire({
            title: "아이디 비밀번호가 잘못되었습니다.",
            icon: "error",
            showCloseButton: true,
          });
        }
      },
      error: function (data) {
        alert("통신 에러");
      },
    });
  }
});