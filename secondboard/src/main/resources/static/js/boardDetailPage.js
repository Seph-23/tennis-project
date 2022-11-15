$("#comment-submit").click(function () {
  let comment = {
    content: $("#comment").val(),
    boardId: $("#board-id").val()
  }
  Swal.fire({
    title: "댓글을 등록하시겠습니까?",
    icon: "success",
    inputType: "submit",
    buttons: {
      confirm: true,
      cancel: true
    },
  }).then(function (confirm) {
    if (confirm) {
      $.ajax({
        type: "post",
        async: false,
        url: "/api/comments/commentAdd/" + comment.boardId,
        data: comment,
        success: function (data) {
          if (data.result === "success") {
            window.location.reload();
          } else if (data.result === "validate") {
            alert("댓글 길이는 1 ~ 100자 이내여야 합니다.");
          }
        },
        error: function () {
          alert("서버 에러!");
        },
      });
    } else {
      return false;
    }
  });
});

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

const updateComment = (commentId) => {
  $("#comment-content-" + commentId).prop('disabled', false);
  $("#comment-update-" + commentId).attr("hidden", true);
  $("#comment-delete-" + commentId).attr("hidden", true);
  $("#comment-update-confirm-" + commentId).attr("hidden", false);
  $("#comment-update-cancel-" + commentId).attr("hidden", false);
};

const updateCommentConfirm = (commentId) => {

  let comment = {
    content: $("#comment-content-" + commentId).val()
  }
  Swal.fire({
    title: "수정하시겠습니까?",
    icon: "success",
    inputType: "update",
    buttons: {
      confirm: true,
      cancel: true
    },
  }).then(function (confirm) {
    if (confirm) {
      $.ajax({
        type: "post",
        url: "/api/comments/commentUpdate/" + commentId,
        data: comment,
        success: function (data) {
          if (data.result === "success") {
            window.location.reload();
            $("#comment-content-" + commentId).prop('disabled', true);
            $("#comment-update-" + commentId).attr("hidden", false);
            $("#comment-delete-" + commentId).attr("hidden", false);
            $("#comment-update-confirm-" + commentId).attr("hidden", true);
            $("#comment-update-cancel-" + commentId).attr("hidden", true);
          } else if (data.result === "validate") {
            alert("댓글은 1 ~ 100 자 이내여야 합니다.");
          }
        },
        error: function () {
          alert("서버 에러!");
        },
      });
    } else {
      return false;
    }
  });
}

const updateCommentCancel = (commentId) => {
  let comment = {
    content: $("#comment-content-" + commentId).val()
  }
  $.ajax({
    type: "post",
    url: "/api/comments/commentUpdateCancel/" + commentId,
    data: comment,
    success: function (data) {
      if (data.result === "success") {
        window.location.reload();
        $("#comment-content-" + commentId).prop('disabled', true);
        $("#comment-update-" + commentId).attr("hidden", false);
        $("#comment-delete-" + commentId).attr("hidden", false);
        $("#comment-update-confirm-" + commentId).attr("hidden", true);
        $("#comment-update-cancel-" + commentId).attr("hidden", true);
      }
    },
    error: function () {
      alert("서버 에러!");
    },
  });
};

const deleteBoard = (boardId) => {
  if (confirm("게시글을 삭제하시겠습니까?") === true) {
    $.ajax({
      type: "post",
      url: "/api/boards/boardDelete/" + boardId,
      success: function (data) {
        if (data.result === "success") {
          window.location.assign("http://localhost:8080/boards/home");
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