<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>login</title>
<link
  href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400;500;700&display=swap"
  rel="stylesheet">
<script src="https://kit.fontawesome.com/53a8c415f1.js"
  crossorigin="anonymous"></script>
<link rel="stylesheet" href="../css/loginStyle.css">
</head>
<body>
  <form action="login">
    <div class="wrap">
      <div class="login">
        <h2>로그인</h2>

        <div class="login_id">
          <h4>아이디</h4>
          <input type="email" name="email" id="" placeholder="아이디를 입력해주세요." value="${cookie.email.value}" autofocus>
        </div>
        <div class="login_pw">
          <h4>비밀번호</h4>
          <input type="password" name="password" id=""
            placeholder="비밀번호를 입력해주세요.">
        </div>
        <div class="login_etc">
          <div class="checkbox">
            <input type="checkbox" name="saveEmail" id=""> 아이디 저장
          </div>
          <div class="forgot_pw">
            <a href="">아이디 찾기</a> <a href="">비밀번호 찾기</a>
          </div>
        </div>

        <div class="submit">
          <input type="submit" value="로그인">
        </div>
        <div class="login_sns">
          <li><a href=""><i class="fab fa-instagram">인스타그램으로
                로그인</i></a></li>
          <li><a href=""><i class="fab fa-facebook-f">페이스북으로
                로그인</i></a></li>
        </div>
        <div class="sign">
          <li><a href="">회원가입</a></li>
        </div>
  </form>
  </div>
  </div>
</body>
</html>