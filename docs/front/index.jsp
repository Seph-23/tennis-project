<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>GOGOTennis</title>
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/slideStyle.css">
<script src='https://kit.fontawesome.com/a076d05399.js'
  crossorigin='anonymous'></script>
</head>
<body>
  <div class="container">
    <div class="header">
      <h1>
        <img src="images/gogoTennisLogo.png" alt="logo" width="160">
        <a href="#"></a>
      </h1>
      <div class="nav">
        <ul>
          <li><a href="#">매치</a></li>
          <li><a href="#">랭킹</a></li>
          <li><a href="#">대회</a></li>
          <li><a href="#">커뮤니티</a></li>
        </ul>
      </div>
      <div class="login">
        <ul>
          <c:choose>
            <c:when test="${not empty sessionScope.loginMember}">
              <li><a href="#a"><i class='far fa-user-circle'
                  style='font-size: 36px'></i></a>
              <li><a href="auth/logout"> <%-- ${sessionScope.loginMember.name} --%>로그아웃
              </a></li>
            </c:when>
            <c:otherwise>
              <li><a href="auth/login.jsp">로그인</a></li>
            </c:otherwise>
          </c:choose>
        </ul>
      </div>
    </div>
  </div>
  <div id="slideShow">
    <ul class="slides">
      <li><img src="images/1.png" alt=""></li>
      <li><img src="images/2.jpeg" alt=""></li>
      <li><img src="images/3.jpeg" alt=""></li>
      <li><img src="images/4.jpeg" alt=""></li>
      <li><img src="images/5.jpeg" alt=""></li>
    </ul>
    <p class="controller">
      <!-- &lang: 왼쪽 방향 화살표
      &rang: 오른쪽 방향 화살표 -->
      <span class="prev">&lang;</span> <span class="next">&rang;</span>
    </p>
  </div>
  <script src="js/slideShow.js"></script>
</body>
</html>