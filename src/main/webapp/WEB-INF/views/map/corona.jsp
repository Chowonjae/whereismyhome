<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ include file="/WEB-INF/views/common/logincheck.jsp"%>
<script
            type="text/javascript"
            src="//dapi.kakao.com/v2/maps/sdk.js?appkey=b97c0d315c9af1251f9514bf8ffe4279&libraries=services"
></script>

    <main>
      <section class="container-fluid bg-black justify-content-center text-center">
          <form class="search-box" method="GET" action="${root }/map" >
            <div class="row col-md-12 mb-2 justify-content-center text-center">
            <input type="hidden" name="act" value="corona"/>
              <div class="row form-group col-lg-2 col-md-2 ps-4 pe-4 justify-content-center align-items-center">
                <select class="form-select bg-white text-black " id="sido" name="sido">
                  <option value="">시도선택</option>
                </select>
              </div>
              <div class="row form-group col-lg-2 col-md-2 ps-4 pe-4 justify-content-center align-items-center">
                <select class="form-select bg-white text-black" id="gugun" name="gugun">
                  <option value="">구군선택</option>
                </select>
              </div>
              <div class="row form-group col-lg-2 col-md-2 ps-4 pe-4 justify-content-center align-items-center">
                <select class="form-select bg-white text-black" id="dong" name="dong">
                  <option value="">동선택</option>
                </select>
              </div>
              <div class="row form-group col-lg-1 col-md-2 ps-4 pe-4 justify-content-center align-items-center">
                <select class="form-select bg-white text-black" id="day" name="day">
                  <option value="">요일선택</option>
                  <option value="WKD">평일</option>
                  <option value="SAT">토요일</option>
                  <option value="SUN">일요일</option>
                </select>
              </div>
              <div class="row form-group col-lg-1 col-md-2 ps-3 pe-3 justify-content-center align-items-center">
                <select class="form-select bg-white text-black" id="night" name="night">
                  <option value="AM">오전</option>
                  <option value="PM">오후</option>
                </select>
              </div>
              <div class="row form-group col-lg-1 col-md-2 ps-3 pe-3 justify-content-center align-items-center">
                <select class="form-select bg-white text-black" id="hour" name="hour">
                  <option value="">시간선택</option>
                  <option value="1">01</option>
                  <option value="2">02</option>
                  <option value="3">03</option>
                  <option value="4">04</option>
                  <option value="5">05</option>
                  <option value="6">06</option>
                  <option value="7">07</option>
                  <option value="8">08</option>
                  <option value="9">09</option>
                  <option value="10">10</option>
                  <option value="11">11</option>
                  <option value="12">12</option>
                </select>
              </div>
              <div class="row form-group col-lg-1 col-md-2 ps-3 pe-3 justify-content-center align-items-center">
                <select class="form-select bg-white text-black" id="min" name="min">
                  <option value="">분선택</option>
                  <option value="0">00</option>
                  <option value="10">10</option>
                  <option value="20">20</option>
                  <option value="30">30</option>
                  <option value="40">40</option>
                  <option value="50">50</option>
                </select>
              </div>
              <div class="row form-group col-lg-2 col-md-2 ps-4 pe-4 justify-content-center align-items-center">
                <button type="button" id="list-btn" class="btn btn-outline-light" style="transition:0.2s;">
                  선별진료소 정보 가져오기
                </button>
              </div>
           </div>
          </form>
      </section>
      <section class="inter-box pb-2" >  
      </div>
      </section>
      <section class="home-result-box">
        <div class="search-result">
          <div class="table-box col-sm-12 col-md-3 overflow-auto">
            <table class="table table-hover text-center col-md-4 col-sm-12">
              <thead>
              <tr>
                <th>코로나 선별진료소 현황</th>
              </tr>
              </thead>
              <tbody id="coronaList">
        
            </tbody>
            </table>
          </div>
          <div id="home-map" class="col-sm-12 col-md-9" style="min-height: 700px"></div>
  <script>
  let aptPoint = [];
  let bounds = new kakao.maps.LatLngBounds();;
  
  let markers = [];
  var container = document.getElementById("home-map");
	const imageSrc = "${root}/assets/img/clinic.png"; // 마커이미지의 주소입니다
	let imageSize = new kakao.maps.Size(50, 55); // 마커이미지의 크기입니다
	let imageOption = { offset: new kakao.maps.Point(27, 69) }; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.
  var options = {
		  center: new kakao.maps.LatLng(37.5012767241426, 127.039600248343), 
		  level: 8,
			};
  var map = new kakao.maps.Map(container, options);
  console.log(map);
	// 마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다
	var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption);

  

 </script>
        </div>
      </section>
    </main>
    <script>
    //search form 제출 관련.
   	document.querySelector("#list-btn").addEventListener("click",function(){
   		let sido = document.querySelector("#sido").value;
   		let gugun = document.querySelector("#gugun").value;
   		let dong = document.querySelector("#dong").value;
   		let day = document.querySelector("#day").value;
   		let night = document.querySelector("#night").value;
   		let hour = document.querySelector("#hour").value;
   		let min = document.querySelector("#min").value;
   		if(sido==""){
   			alert("시도를 선택해주세요.");
   		}
   		else if(gugun==""){
   			alert("구군을 선택해주세요.");
   		}
   		else if(dong==""){
   			alert("동을 선택해주세요.");
   		}
   		else if(day==""){
   			alert("요일을 선택해주세요.");
   		}
   		else if(hour==""){
   			alert("시간을 선택해주세요.");
   		}
   		else if(min==""){
   			alert("분을 선택해주세요.");
   		}
   		else{
   	        let config = {
     	   	          method: "GET",
     	   	          headers: {
     	   	            "Content-Type": "application/json",
     	   	          },
     	     	   	};

     	   			fetch(`${root}/rmap/corona/\${sido}/\${gugun}/\${day}/\${night}/\${hour}/\${min}`,config)
     	   			.then((response)=>response.json())
     	   			.then((data)=>makeCoronaList(data));
     	   		
		}
   		
   	});      

    function makeCoronaList(apts){
      	let tbody = ``;
      	if(apts.length==0){
      		tbody+=`<tr>
      	        <td>선별진료소 없음</td>
      	        </tr>`;
      	}	
      	else{
      	  if(markers.length>0){
      	  	deleteMark();
      	  	bounds = new kakao.maps.LatLngBounds();
      	  }
      	  var st = 0;
      		apts.forEach((apt)=>{
      			console.log(apt);
      			tbody+=`
      			    <tr class="apt-item" >
      		    	<td>
      		    	<div class="apt-name">
      		    		<a>\${apt.name }</a>
      		    	</div>
      		    	<div class="apt-space">
      		    		주소 : \${apt.address }
      		    	</div>
      		    	<div class="apt-price">
      		    		평일 : \${apt.weekday }
      		    	</div>
      		    	<div class="apt-price text-primary">
      		    		토요일 : \${apt.saterday }
      		    	</div>
      		    	<div class="apt-price text-danger">
      		    		일요일 : \${apt.sunday }
      		    	</div>
      		    	<div class="apt-phone">
      		    		전화번호 : \${apt.phonenumber }
      		    	</div>

      		    	</td>
      		    </tr>
      			`;

      			st+=1;

            	var geocoder = new kakao.maps.services.Geocoder();
            	geocoder.addressSearch(
            			 apt.address,
            		      function (result, status) {
            		        // 정상적으로 검색이 완료됐으면
            		        if (status === kakao.maps.services.Status.OK) {
            		          var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
            		          bounds.extend(new kakao.maps.LatLng(result[0].y, result[0].x));
            		          map.setBounds(bounds);
            		          var marker = new kakao.maps.Marker({
            		            map: map,
            		            position: coords,
            		            image:markerImage,
            		          });
            		          markers.push(marker);
      						}
						});
        	});
		}
		document.querySelector("#coronaList").innerHTML = tbody;
	}
  	

  	
    </script>  
    
    <script type="text/javascript" src="${root }/assets/js/main2.js"></script>      
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
