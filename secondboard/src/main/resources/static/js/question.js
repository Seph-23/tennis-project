const questionClick = (questionId) => {
    const loginMember = $("#loginMember").attr("value")
    const loginMemberNickname = $("#loginMemberNickname").attr("value")
    const loginMemberRole = $("#loginMemberRole").attr("value")
    const clickedQuestionAuthor = $("#question-" + questionId).children('td:eq(3)').text()
    if (loginMember == "" || (loginMemberNickname != clickedQuestionAuthor && loginMemberRole == 'MEMBER')) {
        Swal.fire({
            title: '조회 권한이 없습니다.',
            confirmButtonText: 'OK'
        })
    } else {
        location.href = "/question/detail/" + questionId
    }
}