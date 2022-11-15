<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${pageContext.request.contextPath}"/>
<c:if test="${srcmsg ne null}">
	<script type="text/javascript">
		alert("${srcmsg}");
	</script>
</c:if>
<c:if test="${loginmsg ne null}">
	<script type="text/javascript">
		alert("${loginmsg}");
	</script>
</c:if>
    <main>
      <section class="container-fluid main-banner p-5">
        <div class="banner-title-box">
           <div class="banner-title display-3 text-center p-5 text-white border-bottom border-2">
            FIND HOME
          </div>
		<c:if test="${userinfo ne null}">
          <form class="search-box p-5" method="GET" action="${root }/map" >
            <div class="row col-md-12 mb-2 justify-content-center text-center">
            <input type="hidden" name="act" value="search"/>
         <div class="form-group col-md-2">
                <select class="form-select bg-white text-black" id="sido" name="sido">
                  <option value="">시도선택</option>
                </select>
              </div>
              <div class="form-group col-md-2">
                <select class="form-select bg-white text-black" id="gugun" name="gugun">
                  <option value="">구군선택</option>
                </select>
              </div>
              <div class="form-group col-md-2">
                <select class="form-select bg-white text-black" id="dong" name="dong">
                  <option value="">동선택</option>
                </select>
              </div>
              <div class="form-group col-md-2">
                <select class="form-select bg-white text-black" id="year" name="year"></select>
              </div>
              <div class="form-group col-md-2">
                <select class="form-select bg-white text-black" id="month" name="month">
                  <option value="">매매월선택</option>
                </select>
              </div>
              
              <div class="form-group col-md-2">
                <button type="button" id="list-btn" class="btn btn-outline-light">
                  매매 정보 가져오기
                </button>
<!--                <button type="submit" id="list-btn" class="btn btn-outline-light">
                  매매 정보 가져오기
                </button>-->
              </div>
            </div>
          </form>
          <script>
         	document.querySelector("#list-btn").addEventListener("click",function(){
           		let sido = document.querySelector("#sido").value;
           		let gugun = document.querySelector("#gugun").value;
           		let dong = document.querySelector("#dong").value;
           		let year = document.querySelector("#year").value;
           		let month = document.querySelector("#month").value;
           		if(sido==""){
           			alert("시도를 선택해주세요.");
           		}
           		else if(gugun==""){
           			alert("구군을 선택해주세요.");
           		}
           		else if(dong==""){
           			alert("동을 선택해주세요.");
           		}
           		else if(year=="" && month==""){
           			document.querySelector(".search-box").submit();
           		}
           		else if(month==""){
           			alert("월을 선택해주세요.");
           		}
           		else if(year == ""){
           			alert("년도를 선택해주세요.");
           		}
           		else{
           			document.querySelector(".search-box").submit();
           		}
          		
          	});
          </script>
          <script
            type="text/javascript"
            src="//dapi.kakao.com/v2/maps/sdk.js?appkey=b97c0d315c9af1251f9514bf8ffe4279&libraries=services"
          ></script>
    <script type="text/javascript" src="${root }/assets/js/main.js"></script>      
          </c:if>
        </div>
      </section>
    </main>

    <%@ include file="/WEB-INF/views/common/footer.jsp" %>