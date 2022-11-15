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
            <mark class="orange">마이페이지</mark>
          </h2>
        </div>
        <div class="col-lg-8 col-md-10 col-sm-12">
          <form id="form-join" method="POST" action="">
			<input type="hidden" name="act" value="join"/>
            <div class="mb-3">
              <label for="username" class="form-label">이름 : </label>
              <input
                type="text"
                class="form-control"
                id="username"
                name="username"
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
                name="userid"
                placeholder="${userinfo.userId}"
                readonly
              />
            </div>
            <div id="idcheck-result"></div>
            <div class="mb-3">
              <label for="userpwd" class="form-label">비밀번호 : </label>
              <input
                type="password"
                class="form-control"
                id="userpwd"
                name="userpwd"
                placeholder="********"
                readonly
              />
            </div>
            <div class="mb-3">
              <label for="emailid" class="form-label">이메일 : </label>
              <div class="input-group">
                <input
                	readonly
                  type="text"
                  class="form-control"
                  id="emailid"
                  name="emailid"
                  placeholder="${userinfo.emailId}"
                />
                <span class="input-group-text">@</span>
                <input
                readonly
                  class="form-control"
                  id="emaildomain"
                  name="emaildomain"
                  placeholder="${userinfo.emailDomain}" 
                />
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
                정보 수정하기
              </button>
              <button type="button" id="btn-account-delete"class="btn btn-outline-success mb-3">회원탈퇴하기</button>
              
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
    <c:if test="${!empty msg}">
    	alert("${msg}");
    </c:if>
    document.querySelector("#btn-modify").addEventListener("click", function () {
  	  location.href = "${root}/user/mvmypagemodify"; 
  	});
    document.querySelector("#btn-account-delete").addEventListener("click", function () {
        var message = "정말로 탈퇴하시겠습니까?";
        result = window.confirm(message);
        if (result) {
            location.href = "${root}/user/delete"; 
        }
    });
    </script>
  </body>
</html>
