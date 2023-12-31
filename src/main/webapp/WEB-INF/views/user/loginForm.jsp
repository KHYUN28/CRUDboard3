<%@ page language="java" contentType="text/html; charset=UTF-16"
         pageEncoding="UTF-16"%>

<%@ include file="../layout/header.jsp" %>

<div class="container">
  <form action="/auth/loginProc" method="post">
    <div class="form-group">
      <div style="text-align: left;">
        <label for="username">Username</label>
        <input type="text" name="username" class="form-control" placeholder="Enter username" id="username">
      </div>
    </div>

    <div class="form-group">
      <div style="text-align: left;">
        <label for="password">Password</label>
        <input type="password" name="password" class="form-control" placeholder="Enter password" id="password">
      </div>
    </div>

    <div style="text-align: right;">
      <a href="https://kauth.kakao.com/oauth/authorize?client_id=059c43b42aa949ccdfaed590a8522969&redirect_uri=http://localhost:7777/auth/kakao/callback&response_type=code"><img height="38px" src="/image/kakao_login_medium.png" /></a>
      <button id="btn-login" class="btn btn-primary">로그인</button>
    </div>
  </form>
</div>

