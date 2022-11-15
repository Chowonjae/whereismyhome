<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${pageContext.request.contextPath}"/>
<c:set var="userId" value="${userinfo.userId}"/>
  <c:if test="${cookie.ssafy_id.value ne null }">
  	<c:set var="idck" value="checked"></c:set>
  	<c:set var="svid" value="${cookie.ssafy_id.value }"></c:set>
  </c:if>
    <header>
      <div class="nav d-flex justify-content-between align-items-center mt-2 mb-2">
      <div class="logo justify-content-start ms-5">
	      <a class="text-decoration-none ddisplay-5 font-weight-bold " href="${root}/">FIND HOME</a>
	      
      </div>
 <c:if test="${userinfo ne null}">
       <div class="nav justify-content-end logined">
          <div class="logined-info me-3 align-middle">
          <strong>${userinfo.userName}</strong> (${userinfo.userId})님 안녕하세요.
          </div>
          <button class="hospital-btn btn me-3" id="btn-hospital">Hospital</button>
          <button class="corona-btn btn me-3" id="btn-corona">Corona</button>
          <button class="mvsearch-btn btn me-3" id="btn-homesearch">HomeSearch</button>
          <button class="notice-btn btn me-3" id="btn-notice">Notice</button>
          <button class="mypage-btn btn me-3" id="btn-mypage">MyPage</button>
          <button class="logout-btn btn me-3" id="btn-logout">Logout</button>
        </div>
      <script>
      //로그아웃
      document.querySelector("#btn-logout").addEventListener("click", function () {
      	location.href = "${root}/user/logout";
        });
      //마이페이지
      document.querySelector(".mypage-btn").addEventListener("click", function () {
    	  location.href = "${root}/user/mvmypage"; 
    	});
      //공지사항
      document.querySelector("#btn-notice").addEventListener("click",function(){
    	  location.href="${root}/board/list?pgno=1&key=&word=";
      })
      //집 검색
      document.querySelector("#btn-homesearch").addEventListener("click",function(){
    	  location.href="${root}/map/search";
      })
      //선별진료소
      document.querySelector("#btn-corona").addEventListener("click",function(){
    	  location.href="${root}/map/corona";
      })
      //선별진료소
      document.querySelector("#btn-hospital").addEventListener("click",function(){
    	  location.href="${root}/map/hospital";
      })	</script>
</c:if>
<c:if test="${userinfo eq null}">
        <div class="nav justify-content-end">
          <button  id="btn-mv-join" class="join-btn btn me-3">Join</button>

          <div class="login-area me-3">
            <div class="dropdown login-pop">
              <button
                class="btn dropdown-toggle"
                type="button"
                id="dropdownMenu"
                data-bs-toggle="dropdown"
                aria-haspopup="true"
                aria-expanded="false"
              >
                Login
              </button>
			<form method="POST" class="dropdown-menu p-3" id="form-login"aria-labelledby="dropdownMenu">
			<input type="hidden" name="act" value="login"/>

            <div class="form-check mb-3 float-end">
              <input
                class="form-check-input"
                type="checkbox"
                value="ok"
                id="saveid"
                name="saveid"
                ${idck}
              />
              <label class="form-check-label" for="saveid"> 아이디저장 </label>
            </div>
            <div class="mb-3">
              <label for="userid" class="form-label">ID</label>
              <input
                type="text"
                class="form-control"
                id="userid"
                name="userid"
                placeholder="ID..."
                value="${svid}"
              />
            </div>
            <div class="mb-3">
              <label for="userpwd" class="form-label">PW</label>
              <input
                type="password"
                class="form-control"
                id="userpwd"
                name="userpwd"
                placeholder="PW..."
              />
            </div>
            <c:if test="${msg ne null}">
            <div class="alert alert-danger mb-2"><small>${msg }</small></div>            
            </c:if>
            <div class="col-auto text-center">
              <button type="button" id="btn-login" class="btn btn-outline-primary mb-3">
                Login
              </button>
              <button type="button" id="btn-findpw" class="btn btn-primary mb-3">
                비밀번호 찾기
              </button>
            </div>
			</form>
            </div>
          </div>
        </div>
    <script>
    // 비밀번호 찾기
    
   	document.querySelector("#btn-findpw").addEventListener("click", function () {
    	location.href = "${root}/user/mvfindpw";
      });
    //회원가입 페이지 이동
      document.querySelector("#btn-mv-join").addEventListener("click", function () {
    	location.href = "${root}/user/mvjoin";
      });
      //로그인
      document.querySelector("#btn-login").addEventListener("click", function () {
        if (!document.querySelector("#userid").value) {
          alert("아이디 입력!!");
          return;
        } else if (!document.querySelector("#userpwd").value) {
          alert("비밀번호 입력!!");
          return;
        } else {
          let form = document.querySelector("#form-login");
          form.setAttribute("action", "${root}/user/login");
          form.submit();
        }
      });
      </script>
	</c:if>
      </div>
    </header>
