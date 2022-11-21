const deleteBoard = (boardId) => {
  if (confirm("게시글을 삭제하시겠습니까?") === true) {
    $.ajax({
      type: "post",
      url: "/api/boards/boardDelete/" + boardId,
      success: function (data) {
        if (data.result === "success") {
          window.location.assign("http://localhost:8080/manager/profile");
        }
      },
      error: function () {
        alert("게시글 삭제 에러!");
      },
    });
  } else {
    return false;
  }
};

const deleteComment = (commentId) => {
  Swal.fire({
    title: "삭제하시겠습니까?",
    icon: "warning",
    inputType: "submit",
    buttons: {
      confirm: true,
      cancel: true
    },
  }).then(function (confirm) {
    if (confirm) {
      $.ajax({
        type: "post",
        url: "/api/comments/commentDelete/" + commentId,
        success: function (data) {
          if (data.result === "success") {
            window.location.reload();
          }
        },
        error: function () {
          alert("서버 에러!");
        },});
    } else {
      return false;
    }
  });
};