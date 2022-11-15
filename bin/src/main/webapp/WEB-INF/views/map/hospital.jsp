<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp"%>
<%@ include file="/WEB-INF/views/common/logincheck.jsp"%>
<script type="text/javascript"
	src="//dapi.kakao.com/v2/maps/sdk.js?appkey=b97c0d315c9af1251f9514bf8ffe4279&libraries=services"></script>

<main>
	<section
		class="container-fluid bg-black justify-content-center text-center">
		<form class="search-box" method="GET" action="${root }/map">
			<div class="row col-md-12 mb-2 justify-content-center text-center">
				<input type="hidden" name="act" value="hospital" />
				<div
					class="row form-group col-lg-2 col-md-2 ps-3 pe-3 justify-content-center align-items-center">
					<select class="form-select bg-white text-black " id="sido"
						name="sido">
						<option value="">시도선택</option>
					</select>
				</div>
				<div
					class="row form-group col-lg-2 col-md-2 ps-3 pe-3 justify-content-center align-items-center">
					<select class="form-select bg-white text-black" id="gugun"
						name="gugun">
						<option value="">구군선택</option>
					</select>
				</div>
				<div
					class="row form-group col-lg-2 col-md-2 ps-3 pe-3 justify-content-center align-items-center">
					<select class="form-select bg-white text-black" id="dong"
						name="dong">
						<option value="">동선택</option>
					</select>
				</div>
				<div
					class="row form-group col-lg-2 col-md-2 ps-3 pe-3 justify-content-center align-items-center">
					<button type="button" id="list-btn" class="btn btn-outline-light"
						style="transition: 0.2s;">병원 정보 가져오기</button>
				</div>
			</div>
		</form>
	</section>
	<section class="inter-box pb-2">
		</div>
		<form id="inter-form" method="GET" action="${root }/map">
			<input type="hidden" id="nact" name="act" value="" /> <input
				type="hidden" id="ndong" name="dong" value="" />
		</form>
	</section>
	<section class="home-result-box">
		<div class="search-result">
			<div class="table-box col-sm-12 col-md-3 overflow-auto">
				<table class="table table-hover text-center col-md-4 col-sm-12">
					<thead>
						<tr>
							<th>안심진료병원 현황</th>
						</tr>
					</thead>
					<tbody id="hospitalList">
					</tbody>
				</table>
			</div>
			<div id="home-map" class="col-sm-12 col-md-9"
				style="min-height: 700px"></div>
			<script>
  let aptPoint = [];
  let bounds = new kakao.maps.LatLngBounds();;
  
  let markers = [];
  var container = document.getElementById("home-map");
	const imageSrc = "${root}/assets/img/hospital.png"; // 마커이미지의 주소입니다
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
   		if(sido==""){
   			alert("시도를 선택해주세요.");
   		}
   		else if(gugun==""){
   			alert("구군을 선택해주세요.");
   		}
   		else if(dong==""){
   			alert("동을 선택해주세요.");
   		}
   		else{
   	        let config = {
   	   	          method: "GET",
   	   	          headers: {
   	   	            "Content-Type": "application/json",
   	   	          },
   	     	   	};

   	   			fetch(`${root}/rmap/hospital/\${sido}/\${gugun}`,config)
   	   			.then((response)=>response.json())
   	   			.then((data)=>makeHospitalList(data));
   	   		}
   		
   		
   	});

   
    function makeHospitalList(apts){
      	let tbody = ``;
      	if(apts.length==0){
      		tbody+=`<tr>
      	        <td>안심진료병원 없음</td>
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
					<tr class="apt-item">
						<td>
							<div class="apt-name">
								<a>\${apt.name }</a>
							</div>
							<div class="apt-space">주소 : \${apt.address }</div>
							<div class="apt-price">`;
				if(apt.type =='A'){
					tbody+=`유형 : 외래진료   `;
				}
				
				else if(apt.type =='B'){
					tbody+=`유형 : 외래진료 및 입원`;
				}
				tbody+=`
							</div>
							<div class="apt-tel">전화번호: \${apt.phonenumber }</div>
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
            		          //var message = String(apt.querySelector("거래금액").textContent) + "만원"; // 인포윈도우에 표시될 내용입니다
//             		          aptPoint.push(new kakao.maps.LatLng(result[0].y, result[0].x));
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
		document.querySelector("#hospitalList").innerHTML = tbody;
	}
  	

  	
    </script>

<script type="text/javascript" src="${root }/assets/js/main2.js"></script>
<%@ include file="/WEB-INF/views/common/footer.jsp"%>
