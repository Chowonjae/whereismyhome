<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ include file="/WEB-INF/views/common/logincheck.jsp" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css"
      rel="stylesheet"
    />
    <link rel="stylesheet" href="${root}/assets/css/main.css" />
    <title>FIND HOME</title>
    <style>
    .form-control:focus {
    	color: #000 !important;
    	box-shadow: none !important;
    	 border-color: #ced4da;
    }
    </style>
  </head>
  <body>
   
    <div class="container">
      <div class="row justify-content-center">
        <div class="col-lg-8 col-md-10 col-sm-12">
          <h2 class="my-3 py-3 shadow-sm bg-light text-center">
            <mark class="orange">회원정보 수정</mark>
          </h2>
        </div>
        <div class="col-lg-8 col-md-10 col-sm-12">
          <form id="form-join" method="POST" action="">
            <div class="mb-3">
              <label for="username" class="form-label">이름 : </label>
              <input
                type="text"
                class="form-control"
                id="username"
                name="userName"
                value="${userinfo.userName}"
                placeholder="${userinfo.userName}"
                readonly
              />
            </div>
            <div class="mb-3">
              <label for="userid" class="form-label">아이디 : </label>
              <input
                type="text"
                class="form-control"
                id="userid"
                name="userId"
                value="${userinfo.userId}"
                placeholder="${userinfo.userId}"
                readonly
              />
            </div>
            <div id="idcheck-result"></div>
            <div class="mb-3">
              <label for="userpwd" class="form-label">새 비밀번호 : </label>
              <input
                type="password"
                class="form-control"
                id="userpwd"
                name="userPwd"
                placeholder="비밀번호..."
              />
            </div>
            <div class="mb-3">
              <label for="pwdcheck" class="form-label">새 비밀번호확인 : </label>
              <input type="password" class="form-control" id="pwdcheck" placeholder="비밀번호확인..." />
            </div>
            <div class="mb-3">
              <label for="emailid" class="form-label">이메일 : </label>
              <div class="input-group">
                <input
                  type="text"
                  class="form-control"
                  id="emailid"
                  name="emailId"
                  placeholder="이메일아이디"
                  value = ""
                />
                <span class="input-group-text">@</span>
               <select
                  class="form-select"
                  id="emaildomain"
                  name="emailDomain"
                  aria-label="이메일 도메인 선택"
                  value=""
                >
                  <option selected>선택</option>
                  <option value="ssafy.com">싸피</option>
                  <option value="google.com">구글</option>
                  <option value="naver.com">네이버</option>
                  <option value="kakao.com">카카오</option>
                  
                </select>
              </div>
              </div>
              <div class="mb-3">
              <label for="userpwd" class="form-label">회원가입일 : </label>
              <input
                type="text"
                class="form-control"
                id="reg_date"
                name="reg_date"
                placeholder="${userinfo.joinDate}"
                readonly
              />
            </div>

            <div class="col-auto text-center">
              <button type="button" id="btn-modify" class="btn btn-outline-primary mb-3">
                저장하기
              </button>

            </div>
          </form>
        </div>
      </div>
    </div>
    <footer>
      <div class="text-center">&copy; created by SSAFY SEOUL 17 TEAM 2</div>
    </footer>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js"></script>
    <script type="text/javascript" src="${root}/assets/js/mypage.js"></script>
    <script>

    
    
    //정보 저장
    document.querySelector("#btn-modify").addEventListener("click", function () {
		if (!document.querySelector("#userpwd").value) {
          alert("비밀번호 입력!!");
          return;
        } else if (document.querySelector("#userpwd").value != document.querySelector("#pwdcheck").value) {
          alert("비밀번호 확인!!");
          return;
        } else {
          let form = document.querySelector("#form-join");
          form.setAttribute("action", "${root}/user/modifyuser");
          form.submit();
        }
      });
    </script>
  </body>
</html>
