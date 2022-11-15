<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${pageContext.request.contextPath}"></c:set>

<!DOCTYPE html>
<html lang="ko">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
      crossorigin="anonymous"
    />
    <link href="${root}/assets/css/app.css" rel="stylesheet" />
    <title>SSAFY</title>
  </head>
  
  <body>
    <div class="container">
      <div class="row justify-content-center">
        <div class="col-lg-8 col-md-10 col-sm-12">
          <h2 class="my-3 py-3 shadow-sm bg-light text-center">
            <mark class="orange">비밀번호 찾기</mark>
          </h2>
        </div>
        <div class="col-lg-8 col-md-10 col-sm-12">
          <form id="form-find" method="POST" action="">
            <div class="mb-3">
              <label for="username" class="form-label">이름 : </label>
              <input
                type="text"
                class="form-control"
                id="username"
                name="userName"
                placeholder="이름..."
              />
            </div>
            <div class="mb-3">
              <label for="userid" class="form-label">아이디 : </label>
              <input
                type="text"
                class="form-control"
                id="userid"
                name="userId"
                placeholder="아이디..."
              />
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
                />
                <span class="input-group-text">@</span>
                <select
                  class="form-select"
                  id="emaildomain"
                  name="emailDomain"
                  aria-label="이메일 도메인 선택"
                >
                  <option selected>선택</option>
                  <option value="ssafy.com">싸피</option>
                  <option value="google.com">구글</option>
                  <option value="naver.com">네이버</option>
                  <option value="kakao.com">카카오</option>
                </select>
              </div>
            </div>
            <div class="col-auto text-center">
              <button type="button" id="btn-find" class="btn btn-outline-primary mb-3">
                비밀번호 찾기
              </button>
              <button type="button" class="btn btn-outline-success mb-3">초기화</button>
            </div>
          </form>
        </div>
      </div>
    </div>
    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
      crossorigin="anonymous"
    ></script>
    <script>
    
      document.querySelector("#btn-find").addEventListener("click", function () {
        if (!document.querySelector("#username").value) {
          alert("이름 입력!!");
          return;
        } else if (!document.querySelector("#userid").value) {
          alert("아이디 입력!!");
          return;
        } else if (document.querySelector("#emailid").value == ""||document.querySelector("#emaildomain").value=="선택") {
              alert("이메일 입력!!");
              return;
        } else {
          let form = document.querySelector("#form-find");
          form.setAttribute("action", "${root}/user/findpw");
          form.submit();
        }
      });
    </script>
  </body>
</html>
